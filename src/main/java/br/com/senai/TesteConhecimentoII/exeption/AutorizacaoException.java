package br.com.senai.TesteConhecimentoII.exeption;

public class AutorizacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AutorizacaoException(String mensagem) {
		super(mensagem);
	}

}
