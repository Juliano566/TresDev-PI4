package br.com.TresDevsPI4.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.TresDevsPI4.model.Imagem;
import br.com.TresDevsPI4.model.Produto;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Integer> {

	@Query(nativeQuery = true, value = "delete from imagem where id_produto = ?")
	public void apagarImagem(Integer id);
	
	@Query(nativeQuery = true, value = "select * from imagem where id_produto = ?")
	public List<Imagem> buscarImagem(int id);

	
}
