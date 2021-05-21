package br.com.TresDevsPI4.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.TresDevsPI4.model.Categoria;
import br.com.TresDevsPI4.model.Funcionario;
import br.com.TresDevsPI4.model.Produto;
import br.com.TresDevsPI4.repositories.CategoriaRepository;

@Controller
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping("/administrativo/categoria")
	public ModelAndView listar(Categoria categoria) {
		ModelAndView mv = new ModelAndView("/administrativo/categoria/listarCategoria");
		mv.addObject("listarCategoria", categoriaRepository.findAll());
		mv.addObject("categoria", categoria);
		return mv;
	}

	
	
	
	  @PostMapping("/administrativo/categoria/salvar") public String
	  salvar(Categoria categoria, BindingResult result) {
	  
	  categoriaRepository.save(categoria);
	  
	  return "redirect:/administrativo/categoria";
	  
	 }
	  
	  @GetMapping("/administrativo/inativar/categoria/{id}")
		public ModelAndView inativar(@PathVariable("id") int id) {
			Optional<Categoria> categoria = categoriaRepository.findById((int) id);

			if (categoria.get().getStatus() == false) {
				categoria.get().setStatus(true);
				categoriaRepository.save(categoria.get());
				return listar(new Categoria());
			}

			else

				categoria.get().setStatus(false);

			categoriaRepository.save(categoria.get());
			return listar(new Categoria());

		}
	  
	  
	  @GetMapping("/administrativo/editar/categoria/{id}")
		public ModelAndView editar(@PathVariable("id") int id) {
			Optional<Categoria> categoria = categoriaRepository.findById((int) id);
			return listar(categoria.get());
		}
	  
	 

}
