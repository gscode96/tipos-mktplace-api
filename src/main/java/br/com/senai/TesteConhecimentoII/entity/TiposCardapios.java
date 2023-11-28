package br.com.senai.TesteConhecimentoII.entity;

import br.com.senai.TesteConhecimentoII.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tipos_cardapios")
@Entity(name = "tiposCardapios")
public class TiposCardapios {
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer Id;
	
	@Column(name = "nome")
	@Size(min = 3, max = 100, message = "O nome do tipo deve conter entre 3 e 100 caracteres!")
	@NotBlank(message = "O nome do tipo não pode ser nulo!")
	private String nome;
	
	@Column(name = "descricao")
	@NotBlank(message = "A descrição do tipo não pode ser nulo!")
	private String descricao;
	
	@Column(name = "status")
	@NotNull(message = "O status não pode ser nulo!")
	@Enumerated(value = EnumType.STRING)
	private Status status;
	
	
}
