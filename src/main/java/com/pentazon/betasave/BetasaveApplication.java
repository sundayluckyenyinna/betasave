package com.pentazon.betasave;

import com.pentazon.betasave.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
public class BetasaveApplication {
//	@Autowired
//	private MailUtil mailUtil;

	public static void main(String[] args) {
		SpringApplication.run(BetasaveApplication.class, args
		);
	}

//	@EventListener(value = ApplicationStartedEvent.class)
//	public String testMail() throws IOException {
//		MessageTemplateUtil.getTemplateOf("test");
//		return "";
//	}
}
