package com.eduardo.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.eduardo.cursomc.domain.enums.TipoCliente;
import com.eduardo.cursomc.dto.ClienteDTO;
import com.eduardo.cursomc.resources.exceptions.FieldMessage;
import com.eduardo.cursomc.services.validation.utils.DocumentUtil;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsertAnnotation, ClienteDTO> {

	@Override
	public void initialize(ClienteInsertAnnotation ann) {	
	}

	@Override
	public boolean isValid(ClienteDTO clienteDTO, ConstraintValidatorContext context) {

		List<FieldMessage> erros = new ArrayList<>();

		if (clienteDTO.getTipo() == null) {
			erros.add(new FieldMessage("tipo", "O campo tipo é obrigatório"));
		} else if (clienteDTO.getTipo().equals(TipoCliente.PESSOA_FISICA.getCodigo()) && !DocumentUtil.isValidCPF(clienteDTO.getCpfOuCnpj())) {
			erros.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		} else if (clienteDTO.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) && !DocumentUtil.isValidCNPJ(clienteDTO.getCpfOuCnpj())) {
			erros.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}

		for (FieldMessage e : erros) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getErro()).addConstraintViolation();
		}
		return erros.isEmpty();
	}

}
