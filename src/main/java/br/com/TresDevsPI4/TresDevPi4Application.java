package br.com.TresDevsPI4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.TresDevsPI4.services.Util;

@SpringBootApplication
public class TresDevPi4Application {

	public static void main(String[] args) {
		SpringApplication.run(TresDevPi4Application.class, args);
		
		
		
		System.out.println(Util.md5("123"));

	}

}
