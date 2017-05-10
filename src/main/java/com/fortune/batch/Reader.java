package com.fortune.batch;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
 
import com.fortune.model.User;
 
public class Reader {
    public static FlatFileItemReader<User> reader(String path) {
        FlatFileItemReader<User> reader = new FlatFileItemReader<User>();
 
        reader.setResource(new ClassPathResource(path));
        reader.setLineMapper(new DefaultLineMapper<User>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] { "id" ,"active", "email", "last_name", "name","phoneNumber", "password" ,"role"});
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {
                    {
                        setTargetType(User.class);
                    }
                });
            }
        });
        return reader;
    }
}
