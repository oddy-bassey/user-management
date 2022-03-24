package com.revoltcode.user.cmd.api;

import com.revoltcode.user.core.configuration.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/*
 * @EnableResourceServer is a convenient annotation for OAuth2 resource servers
 * enabling a spring security filter that authenticates requests via an incoming oAuth2 token
 */
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({AxonConfig.class})
@SpringBootApplication
public class UserCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCommandApplication.class, args);
	}

}
