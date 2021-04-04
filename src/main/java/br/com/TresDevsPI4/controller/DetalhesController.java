package br.com.TresDevsPI4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.TresDevsPI4.model.Produto;
import br.com.TresDevsPI4.repositories.ProdutoRepository;

@Controller
public class DetalhesController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping("/loja/detalhes")
	public ModelAndView detalhes(Produto produto) {
		ModelAndView mv = new ModelAndView("/loja/detalhes");
		mv.addObject("produto", produto);
		return mv;
	}
	
	@GetMapping("/loja/teste")
	public ModelAndView teste(Produto produto) {
		ModelAndView mv = new ModelAndView("/loja/teste");
		mv.addObject("produto", produto);
		return mv;
	}
	
}
