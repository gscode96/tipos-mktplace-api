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
		Page<TiposCardapios> tiposCardapios = repository.buscarPorNome(nome + "%", paginacao);
		Preconditions.checkNotNull(tiposCardapios, "Não foi encontrado tipo de cardapio para o nome informado!");
		return tiposCardapios;
	}

	@Override
	public TiposCardapios Inserir(TiposCardapios tipoCardapio) {
		TiposCardapios tipoEncontrado = repository.buscarPorNome(tipoCardapio.getNome().toUpperCase());
		if (tipoEncontrado != null) {
			throw new IllegalArgumentException("O nome do tipo de cardapio já esta em uso!");
		}
		Preconditions.checkArgument(tipoCardapio.getStatus().equals(Status.A), "O status deve ser A ou I");
		Preconditions.checkArgument(tipoCardapio.getStatus().equals(Status.I), "O status deve ser A ou I");
		
		tipoCardapio.setNome(tipoCardapio.getNome().toUpperCase());
		TiposCardapios tipoSalvo = repository.save(tipoCardapio);

		return tipoSalvo;
	}

}
