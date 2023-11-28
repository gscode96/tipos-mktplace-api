package br.com.senai.TesteConhecimentoII.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.TesteConhecimentoII.entity.TiposCardapios;

@Repository
public interface TiposCardapiosRepository extends JpaRepository<TiposCardapios, Integer> {
	
	@Query(value = "SELECT t FROM tiposCardapios t WHERE t.nome LIKE :nome ORDER BY t.status",
			countQuery = "SELECT Count(t) FROM tiposCardapios t WHERE t.nome LIKE :nome")
	public Page<TiposCardapios> buscarPorNome(String nome, Pageable paginacao);
	
	@Query(value = "SELECT t FROM tiposCardapios t WHERE t.nome = :nome ORDER BY t.status")
	public TiposCardapios buscarPorNome(String nome);
	
	
	
}
