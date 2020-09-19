# Spring Security配置JSON登录
https://www.jianshu.com/p/693914564406
## 一、重写UsernamePasswordAuthenticationFilter
````
/**
 * AuthenticationFilter that supports rest login(json login) and form login.
 * @author chenhuanming
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //attempt Authentication when Content-Type is json
        if(request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                ||request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)){

            //use jackson to deserialize json
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()){
                AuthenticationBean authenticationBean = mapper.readValue(is,AuthenticationBean.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.getUsername(), authenticationBean.getPassword());
            }catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            }finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }

        //transmit it to UsernamePasswordAuthenticationFilter
        else {
            return super.attemptAuthentication(request, response);
        }
    }
}

````

## 二、WebSecurityConfigurerAdapter配置
````
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
            .cors().and()
            .antMatcher("/**").authorizeRequests()
            .antMatchers("/", "/login**").permitAll()
            .anyRequest().authenticated()
            //这里必须要写formLogin()，不然原有的UsernamePasswordAuthenticationFilter不会出现，也就无法配置我们重新的UsernamePasswordAuthenticationFilter
            .and().formLogin().loginPage("/")
            .and().csrf().disable();

    //用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
    http.addFilterAt(customAuthenticationFilter(),
    UsernamePasswordAuthenticationFilter.class);
}

//注册自定义的UsernamePasswordAuthenticationFilter
@Bean
CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
    CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
    filter.setAuthenticationSuccessHandler(new SuccessHandler());
    filter.setAuthenticationFailureHandler(new FailureHandler());
    filter.setFilterProcessesUrl("/login/self");

    //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
    filter.setAuthenticationManager(authenticationManagerBean());
    return filter;
}
````

##spring security 工作原理
spring security 是基于javax.servlet.Filter的，
因此才能在spring mvc（DispatcherServlet基于Servlet）前起作用。

###UsernamePasswordAuthenticationFilter:
实现Filter接口，负责拦截登录处理的url，帐号和密码会在这里获取，
然后封装成Authentication交给AuthenticationManager进行认证工作
###Authentication:
贯穿整个认证过程，封装了认证的用户名，密码和权限角色等信息，
接口有一个boolean isAuthenticated()方法来决定该Authentication认证成功没;
###AuthenticationManager:
认证管理器，但本身并不做认证工作，只是做个管理者的角色。
例如默认实现ProviderManager会持有一个AuthenticationProvider数组，
把认证工作交给这些AuthenticationProvider，
直到有一个AuthenticationProvider完成了认证工作。
###AuthenticationProvider:
认证提供者，默认实现，也是最常使用的是DaoAuthenticationProvider。
我们在配置时一般重写一个UserDetailsService来从数据库获取正确的用户名密码，
其实就是配置了DaoAuthenticationProvider的UserDetailsService属性，
DaoAuthenticationProvider会做帐号和密码的比对，
如果正常就返回给AuthenticationManager一个验证成功的Authentication


###Spring Security form-login属性详解
````
1. login-page 
    自定义登录页url,默认为/login
2. login-processing-url 
    登录请求拦截的url,也就是form表单提交时指定的action
3. default-target-url 
    默认登录成功后跳转的url
4. always-use-default-target 
    是否总是使用默认的登录成功后跳转url
5. authentication-failure-url 
    登录失败后跳转的url
6. username-parameter 
    用户名的请求字段 默认为userName
7. password-parameter 
    密码的请求字段 默认为password
8. authentication-success-handler-ref 
    指向一个AuthenticationSuccessHandler用于处理认证成功的请求,不能和default-target-url还有always-use-default-target同时使用
9. authentication-success-forward-url 
    
10. authentication-failure-handler-ref 
    指向一个AuthenticationFailureHandler用于处理失败的认证请求
11. authentication-failure-forward-url 
    
12. authentication-details-source-ref 
    指向一个AuthenticationDetailsSource,在认证过滤器中使用
````

