package br.com.senai.TesteConhecimentoII.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.senai.TesteConhecimentoII.entity.TiposCardapios;
import br.com.senai.TesteConhecimentoII.entity.enums.Status;
import br.com.senai.TesteConhecimentoII.service.TiposCardapiosService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/tipos-cardapios")
public class TiposCardapiosController {

	@Qualifier("tiposCardapiosServiceImpl")
	@Autowired
	private TiposCardapiosService service;

	@Autowired
	private MapConverter converter;

	@GetMapping
	public ResponseEntity<?> buscarPorNome(@RequestParam("nome") String nome, @RequestParam Optional<Integer> pagina) {
		Pageable paginacao = null;
		if (pagina != null) {
			paginacao = PageRequest.of(pagina.get(), 20);
		} else {
			paginacao = PageRequest.of(0, 20);
		}
		try {

			Page<TiposCardapios> tiposCardapios = service.buscarPorNome(nome, paginacao);

			return ResponseEntity.ok(converter.toJsonList(tiposCardapios));

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body("Ocorreu um erro ao buscar o tipo de cardapio. Motivo:" + e.getMessage());
		}

	}

	@GetMapping("id/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") Integer id) {
		TiposCardapios tipoEncontrado = service.buscarPorId(id);

		return ResponseEntity.ok(converter.toJsonMap(tipoEncontrado));
	}

	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody String jsonTipoCardapio) {

		try {
			ObjectMapper object = new ObjectMapper();

			TiposCardapios tipo = object.readValue(jsonTipoCardapio, TiposCardapios.class);
			if (tipo.getId() != null) {
				service.Inserir(tipo);
				return ResponseEntity.ok((tipo));
			}
			service.Inserir(tipo);
			return ResponseEntity.created(URI.create("/tipo-cardapio/id/" + tipo.getId())).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest()
					.body("Ocorreu um erro ao inserir o tipo de cardapio. Motivo:" + e.getMessage());

		}

	}

	@Transactional
	@PutMapping
	public ResponseEntity<?> alteraripo(@RequestBody String jsonTipoCardapio) {
		try {

			ObjectMapper objectMapper = new ObjectMapper();
			TiposCardapios tipo = objectMapper.readValue(jsonTipoCardapio, TiposCardapios.class);
			if (tipo.getId() != null) {
				service.Alterar(tipo);
				return ResponseEntity.ok((tipo));
			} else {
				throw new IllegalArgumentException("O id deve ser informado para modificar!");

			}

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body("Ocorreu um erro ao atualizar o tipo de cardapio. Motivo:" + e.getMessage());
		}
	}

	@Transactional
	@PatchMapping("/id/{id}/status/{status}")
	public ResponseEntity<?> atualizarStatus(@PathVariable("id") Integer id, @PathVariable("status") Status status) {

		try {
			service.atualizarStatus(id, status);
			return ResponseEntity.ok().build();

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body("Ocorreu um erro ao alterar o status do tipo de cardapio. Motivo:" + e.getMessage());

		}

	}

}
