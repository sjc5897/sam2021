//package com.sam2021.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//public class AppSecurityConfiguration  extends WebSecurityConfiguration {
//    @Autowired
//    DataSource dataSource;
//
//    @Autowired
//    public void config(AuthenticationManagerBuilder auth) throws Exception{
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select email,password "
//                + "from userinfo" + "where email = ?")
//                .authoritiesByUsernameQuery("select role from userinfo  where email = ?");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http.authorizeRequests().antMatchers("/").permitAll()
//
//    }
//}
