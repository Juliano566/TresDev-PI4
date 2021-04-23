package br.com.TresDevsPI4.controller;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.TresDevsPI4.model.Cliente;
import br.com.TresDevsPI4.model.Funcionario;
import br.com.TresDevsPI4.repositories.ClienteRepository;
import br.com.TresDevsPI4.repositories.FuncionarioRepository;
import br.com.TresDevsPI4.services.Util;

@Controller
public class LoginController {

	@Autowired
	private FuncionarioRepository fun;

	@Autowired
	private ClienteRepository cli;

	@GetMapping("/login")
	public String exirMenu(Funcionario funcionario) {
		return "administrativo/login";
	}

	@PostMapping("/efetuarLogin")
	// Buscar no banco de dados funcionario com email e senha
	public String efetuarLogin(Funcionario funcionario, RedirectAttributes ra, HttpSession session) {

		Cliente cliente = new Cliente();
		cliente.setEmail(funcionario.getEmail());
		cliente.setSenha(funcionario.getSenha());
		System.out.println(Util.md5(funcionario.getSenha()));
		funcionario = this.fun.findByEmailAndSenha(funcionario.getEmail(), Util.md5(funcionario.getSenha()));

		if (funcionario != null) {
			// Guarda sessao o objeto usuario
			if (funcionario.getStatus()) {
				session.setAttribute("usuarioLogado", funcionario);
				return "redirect:/administrativo/login";

			} else {
				ra.addFlashAttribute("mensagem", "Usuario invativo");
				return "redirect:/login";
			}
		}
		
		// // Buscar no banco de dados cliente com email e senha
		cliente = this.cli.findByEmailAndSenha(cliente.getEmail(), Util.md5(cliente.getSenha()));
		if (cliente != null) {
			session.setAttribute("usuarioLogado", cliente);
			return "redirect:/";
		}
		ra.addFlashAttribute("mensagem", "Login/senha invalidos");
		return "redirect:/login";
	}

	@GetMapping("/administrativo/login")
	public ModelAndView listarEstoque() {
		ModelAndView mv = new ModelAndView("/administrativo/fragments/layoutAdm");
		return mv;
	}


}