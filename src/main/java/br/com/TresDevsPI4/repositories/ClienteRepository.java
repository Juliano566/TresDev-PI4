package br.com.TresDevsPI4.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.TresDevsPI4.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	@Query(nativeQuery = true, value = "select * from cliente where status = true")
	public List<Cliente> buscarTrue();

	public Cliente findByEmailAndSenha(String email, String senha);
	
	@Query(nativeQuery = true, value = "select email from funcionario where email = ?")
	public String buscarEmailFuncionario(String email);
	
	@Query(nativeQuery = true, value = "select email from cliente where email = ?")
	public String buscarEmailCliente(String email);
	
	@Query(nativeQuery = true, value = "select email from cliente where cpf = ?")
	public String buscarCPF(String cpf);
	
	@Query(nativeQuery = true, value = "select id from cliente where email = ?")
	public Integer buscarId(String email);

}
