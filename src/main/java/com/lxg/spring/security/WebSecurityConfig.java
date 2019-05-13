package com.lxg.spring.security;


import com.lxg.spring.config.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启security注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	http.csrf().disable();
    	
        //允许所有用户访问"/"和"/home"
        http.authorizeRequests()
                .antMatchers("/", "/home").permitAll() //所有角色都可以访问
                //.antMatchers("/orders/**").hasRole("USER")    //用户权限
                //.antMatchers("/users/**").hasRole("ADMIN")    //管理员权限
                //其他地址的访问均需验证权限
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //指定登录页是"/login"
                .loginPage("/pagesLogin") //跳转登录页面的控制器，该地址要保证和表单提交的地址一致！可以用.html前台自愿文件查找 或者/login 则调用/login服务
                .defaultSuccessUrl("/indetables")//登录成功后默认跳转到 indetables
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")//退出登录后的默认url是"/pages-login"
                .permitAll();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/assets/**");
    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
            .userDetailsService(customUserDetailsService());  //登录用户角色验证
            //.passwordEncoder(passwordEncoder());   //MD5密码加密算法验证

    }

    /**
     * 设置用户密码的加密方式为MD5加密
     * @return
     */
    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();

    }

    /**
     * 自定义UserDetailsService，从数据库中读取用户信息
     * @return
     */
    @Bean
    public CustomUserDetailsService customUserDetailsService(){
        return new CustomUserDetailsService();
    }

}