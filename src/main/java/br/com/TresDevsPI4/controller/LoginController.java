package br.com.TresDevsPI4.controller;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.TresDevsPI4.model.Funcionario;
import br.com.TresDevsPI4.repositories.FuncionarioRepository;

@Controller
public class LoginController {

	@Autowired
	private FuncionarioRepository fun;

	@GetMapping("/login")
	public String exirMenu(Funcionario funcionario) {
		return "administrativo/login";
	}

	@PostMapping("/efetuarLogin")
	// Buscar no banco de dados funcionario com email e senha
	public String efetuarLogin(Funcionario funcionario, RedirectAttributes ra, HttpSession session) {
		funcionario = this.fun.findByEmailAndSenha(funcionario.getEmail(), funcionario.getSenha());

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
		ra.addFlashAttribute("mensagem", "Login/senha invalidos");
		return "redirect:/login";
	}

	@GetMapping("/administrativo/login")
	public ModelAndView listarEstoque() {
		ModelAndView mv = new ModelAndView("/administrativo/fragments/layoutAdm");
		return mv;
	}

//desloga sessao
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";

	}

}
