package br.com.TresDevsPI4.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.TresDevsPI4.model.Cliente;
import br.com.TresDevsPI4.repositories.ClienteRepository;
import br.com.TresDevsPI4.services.Util;

@Controller
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;
//done 
	@GetMapping("/teste2")
	public ModelAndView teste(Cliente cliente) {
		ModelAndView mv = new ModelAndView("/fragments/layoutAdm");
		mv.addObject("cliente", cliente);
		return mv;
	}
//done
	@GetMapping("/cliente/cadastrar")
	public ModelAndView cadastrar(Cliente cliente) {
		ModelAndView mv = new ModelAndView("/cliente/cadastrarCliente");
		mv.addObject("cliente", cliente);
		return mv;
	}
//done
	@GetMapping("cliente/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/cliente/listarCliente");
		mv.addObject("listarCliente", clienteRepository.findAll());
		return mv;
	}
//done 
	@GetMapping("/cliente/listar/{numeroPagina}/{qtdePagina}")
	public ModelAndView obterClientePaginada(@PathVariable int numeroPagina, @PathVariable int qtdePagina) {
		ModelAndView mv = new ModelAndView("/cliente/listarCliente");
		if (qtdePagina >= 10)
			qtdePagina = 10;
		Pageable page = PageRequest.of(numeroPagina, qtdePagina);
		mv.addObject("listarCliente", clienteRepository.findAll(page));
		return mv;
	}
//done
	@GetMapping("/cliente/editar/cliente/{id}")
	public ModelAndView editar(@PathVariable("id") int id) {
		Optional<Cliente> cliente = clienteRepository.findById((int) id);
		return editar2(cliente.get());
	}
	
	@GetMapping("/cliente/editar")
	public ModelAndView editar2(Cliente cliente) {
		ModelAndView mv = new ModelAndView("/cliente/editarCliente");
		mv.addObject("cliente", cliente);
		return mv;
	}
	
	
//done
	@PostMapping("/cliente/salvar")
	public String salvar(Cliente cliente, BindingResult result, RedirectAttributes ra) {
		try {
			String senha =cliente.getSenha();
			senha =Util.md5(senha);
			cliente.setSenha(senha);
			
			clienteRepository.save(cliente);
			return "redirect:/cliente/cadastrar";
		} catch (Exception e) {
			ra.addFlashAttribute("mensagem", "Email invalido");
			return "redirect:/cliente/cadastrarCliente";
		}
	}
//done
	@GetMapping("/cliente/inativar/{id}")
	public ModelAndView inativar(@PathVariable("id") int id) {
		Optional<Cliente> cliente = clienteRepository.findById((int) id);

		if (cliente.get().getStatus() == false) {
			cliente.get().setStatus(true);
			clienteRepository.save(cliente.get());
			return listar();
		}

		else

			cliente.get().setStatus(false);

		clienteRepository.save(cliente.get());
		return listar();

	}

}
