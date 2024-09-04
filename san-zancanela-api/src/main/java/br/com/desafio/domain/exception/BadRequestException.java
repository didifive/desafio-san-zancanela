package br.com.desafio.domain.exception;

public class BadRequestException extends SanZancanelaApiException {
    public BadRequestException(String message) {
        super(message);
    }
}
