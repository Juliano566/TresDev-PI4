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
import br.com.TresDevsPI4.model.Endereco;
import br.com.TresDevsPI4.repositories.ClienteRepository;
import br.com.TresDevsPI4.repositories.EnderecoRepository;
import br.com.TresDevsPI4.services.Util;

@Controller
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository endereceRepository;

//done 
	@GetMapping("/teste2")
	public ModelAndView teste(Cliente cliente) {
		ModelAndView mv = new ModelAndView("/fragments/layoutAdm");
		mv.addObject("cliente", cliente);
		return mv;
	}

	@GetMapping("/cep")
	public ModelAndView cep(Cliente cliente) {
		ModelAndView mv = new ModelAndView("/cep");
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
	@GetMapping("/cliente/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/cliente/listarCliente");
		mv.addObject("listarCliente", clienteRepository.findAll());
		return mv;
	}

	@GetMapping("/cliente/perfil")
	public ModelAndView perfil() {
		ModelAndView mv = new ModelAndView("/cliente/layoutCLi");
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
	public ModelAndView salvar(Cliente cliente, BindingResult result, RedirectAttributes ra) {

		ModelAndView mv = new ModelAndView("/cliente/cadastrarCliente");

		String emailF = clienteRepository.buscarEmailFuncionario(cliente.getEmail());
		String emailC = clienteRepository.buscarEmailCliente(cliente.getEmail());
		String cpf = clienteRepository.buscarCPF(cliente.getCpf());

		if (emailF == null && emailC == null) {
			if (cpf == null) {
				String senha = cliente.getSenha();
				senha = Util.md5(senha);
				cliente.setSenha(senha);
				clienteRepository.save(cliente);

				if (cliente.getCopiaEndereco() != null) {
					Integer id = clienteRepository.buscarId(cliente.getEmail());
					Endereco endereco = new Endereco();

					endereco.setCliente(id);
					endereco.setCep(Integer.parseInt(cliente.getCep()));
					endereco.setCidade(cliente.getCidade());
					endereco.setLogradouro(cliente.getLogradouro());
					endereco.setNumero(Integer.parseInt(cliente.getNumero()));
					endereco.setComplemento(cliente.getComplemento());
					endereco.setRua(cliente.getLogradouro());
					endereco.setBairro(cliente.getBairro());
					endereco.setStatus(true);
					endereceRepository.save(endereco);

				}

				mv = new ModelAndView("/administrativo/login");
				mv.addObject("cliente", cliente);
				return mv;
			}
			ra.addFlashAttribute("mensagemCpf", "CPF Invalido");
			mv.addObject("mensagemCpf", "CPF Invalido");
			mv.addObject("cliente", cliente);
			return mv;

		}

		if (cpf == null) {
			ra.addFlashAttribute("mensagemEmail", "Email Invalido");

			mv.addObject("mensagemEmail", "Email Invalido");
			mv.addObject("cliente", cliente);
			return mv;

		}

		ra.addFlashAttribute("mensagemCpf", "CPF Invalido");
		ra.addFlashAttribute("mensagemEmail", "Email Invalido");

		mv.addObject("mensagemEmail", "Email Invalido");
		mv.addObject("mensagemCpf", "CPF Invalido");
		mv.addObject("cliente", cliente);
		return mv;

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
