package com.eduardo.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SMTPEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(SMTPEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage email) {
		LOG.info("Enviando Email:");
		mailSender.send(email);
		LOG.info("Email enviado!");
	}

	@Override
	public void sendHTMLEmail(MimeMessage email) {
		LOG.info("Enviando Email HTML:");
		javaMailSender.send(email);
		LOG.info("Email HTML enviado!");			
	}

}
