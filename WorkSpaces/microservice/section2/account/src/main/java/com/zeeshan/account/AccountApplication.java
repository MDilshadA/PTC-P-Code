package com.zeeshan.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*@ComponentScans({ @ComponentScan("com.zeeshan.accounts.controller") })
@EnableJpaRepositories("com.zeeshan.accounts.repository")
@EntityScan("com.zeeshan.accounts.model")*/

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

}
