package springdata.xml.ex.springdataxmlexercise.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springdata.xml.ex.springdataxmlexercise.utils.ValidatorUtil;
import springdata.xml.ex.springdataxmlexercise.utils.ValidatorUtilImpl;
import springdata.xml.ex.springdataxmlexercise.utils.XmlParser;
import springdata.xml.ex.springdataxmlexercise.utils.XmlParserImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public XmlParser xmlParser(){
        return new XmlParserImpl();
    }

    @Bean
    public ValidatorUtil validatorUtil(){
        return new ValidatorUtilImpl();
    }

    @Bean
    public Random random(){
        return new Random();
    }

    @Bean
    public BufferedReader bufferedReader(){
        return new BufferedReader(new InputStreamReader(System.in));
    }

}
