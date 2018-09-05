package com.niit.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//similar to dispatcher-servlet.xml file configuration

@Configuration
@EnableWebMvc //<mvc:annotation-driven> tag in dispather-servlet.xml file
@ComponentScan(basePackages="com.niit") //<context:component-scan ..>

public class WebAppConfig extends WebMvcConfigurerAdapter
{

}
