package br.com.TresDevsPI4.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.TresDevsPI4.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	@Query(nativeQuery = true, value = "select * from produto where status = true")
	public List<Produto> buscarTrue();

	@Query(nativeQuery = true, value = "select * from produto where id = ?")
	public List<Produto> buscarProduto(Integer id);
	
	@Query(nativeQuery = true, value = "select nome from produto where id = ?")
	public String buscarProdutoNome(Integer id);
	
}
