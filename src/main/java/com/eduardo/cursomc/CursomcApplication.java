package com.eduardo.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.eduardo.cursomc.domain.Categoria;
import com.eduardo.cursomc.domain.Cidade;
import com.eduardo.cursomc.domain.Cliente;
import com.eduardo.cursomc.domain.Endereco;
import com.eduardo.cursomc.domain.Estado;
import com.eduardo.cursomc.domain.Produto;
import com.eduardo.cursomc.domain.enums.TipoCliente;
import com.eduardo.cursomc.repositories.CategoriaRepository;
import com.eduardo.cursomc.repositories.CidadeRepository;
import com.eduardo.cursomc.repositories.ClienteRepository;
import com.eduardo.cursomc.repositories.EnderecoRepository;
import com.eduardo.cursomc.repositories.EstadoRepository;
import com.eduardo.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		Produto Prod1 = new Produto(null, "Computador", 2000.00);
		Produto Prod2 = new Produto(null, "Impressora", 800.00);
		Produto Prod3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(Prod1, Prod2, Prod3));
		cat2.getProdutos().add(Prod2);
		
		Prod1.getCategorias().add(cat1);
		Prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		Prod3.getCategorias().add(cat1);
		
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlandia", est1);
		Cidade cid2 = new Cidade(null, "Sao Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(cid1);
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));
		
		Cliente cliente = new Cliente(null, "Maria Silva", "maria@gmail.com", "123456789", TipoCliente.PESSOA_FISICA);
		cliente.getTelefones().addAll(Arrays.asList("33223311", "33223322"));
		
		Endereco end1 = new Endereco(null, "Rua Flores", "300", "apt 303", "Jardim", "35305301", cliente, cid1);
		Endereco end2 = new Endereco(null, "Av Matos", "101", "Sala 800", "Centro", "65605601", cliente, cid2);
		
		cliente.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(Prod1, Prod2, Prod3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		clienteRepository.save(cliente);
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
	}

}
