package br.com.senai.TesteConhecimentoII.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.TesteConhecimentoII.entity.TiposCardapios;
import br.com.senai.TesteConhecimentoII.entity.enums.Status;
import br.com.senai.TesteConhecimentoII.repository.TiposCardapiosRepository;
import br.com.senai.TesteConhecimentoII.service.TiposCardapiosService;
import jakarta.validation.constraints.NotNull;

@Service
public class TiposCardapiosServiceImpl implements TiposCardapiosService {

	@Autowired
	private TiposCardapiosRepository repository;

	@Override
	public Page<TiposCardapios> buscarPorNome(String nome, Pageable paginacao) {
		Page<TiposCardapios> tiposCardapios = repository.buscarPorNome(nome.toUpperCase() + "%", paginacao);
		Preconditions.checkNotNull(tiposCardapios, "Não foi encontrado tipo de cardapio para o nome informado!");
		return tiposCardapios;
	}
	
	@Override
	public TiposCardapios buscarPorId(Integer id) {
		if (id < 1) {
			throw new IllegalArgumentException("O id deve ser possitivo e maior que zero!");
		}
		TiposCardapios tipoEncontrado = repository.buscarPorId(id);
		
		Preconditions.checkNotNull(tipoEncontrado, "Não foi encontrado tipo para o id informado!");
		
		return tipoEncontrado;
	}
	

	@Override
	public TiposCardapios Inserir(TiposCardapios tipoCardapio) {
		TiposCardapios tipoEncontrado = repository.buscarPorNome(tipoCardapio.getNome().toUpperCase());
		if (tipoEncontrado != null) {
			throw new IllegalArgumentException("O nome do tipo de cardapio já esta em uso!");
		}
		
		tipoCardapio.setNome(tipoCardapio.getNome().toUpperCase());
		TiposCardapios tipoSalvo = repository.save(tipoCardapio);

		return tipoSalvo;
	}
	
	@Override
	public TiposCardapios Alterar(TiposCardapios tipoCardapio) {
		
		tipoCardapio.setNome(tipoCardapio.getNome().toUpperCase());
		TiposCardapios tipoSalvo = repository.save(tipoCardapio);

		return tipoSalvo;
	}

	@Override
	public void atualizarStatus( Integer id, Status status) {
		if (id < 1) {
			throw new IllegalArgumentException("O id deve ser possitivo e maior que zero!");
		}
		TiposCardapios tipoEncontrado = repository.buscarPorId(id);
		Preconditions.checkNotNull(tipoEncontrado, "Não encontrado tipo para o id informado!");
		Preconditions.checkArgument(!tipoEncontrado.getStatus().equals(status), "O status informado já esta salvo!");
		repository.atualizarPor(id, status);
	}



}
