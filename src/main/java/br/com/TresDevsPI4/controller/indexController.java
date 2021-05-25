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
import br.com.TresDevsPI4.repositories.CategoriaRepository;
import br.com.TresDevsPI4.repositories.ProdutoRepository;
import br.com.TresDevsPI4.services.Util;

@Controller()
@RequestMapping
public class indexController {

	boolean teste = false;
	boolean testeCategoria = false;
	String valorCategoria;

	@Autowired
	private ProdutoRepository produtoRepositorio;

	@Autowired
	private CategoriaRepository categoriaRepositorio;

	@RequestMapping(path = { "/index", "/" })
	public ModelAndView index(HttpSession session, Cliente cliente) {
		ModelAndView mv = new ModelAndView("/index");
		if (!teste) {
			session.setAttribute("usuarioLogado", cliente);
			mv.addObject("listaCategorias", categoriaRepositorio.buscarCategorias());
			if (!testeCategoria) {
				mv.addObject("listaProdutos", produtoRepositorio.buscarTrue());
			} else {
				mv.addObject("listaProdutos", produtoRepositorio.buscarProdutoCategoria(valorCategoria));
				testeCategoria = false;
			}
			teste = true;
			return mv;
		}
		mv.addObject("listaCategorias", categoriaRepositorio.buscarCategorias());
		if (!testeCategoria) {
			mv.addObject("listaProdutos", produtoRepositorio.buscarTrue());
		} else {
			mv.addObject("listaProdutos", produtoRepositorio.buscarProdutoCategoria(valorCategoria));
			testeCategoria = false;
		}
		return mv;
	}

	@GetMapping("/index/{categoria}")
	public String categorias(@PathVariable("categoria") String categoria) {
		testeCategoria = true;
		valorCategoria = categoria;
		return "redirect:/";
	}

	// desloga sessao
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
		mv.addObject("listaCategorias", categoriaRepositorio.buscarCategorias());
		mv.addObject("produto", produto);
		return mv;
	}

}
