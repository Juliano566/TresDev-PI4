package br.com.TresDevsPI4.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.TresDevsPI4.model.Categoria;
import br.com.TresDevsPI4.repositories.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
		
	@PostMapping
	public @ResponseBody Categoria novaCategoria(@RequestParam String nome) {
		Categoria categoria = new Categoria(nome);
		categoriaRepository.save(categoria);
		
		return categoria;
	}
	
	@GetMapping
	public Iterable<Categoria> obterCategorias() {
		return categoriaRepository.findAll();
	}
	
	@GetMapping(path="/nome/{parteNome}")
	public Iterable<Categoria>obterCategoriaPorNome(@PathVariable String parteNome){
		return categoriaRepository.findByNomeContaining(parteNome);
	}
	
	
	@GetMapping(path="/pagina/{numeroPagina}/{qtdePagina}")
	public Iterable<Categoria> obterCategoriasPaginada(
			@PathVariable int numeroPagina, @PathVariable int qtdePagina) {
		if(qtdePagina >= 5) qtdePagina = 5;
		Pageable page = PageRequest.of(numeroPagina, qtdePagina);
		return categoriaRepository.findAll(page);
	}
	
	@GetMapping(path="/{id}")
	public Optional<Categoria> obterCategoriPorId(@PathVariable int id) {
		return categoriaRepository.findById(id);
		
	}
	
	@PutMapping
	public Categoria alterarCategoria( Categoria categoria) {
		categoriaRepository.save(categoria);
		return categoria;
	}
	
	
	@DeleteMapping(path = "/delete/{id}")
	public void excluirCategoria(@PathVariable int id) {
		 categoriaRepository.deleteById(id);
	}
	
	
	
}
