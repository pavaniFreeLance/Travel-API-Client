package com.afkl.cases.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAsync
public class AsyncConfiguration implements WebMvcConfigurer
{
    @Bean(name = "asyncExecutor")
    public ThreadPoolTaskExecutor asyncExecutor() 
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsynchThread-");        
        executor.initialize();
        return executor;
    }
    
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
          configurer.setTaskExecutor(asyncExecutor());
          configurer.setDefaultTimeout(30000);
    }
   
   
}