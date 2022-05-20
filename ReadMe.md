This sample application consists of below key points/aspects :
--------------------------------------------------------------
Spring boot with customized multitenant framework
--------------------------------------------------
    a. It handles dynamic implementation of multiple DispatcherServlet for 2 different tenants (  Tenant1 & Tenant2  ) 
        using Spring Boot
        
    b. In normal applications ( non spring-boot ) WebApplicationInitializer class needs to be overridden to customize the
        DispatcherServlet. But in Spring boot application we could do the similar activity by implementing
        ServletContextInitializer.
        
    c. Each tenant DispatcherServlets are being registered with customized InternalViewResolver to register view resolver
        path for static documents.
        
    d. MvcConfigrationTenant1 & MvcConfigrationTenant2 classes respectively implements WebMvcConfigurer with instantiating
        InternalViewResolver with required suffix and prefix.
        
    e. MultiTenantWebSecurityConfig - its a customization of WebSecurityConfigurerAdapter for each of the tenant and overriding
        Spring based default security. Each tenant have its own tenant context url and spring security is applied independently
        depending on the URL mapping for respective tenants.
        
    f. This application also implement custom AuthenticationProcessingFilter which is noting but a overridden class of
        UsernamePasswordAuthenticationFilter. Its enable all user login as its bypass spring boot default security implementation.
        
    e. Importent note :  for Spring security to identify the custom Authentication filter it requires the filterProcessingURL
        with the required login URL (  default for Spring security 4 +  is /login ) and another way is to have the setup of
        requiresAuthenticationRequestMatcher valiable of UsernamePasswordAuthenticationFilter class with specified login URL.
        
    g. AuthenticationSuccessHandler is being customized in CustomAuthenticationSuccessHandler to provide the support for after successful authentication
        redirection specification.
        
    h. AuthenticationFailureHandler is being customized in CustomAuthenticationFailureHandler to provide the support for the
        invalid login scenarios.
        
    I. CustomAuthenticationProvider is being the customized version of AuthenticationProvider which overrides the Authenticate
        method as well as it specify the supported Auth token mechanism, i.e for this application it is
        UsernamePasswordAuthenticationToken object which contains user auth details like principal, credential & authorities.
        
    F. CustomAuthenticationProvider is being consumed by each tenant security configs for their required authentication process.
    
    G. This application also handles session controlling like invalid session strategy, securing the cookie by enabling
        'httponly' and 'secure' attribute in the cookie, concurrent session control strategy, maximum concurrent session handling.
        
    H. CustomFilter also being used to securing the tenant specific resources.



