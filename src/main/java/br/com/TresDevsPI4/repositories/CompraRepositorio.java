package br.com.TresDevsPI4.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.TresDevsPI4.model.Compra;
import br.com.TresDevsPI4.model.Endereco;



public interface CompraRepositorio extends JpaRepository<Compra, Integer> {

	@Query(nativeQuery = true, value = "select max(id) from compra")
	public Integer buscarIdCompra();
	
}
