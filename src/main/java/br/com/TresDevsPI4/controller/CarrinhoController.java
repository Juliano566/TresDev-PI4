package br.com.TresDevsPI4.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.TresDevsPI4.model.Cliente;
import br.com.TresDevsPI4.model.Endereco;
import br.com.TresDevsPI4.model.Funcionario;
import br.com.TresDevsPI4.model.Produto;
import br.com.TresDevsPI4.repositories.EnderecoRepository;
import br.com.TresDevsPI4.repositories.ProdutoRepository;
import br.com.TresDevsPI4.services.Util;

@Controller
public class CarrinhoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping("/loja/carrinho")
	public ModelAndView detalhes(Produto produto) {
		ModelAndView mv = new ModelAndView("/loja/carrinho");
		mv.addObject("produto", produto);
		return mv;
	}


}
