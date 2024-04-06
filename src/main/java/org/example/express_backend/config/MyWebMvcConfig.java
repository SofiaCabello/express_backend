package org.example.express_backend.config;

import org.example.express_backend.interceptor.CORSInterceptor;
import org.example.express_backend.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    //自定义拦截器对象
    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册登录自定义拦截器对象
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v2/api-docs",
                        "/v3/api-docs/**",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security"
                );
        //注册跨域拦截器
        registry.addInterceptor(new CORSInterceptor())
                .addPathPatterns("/**");
    }
}
