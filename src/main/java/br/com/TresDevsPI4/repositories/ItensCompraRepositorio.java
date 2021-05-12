package br.com.TresDevsPI4.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.TresDevsPI4.model.Compra;
import br.com.TresDevsPI4.model.ItensCompra;

@Repository
public interface ItensCompraRepositorio extends JpaRepository<ItensCompra, Long> {

	@Query(nativeQuery = true, value = "select * from itens_compra where id_compra = ?")
	public List<ItensCompra> listarCompra(Integer id);
	
	@Query(nativeQuery = true, value = "select * from itens_compra inner join produto on itens_compra.produto_id = produto.id "
			+ " where id_compra = ?")
	public List<ItensCompra> listarCompra2(Integer id);
	
	
}
