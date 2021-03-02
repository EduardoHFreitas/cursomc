package com.eduardo.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.eduardo.cursomc.domain.Cliente;
import com.eduardo.cursomc.dto.ClienteUpdateDTO;
import com.eduardo.cursomc.repositories.ClienteRepository;
import com.eduardo.cursomc.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdateAnnotation, ClienteUpdateDTO> {

	@Autowired
	private HttpServletRequest httpServletRequest; 
	
	@Autowired
	private ClienteRepository clienteRepository; 
	
	@Override
	public void initialize(ClienteUpdateAnnotation ann) {	
	}

	@Override
	public boolean isValid(ClienteUpdateDTO clienteUpdateDTO, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> erros = new ArrayList<>();

		Cliente cliente = clienteRepository.findByEmail(clienteUpdateDTO.getEmail());
		
		if (cliente != null && !cliente.getId().equals(uriId)) {
			erros.add(new FieldMessage("email", "Email já cadastrado"));
		}
		
		for (FieldMessage e : erros) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getErro()).addConstraintViolation();
		}
		return erros.isEmpty();
	}

}
