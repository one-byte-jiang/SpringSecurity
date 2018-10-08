package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private MyUserService myUserService;
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/images/**");//首先取消掉对静态资源的处理

    }
    /**
     * 重写对http请求的拦截
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()//对项目的主路径进行放行
                .anyRequest().authenticated()//对其他请求我们要经过验证
                .and()
                .logout().permitAll()//对注销也是允许访问的
                .and()
                .formLogin();//允许表单登录
        http.csrf().disable();//关闭csrf认证
    }
    /**
     * 添加一个认证用户
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");//基于内存存储数据
        //auth.inMemoryAuthentication().withUser("lisi").password("lisi").roles("USER");

        auth.userDetailsService(myUserService).passwordEncoder(new MyPasswordEncoder());//交给自定义的数据库用户管理
    }
}
