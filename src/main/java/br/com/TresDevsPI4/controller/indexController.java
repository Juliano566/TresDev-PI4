package br.com.TresDevsPI4.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.TresDevsPI4.model.Produto;
import br.com.TresDevsPI4.repositories.ProdutoRepository;

@Controller()
@RequestMapping
public class indexController {
	
	@Autowired
	private ProdutoRepository produtoRepositorio;
	
	@RequestMapping(path = {"/index","/"})
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("/index");
		
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		
		return mv;
		
	}
	
	@GetMapping("/loja/detalhes/{produtoId}")
	public ModelAndView detalhesProduto(@PathVariable Integer produtoId) {
		
		//System.out.println(produtoId);
		
		ModelAndView mv = new ModelAndView("loja/detalhes");
	
		Produto produto = produtoRepositorio.findById(produtoId).orElseThrow();
		//System.out.println(produto.getId());
		
		mv.addObject("produto", produto);
		return mv;
		
	}
	



}
