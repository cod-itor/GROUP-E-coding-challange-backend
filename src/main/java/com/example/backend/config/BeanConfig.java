    package com.example.backend.config;


    import com.example.backend.model.entity.AppUser;
    import com.example.backend.model.response.AppUserResponse;
    import org.modelmapper.ModelMapper;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;

    @Configuration
    public class BeanConfig {
        @Bean
        public ModelMapper modelMapper(){
            return new ModelMapper();
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }
    }
