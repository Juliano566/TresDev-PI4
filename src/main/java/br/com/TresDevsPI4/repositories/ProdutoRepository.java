package br.com.TresDevsPI4.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import br.com.TresDevsPI4.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {


	
}
