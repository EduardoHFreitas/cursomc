package com.eduardo.cursomc.services.mock;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import com.eduardo.cursomc.services.AbstractEmailService;

public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage email) {
		LOG.info("Enviando Email:");
		LOG.info(email.toString());
		LOG.info("Email enviado!");
	}

	@Override
	public void sendHTMLEmail(MimeMessage email) {
		LOG.info("Enviando Email HTML:");
		LOG.info(email.toString());
		LOG.info("Email HTML enviado!");		
	}
}
