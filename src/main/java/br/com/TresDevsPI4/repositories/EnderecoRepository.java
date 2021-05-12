package br.com.TresDevsPI4.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.TresDevsPI4.model.Compra;
import br.com.TresDevsPI4.model.Endereco;
import br.com.TresDevsPI4.model.Funcionario;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

	@Query(nativeQuery = true, value = "select * from endereco where cliente = ?")
	public List<Endereco> buscarEndereco(int id);
	
	@Query(nativeQuery = true, value = "select * from endereco where id = ?")
	public List<Endereco> buscarEndereco2(int id);
	
	
}
