package com.cafe24.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.cafe24.config.app.DBConfig;
import com.cafe24.config.app.MybatisConfig;
import com.cafe24.config.app.SecurityConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cafe24.mysite.service","com.cafe24.mysite.repository","com.cafe24.mysite.security"})
@Import({SecurityConfig.class, DBConfig.class, MybatisConfig.class})
public class AppConfig {

}
