package br.com.TresDevsPI4.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.TresDevsPI4.model.Categoria;
import br.com.TresDevsPI4.model.Produto;

@Repository
	public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, Integer> {
	
	public Iterable<Categoria> findByNomeContaining(String parteNome);
	
	@Query(nativeQuery = true, value = "select * from categoria where status = true")
	public List<Categoria> buscarCategorias();
	
	@Query(nativeQuery = true, value = "select nome from categoria where id = ?")
	public String buscar(Integer id);

}
