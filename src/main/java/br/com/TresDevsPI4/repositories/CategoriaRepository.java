package br.com.TresDevsPI4.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.TresDevsPI4.model.Categoria;

@Repository
	public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, Integer> {
	
	public Iterable<Categoria> findByNomeContaining(String parteNome);

}
