package br.com.TresDevsPI4.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.TresDevsPI4.model.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

	@Query(nativeQuery = true, value = "select * from funcionario where status = true")
	public List<Funcionario> buscarTrue();

	public Funcionario findByEmailAndSenha(String email, String senha);

}
