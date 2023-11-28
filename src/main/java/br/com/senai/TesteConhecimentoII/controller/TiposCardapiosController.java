package br.com.senai.TesteConhecimentoII.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import br.com.senai.TesteConhecimentoII.entity.TiposCardapios;
import br.com.senai.TesteConhecimentoII.entity.enums.Status;
import br.com.senai.TesteConhecimentoII.service.TiposCardapiosService;

@RestController
@RequestMapping("/tiposCardapios")
public class TiposCardapiosController {

	@Qualifier("tiposCardapiosServiceImpl")
	@Autowired
	private TiposCardapiosService service;

	@Autowired
	private MapConverter converter;

	@GetMapping("nome/{nome}")
	public ResponseEntity<?> buscarPorNome(@PathVariable("nome") String nome) {
		Pageable paginacao = PageRequest.of(0, 20);
		try {

			Page<TiposCardapios> tiposCardapios = service.buscarPorNome(nome, paginacao);

			return ResponseEntity.ok(converter.toJsonList(tiposCardapios));

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body("Ocorreu um erro ao buscar o tipo de cardapio. Motivo:" + e.getMessage());
		}

	}

	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody String jsonTipoCardapio) {

		try {
			ObjectMapper object = new ObjectMapper();

			TiposCardapios tipo = object.readValue(jsonTipoCardapio, TiposCardapios.class);

			service.Inserir(tipo);
			return ResponseEntity.created(URI.create("/tipoCardapio/id/" + tipo.getId())).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest()
					.body("Ocorreu um erro ao inserir o tipo de cardapio. Motivo:" + e.getMessage());

		}

	}

}
