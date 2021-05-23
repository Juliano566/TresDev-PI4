package br.com.TresDevsPI4.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.TresDevsPI4.model.Compra;
import br.com.TresDevsPI4.model.Endereco;


@Repository
public interface CompraRepositorio extends JpaRepository<Compra, Integer> {

	@Query(nativeQuery = true, value = "select max(id) from compra")
	public Integer buscarIdCompra();
	
	@Query(nativeQuery = true, value = "select * from compra where id_cliente = ?")
	public List<Compra> listarCompra(Integer id);
	
	@Query(nativeQuery = true, value = "select endereco from compra where id = ?")
	public Integer bustarEndereco(Integer id);
	
	
	@Query(nativeQuery = true, value = "select * from compra where id = ?")
	public Optional<Compra> buscarId(Integer id);
	
	@Query(nativeQuery = true, value = "select forma_pagamento from compra where id = ?")
	public String buscarPagamento(Integer id);
}
