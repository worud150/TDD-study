package com.green.todotestapp;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.lang.model.element.Element;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureMockMvc
@Import({MockMvcConfig.MockMvc.class})
@interface  MockMvcConfig {
    class MockMvc {
        @Bean
        MockMvcBuilderCustomizer utf8Config (){
            return b -> b.addFilter(
                    new CharacterEncodingFilter("UTF-8", true)
            );
        }
    }
}
