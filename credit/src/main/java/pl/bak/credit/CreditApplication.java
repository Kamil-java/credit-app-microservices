package pl.bak.credit;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import pl.bak.credit.error.handler.ErrorHandler;

@SpringBootApplication
public class CreditApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder()
                .errorHandler(new ErrorHandler())
                .build();
    }
}
