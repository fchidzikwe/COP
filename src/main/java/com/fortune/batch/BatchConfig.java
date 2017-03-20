package com.fortune.batch;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fortune.model.User;
import com.fortune.repository.UserRepository;

 
@Configuration
@EnableBatchProcessing
public class BatchConfig {
 
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
 
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
 
    @Autowired
    public UserRepository userRepository;
 
    @Bean
    public Job job() {
        return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(new Listener(userRepository))
                .flow(step1()).end().build();
    }
 
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<User, User>chunk(2)
                .reader(Reader.reader("users.csv"))
                .processor(new Processor()).writer(new Writer(userRepository)).build();
    }
}
