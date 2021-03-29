package com.eduardo.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.eduardo.cursomc.domain.Categoria;
import com.eduardo.cursomc.domain.Cidade;
import com.eduardo.cursomc.domain.Cliente;
import com.eduardo.cursomc.domain.Endereco;
import com.eduardo.cursomc.domain.ItemPedido;
import com.eduardo.cursomc.domain.Pagamento;
import com.eduardo.cursomc.domain.PagamentoComBoleto;
import com.eduardo.cursomc.domain.PagamentoComCartao;
import com.eduardo.cursomc.domain.Pedido;
import com.eduardo.cursomc.domain.Produto;
import com.eduardo.cursomc.domain.enums.Estado;
import com.eduardo.cursomc.domain.enums.EstadoPagamento;
import com.eduardo.cursomc.domain.enums.TipoCliente;
import com.eduardo.cursomc.repositories.CategoriaRepository;
import com.eduardo.cursomc.repositories.CidadeRepository;
import com.eduardo.cursomc.repositories.ClienteRepository;
import com.eduardo.cursomc.repositories.EnderecoRepository;
import com.eduardo.cursomc.repositories.ItemPedidoRepository;
import com.eduardo.cursomc.repositories.PagamentoRepository;
import com.eduardo.cursomc.repositories.PedidoRepository;
import com.eduardo.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Mouse", 80.00);
		Produto prod3 = new Produto(null, "Impressora", 800.00);
		Produto prod4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto prod5 = new Produto(null, "Toalha", 50.00);
		Produto prod6 = new Produto(null, "Colcha", 200.00);
		Produto prod7 = new Produto(null, "TV true color", 1200.00);
		Produto prod8 = new Produto(null, "Roçadeira", 800.00);
		Produto prod9 = new Produto(null, "Abajour", 100.00);
		Produto prod10 = new Produto(null, "Pendente", 180.00);
		Produto prod11 = new Produto(null, "Shampoo", 90.00);

		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2, prod4));
		cat3.getProdutos().addAll(Arrays.asList(prod5, prod6));
		cat4.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3, prod7));
		cat5.getProdutos().addAll(Arrays.asList(prod8));
		cat6.getProdutos().addAll(Arrays.asList(prod9, prod10));
		cat7.getProdutos().addAll(Arrays.asList(prod11));

		prod1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		prod3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		prod4.getCategorias().addAll(Arrays.asList(cat2));
		prod5.getCategorias().addAll(Arrays.asList(cat3));
		prod6.getCategorias().addAll(Arrays.asList(cat3));
		prod7.getCategorias().addAll(Arrays.asList(cat4));
		prod8.getCategorias().addAll(Arrays.asList(cat5));
		prod9.getCategorias().addAll(Arrays.asList(cat6));
		prod10.getCategorias().addAll(Arrays.asList(cat6));
		prod11.getCategorias().addAll(Arrays.asList(cat7));

		Cidade cid1 = new Cidade(null, "Uberlandia", Estado.MG);
		Cidade cid2 = new Cidade(null, "Sao Paulo", Estado.SP);
		Cidade cid3 = new Cidade(null, "Campinas", Estado.SP);

		Cliente cliente = new Cliente(null, "Maria Silva", "maria@gmail.com", "123456789", TipoCliente.PESSOA_FISICA);
		cliente.getTelefones().addAll(Arrays.asList("33223311", "33223322"));

		Endereco end1 = new Endereco(null, "Rua Flores", "300", "apt 303", "Jardim", "35305301", cliente, cid1);
		Endereco end2 = new Endereco(null, "Av Matos", "101", "Sala 800", "Centro", "65605601", cliente, cid2);

		cliente.getEnderecos().addAll(Arrays.asList(end1, end2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cliente, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/01/2017 19:35"), cliente, end2);

		Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		Pagamento pag2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 23:59"), null);

		ped1.setPagamento(pag1);
		ped2.setPagamento(pag2);

		cliente.getPedidos().addAll(Arrays.asList(ped1, ped2));

		ItemPedido item1 = new ItemPedido(ped1, prod1, 0.00, 1, prod1.getPreco());
		ItemPedido item2 = new ItemPedido(ped1, prod2, 0.00, 2, prod2.getPreco());
		ItemPedido item3 = new ItemPedido(ped2, prod3, 100.00, 1, prod3.getPreco());

		ped1.getItens().addAll(Arrays.asList(item1, item2));
		ped2.getItens().add(item3);

		prod1.getItens().add(item1);
		prod2.getItens().add(item2);
		prod3.getItens().add(item3);

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8, prod9, prod10, prod11));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		clienteRepository.save(cliente);
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pag1, pag2));
		itemPedidoRepository.saveAll(Arrays.asList(item1, item2, item3));
	}

}
