package br.com.desafio.domain.exception;

public class EntityNotFoundException extends SanZancanelaApiException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
