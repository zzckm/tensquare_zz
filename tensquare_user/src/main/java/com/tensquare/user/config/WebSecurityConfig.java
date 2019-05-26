package com.tensquare.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 */
@Configuration //让系统知道这是一个配置类
@EnableWebSecurity                      //继承空方法，可继承重写
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Override //做spring security 就使用下面的就可以了
    protected void configure(HttpSecurity http) throws Exception {

        //todo(springboot可以使用全注解开发，spring只能自己写的类用注解，框架的类使用配置文件)
        /**
         * authorizeRequests（权限需求-授权需求） : 所有所有security全注解配置实现的开头（格式）—表示开始说明需要的权限。
         * 需要的权限分为两部分：1. 拦截的路径；
         *                       2. 访问该路径需要的权限
         *antMatchers ：表示拦截的路径是什么 antMatchers.access   /antMatchers.hasAnyRole  这两个是添加角色状态
         * permitAll ：任何权限都可以访问，放行所有，
         * anyRequest ：任何的请求
         * authenticated ：认证后才可以访问
         * .and().csrf().disable(); ： 固定写法，表示使csrf（网络攻击）拦截失效
         */
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
