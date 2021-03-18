/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.TresDevsPI4.controller;


import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.TresDevsPI4.model.Produto;
import br.com.TresDevsPI4.repositories.ProdutoRepository;



/*
 *
 * @author nails
 */

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	//auterar para caminho absoluto real da maquina
	private static String caminhoImagens = "C:/Users/Julia/workspace-spring-tool-suite-4-4.9.0.RELEASE/img/";
    
 
	@Autowired
	private ProdutoRepository produtoRepository;
		
	//Cadastra produto
	@PostMapping
	public @ResponseBody Produto novoProduto(Produto produto, 
		@RequestParam("file") MultipartFile arquivo) {
		
		
		produtoRepository.save(produto);	
		System.out.println(produto);
		try {
			if(!arquivo.isEmpty()) {
				
				byte[] bytes = arquivo.getBytes();
				
				
				
				produto.setFoto1(arquivo.getOriginalFilename());
				
				
				Path caminho = Paths.get(caminhoImagens + String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
				
				Files.write(caminho, bytes);
				produto.setFoto1(caminhoImagens +  String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
				
				System.out.println(produto);
				produtoRepository.save(produto);
			}
		} catch (IOException e) {
			e.printStackTrace();
			
		}
			
		return produto;
	}
	
	
	
	//Listar produtos
	@GetMapping
	public Iterable<Produto> obtProduto() {
		return produtoRepository.findAll();
	}
	
	//LIstar produto por nome
	@GetMapping(path="/nome/{parteNome}")
	public Iterable<Produto>obtProdutoPorNome(@PathVariable String parteNome){
		return produtoRepository.findByNomeContaining(parteNome);
	}
	
	
	//Listar produtos por pagina
	@GetMapping(path="/pagina/{numeroPagina}/{qtdePagina}")
	public Iterable<Produto> obterProdutoPaginada(
			@PathVariable int numeroPagina, @PathVariable int qtdePagina) {
		if(qtdePagina >= 5) qtdePagina = 5;
		Pageable page = PageRequest.of(numeroPagina, qtdePagina);
		return produtoRepository.findAll(page);
	}
	
	//Buscar produto por ID
	@GetMapping(path="/{id}")
	public Optional<Produto> obterProdutoPorId(@PathVariable int id) {
		return produtoRepository.findById(id);
		
	}
	
	//Alterar produto
	@PutMapping
	public Produto alterarProduto(Produto produto) {
		produtoRepository.save(produto);
		return produto;
	}
	
	//deletar protudo
	@DeleteMapping(path = "/delete/{id}")
	public void excluirProduto(@PathVariable int id) {
		 produtoRepository.deleteById(id);
	}

	@GetMapping(path =  "/teste")
	public String index() {
		return "index";
	}
	

}


	
	
	
	
	
	
	
	
	
	
	
	 
