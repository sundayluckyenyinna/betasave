package com.pentazon.betasave;

import com.pentazon.betasave.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BetasaveApplication {
	@Autowired
	private JwtUtil jwtUtil;

	public static void main(String[] args) {
		SpringApplication.run(BetasaveApplication.class, args
		);
	}

}
