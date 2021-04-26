package br.com.TresDevsPI4.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.TresDevsPI4.model.Cliente;
import br.com.TresDevsPI4.model.Produto;
import br.com.TresDevsPI4.repositories.ProdutoRepository;
import br.com.TresDevsPI4.services.Util;

@Controller()
@RequestMapping
public class indexController {

	boolean teste = false;

	@Autowired
	private ProdutoRepository produtoRepositorio;

	@RequestMapping(path = { "/index", "/" })
	public ModelAndView index(HttpSession session, Cliente cliente) {
		ModelAndView mv = new ModelAndView("/index");
		//System.out.println("verfica " + teste);
		
		if (!teste) {
			session.setAttribute("usuarioLogado", cliente);
			mv.addObject("listaProdutos", produtoRepositorio.buscarTrue());
			//System.out.println("111111111111111");
			teste = true;
			//System.out.println("verfica " + teste);
			return mv;
		}

		mv.addObject("listaProdutos", produtoRepositorio.buscarTrue());
		//System.out.println("22222222222");
		return mv;

	}
	
	//desloga sessao
		@GetMapping("/logout")
		public String logout(HttpSession session) {
			session.invalidate();
			teste = false;
			return "redirect:/";

		}

	@GetMapping("/loja/detalhes/{produtoId}")
	public ModelAndView detalhesProduto(@PathVariable Integer produtoId) {

		// System.out.println(produtoId);

		ModelAndView mv = new ModelAndView("loja/detalhes");

		Produto produto = produtoRepositorio.findById(produtoId).orElseThrow();
		// System.out.println(produto.getId());

		mv.addObject("produto", produto);
		return mv;

	}

}
