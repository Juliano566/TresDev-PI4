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
	public String salvar(Endereco endereco, BindingResult result, RedirectAttributes ra) {
		try {
			enderecoRepository.save(endereco);
			return "redirect:/cliente/perfil";
		} catch (Exception e) {
			return "redirect:/cliente/endereco/cadastrar";
		}
	}

	@GetMapping("cliente/listar/endereco/{id}")
	public ModelAndView listar(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("/cliente/listarEndereco");
		mv.addObject("listarEndereco", enderecoRepository.buscarEndereco(id));
		return mv;
	}

	@GetMapping("/cliente/endereco/editar/{id}")
	public ModelAndView editar(@PathVariable("id") int id) {
		Optional<Endereco> endereco = enderecoRepository.findById((int) id);
		return cadastrar(endereco.get());
	}

	@GetMapping("/cliente/endereco/inativar/{id}")
	public ModelAndView inativar(@PathVariable("id") int id) {
		Optional<Endereco> endereco = enderecoRepository.findById((int) id);
		if (endereco.get().getStatus() == false) {
			endereco.get().setStatus(true);
			enderecoRepository.save(endereco.get());
			return listar(endereco.get().getCliente());
		}
		else {
			endereco.get().setStatus(false);
			enderecoRepository.save(endereco.get());
			return listar(endereco.get().getCliente());
		}
	}
}
