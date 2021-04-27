package br.com.TresDevsPI4.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.TresDevsPI4.model.Cliente;
import br.com.TresDevsPI4.model.Endereco;
import br.com.TresDevsPI4.repositories.EnderecoRepository;
import br.com.TresDevsPI4.services.Util;

@Controller
public class EnderecoController {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	@GetMapping("/cliente/endereco/cadastrar")
	public ModelAndView cadastrar(Endereco endereco) {
		ModelAndView mv = new ModelAndView("/cliente/endereco");
		mv.addObject("endereco", endereco);
		return mv;
	}
	
	@PostMapping("/cliente/endereco/salvar")
	public String salvar(Endereco endereco, BindingResult result, RedirectAttributes ra, HttpSession session) {
		try {
			enderecoRepository.save(endereco);
			return "redirect:/cliente/perfil";
		} catch (Exception e) {
			return "redirect:/cliente/endereco/cadastrar";
		}
	}
	
}
