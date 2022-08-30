package com.itguigu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity //开启
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 这是Security提供的一种加密方式，将其放入容器中，方便使用
     * 必须指定加密方式，上下加密方式要一致
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();//指定解码器,会自动解码
    }


    /**
     * Authentication 认证
     *
     * @param auth
     * @throws Exception
     */
/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin") //账户
                *//**
                 * 访问被禁止（账户或密码错）
                 * 在.password("{noop}admin")在此处默认加密的，加一个{noop}就是不加密
                 * 在登录时，BCryptPasswordEncoder会将传递过来的密码加密一次
                 * 在从数据库传递过来的密码也需要加密一次
                 *//*
                .password(getBCryptPasswordEncoder().encode("admin")) // 密码，
                .roles("");//role  角色
    }*/




    /**
     * 登录过后会显示无法连接，因为Security默认不允许iframe嵌套页面
     * 就要用到另一个configure方法
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         *  super.configure(http);
         * 暂时不能去掉，因为其中有默认配置,否则不需要认证就可以访问
         *      http
         * 			.authorizeRequests()
         * 				.anyRequest().authenticated()  所有请求登录的才能访问
         * 				.and()
         * 			.formLogin().and()  设置登录的相关信息
         * 			.httpBasic();
         *        }
         */

        //设置允许iframe嵌套显示
        http.headers().frameOptions().disable();
        //1.设置资源放行
        http.authorizeRequests()
                .antMatchers("/login", "/static/**").permitAll()
                //除此路径外其他请求需要登录
                .anyRequest().authenticated();

        //2.设置登录
        http.formLogin()
                //设置自定义登录页面的路径
                .loginPage("/login")
                //处理登录请求的路径
                .loginProcessingUrl("/login_process")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .failureUrl("/login");

        //3.设置注销
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");

        //4. 禁用csrf
        //注意: 禁用csrf , 否则请求/logout报错404
        http.csrf().disable();

        //设置自定义权限不足页面
        http.exceptionHandling()
                .accessDeniedHandler(new AtguiguAccessDeniedHandler());
    }

}
