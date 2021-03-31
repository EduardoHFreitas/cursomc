package com.eduardo.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.eduardo.cursomc.domain.Pedido;

public interface IEmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage email);
	
	void sendOrderConfirmationHTMLEmail(Pedido pedido);
	
	void sendHTMLEmail(MimeMessage email);
}
