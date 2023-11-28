package br.com.senai.TesteConhecimentoII.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import br.com.senai.TesteConhecimentoII.entity.TiposCardapios;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Validated
public interface TiposCardapiosService {

	public Page<TiposCardapios> buscarPorNome(
			@NotBlank(message = "O nome é obrigatório") @Size(min = 3, max = 100, message = "O nome deve conter pelo menos 3 e 100 caracteres") String nome,
			@NotNull(message = "A paginação deve ser de no minimo 20!") Pageable paginacao);

	public TiposCardapios Inserir(@NotNull(message = "O tipo de cardapio é obrigatorio!") TiposCardapios tipoCardapio);

}
