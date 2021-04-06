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

import br.com.TresDevsPI4.model.Categoria;
import br.com.TresDevsPI4.model.Funcionario;
import br.com.TresDevsPI4.repositories.FuncionarioRepository;

@Controller
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@GetMapping("/teste")
	public ModelAndView teste(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("/fragments/layoutAdm");
		mv.addObject("funcionario", funcionario);
		return mv;
	}

	@GetMapping("/administrativo/funcionario/cadastrar")
	public ModelAndView cadastrar(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("/administrativo/funcionario/cadastrarFuncionario");
		mv.addObject("funcionario", funcionario);
		return mv;
	}

	@GetMapping("/administrativo/funcionario/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/administrativo/funcionario/listarFuncionario");
		mv.addObject("listarFuncionario", funcionarioRepository.findAll());
		return mv;
	}

	@GetMapping("/administrativo/funcionario/listar/{numeroPagina}/{qtdePagina}")
	public ModelAndView obterFuncionarioPaginada(@PathVariable int numeroPagina, @PathVariable int qtdePagina) {
		ModelAndView mv = new ModelAndView("/administrativo/funcionario/listarFuncionario");
		if (qtdePagina >= 10)
			qtdePagina = 10;
		Pageable page = PageRequest.of(numeroPagina, qtdePagina);
		mv.addObject("listarFuncionario", funcionarioRepository.findAll(page));
		return mv;
	}

	@GetMapping("/administrativo/editar/funcionario/{id}")
	public ModelAndView editar(@PathVariable("id") int id) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById((int) id);
		return cadastrar(funcionario.get());
	}

	@PostMapping("/administrativo/salvar/funcionario")
	public ModelAndView salvar(Funcionario funcionario, BindingResult result, RedirectAttributes ra) {
		try {
			funcionarioRepository.save(funcionario);
			return cadastrar(new Funcionario());
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("/administrativo/funcionario/cadastrarFuncionario");
			ra.addFlashAttribute("mensagem", "Email invalido");
			return mv;
		}
	}

	@GetMapping("/administrativo/inativar/funcionario/{id}")
	public ModelAndView inativar(@PathVariable("id") int id) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById((int) id);

		if (funcionario.get().getStatus() == false) {
			funcionario.get().setStatus(true);
			funcionarioRepository.save(funcionario.get());
			return listar();
		}

		else

			funcionario.get().setStatus(false);

		funcionarioRepository.save(funcionario.get());
		return listar();

	}

}
