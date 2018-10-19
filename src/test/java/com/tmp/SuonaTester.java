package com.tmp;

import com.cat.CatApplication;
import com.cat.manager.MessageSender;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatApplication.class)
@EnableAutoConfiguration
public class SuonaTester {

	@Autowired
	private MessageSender messageSender;

	@Test
	public void creatValidateCodeTemplate() throws Exception {
		this.createTemplate("src/test/template/validateCode.html","CAT_CAPTCHA_V1","贷后验证码","验证码");
	}

	@Test
	public void creatResetPasswordTemplate() throws Exception {
		this.createTemplate("src/test/template/resetPassword.html","CAT_RESET_PASSWORD_V0.0.1",
				"贷后重置密码","重置密码");
	}

	public void createTemplate(String tempFile,String code, String name, String subject) throws Exception {
		FileReader reader = new FileReader(tempFile);
		char[] buffer = new char[1000];
		StringBuilder content = new StringBuilder();
		while (reader.read(buffer)>0){
			content.append(buffer);
		}

		System.out.println(content.toString().trim());
		Map<String,String> template = new HashMap<>();
		template.put("templateCode", code);
		template.put("templateName",name);
		template.put("markupType","HTML");
		template.put("language","CN");
		template.put("subjectModel",subject);
		template.put("contentModel",content.toString().trim());
		template.put("signModel","");

		System.out.println(messageSender.createTemplate(template) );
	}

}
