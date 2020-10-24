package com.sam2021.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.rmi.registry.Registry;

/**
 * Class used to configure Spring MVC and Templating Engine
 * @Language: Java 13
 * @Framework: Spring
 * @Author: Stephen Cook <sjc5897@rit.edu>
 * @Created: 10/24/2020
 * @LastEdit:  10/24/2020
 */

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * Registration of controllers, done via this tutorial: https://www.baeldung.com/spring-mvc-tutorial'
     * Note the tutorial uses jsp, not thymeleaf
     * @param registry A ViewController Registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("index");
    }

    /**
     * Sets up a the systems templating engine: https://www.baeldung.com/spring-template-engines#2-spring-configuration
     * @return SpringTemplateEngine a new template engine
     */
    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        return templateEngine;
    }

    /**
     * Configues a Thymeleaf Template Resolver to our file structure
     * @return returns a configured template resolver configured with our file structure
     */
    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }

    /**
     * Returns a thymeleaf view resolver
     * @return a thymeleaf view resolver
     */
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }

    /**
     * Configures the resource registry for our file structure
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/resources/**", "/css/**")
                .addResourceLocations("/WEB-INF/resources/", "/WEB-INF/css/");
    }
}
