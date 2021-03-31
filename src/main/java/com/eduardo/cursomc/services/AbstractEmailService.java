package com.eduardo.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.eduardo.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements IEmailService {

	@Value("${default.email.sender}")
	private String sender;

	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(pedido);
		
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(pedido.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido realizado! Codigo: " + pedido.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(pedido.toString());
		
		return sm;
	}
}
