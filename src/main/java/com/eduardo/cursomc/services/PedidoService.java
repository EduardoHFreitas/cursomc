package com.eduardo.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduardo.cursomc.domain.ItemPedido;
import com.eduardo.cursomc.domain.PagamentoComBoleto;
import com.eduardo.cursomc.domain.Pedido;
import com.eduardo.cursomc.domain.enums.EstadoPagamento;
import com.eduardo.cursomc.repositories.ItemPedidoRepository;
import com.eduardo.cursomc.repositories.PagamentoRepository;
import com.eduardo.cursomc.repositories.PedidoRepository;
import com.eduardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private ProdutoService produtoService;
	
	public Pedido findById(Integer id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));	
	}

	@Transactional
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamento = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagamento, pedido.getInstante());
			
			pedido.setPagamento(pagamento);
		}

		
		for (ItemPedido item : pedido.getItens()) {
			if (item.getDesconto() == null) {
				item.setDesconto(0.00);
			}
			
			item.setPreco(produtoService.findById(item.getProduto().getId()).getPreco());
			item.setPedido(pedido);
		}
		
		pedido = pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		itemPedidoRepository.saveAll(pedido.getItens());
		
		return pedido;
	}
}
