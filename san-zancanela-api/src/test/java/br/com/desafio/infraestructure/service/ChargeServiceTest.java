package br.com.desafio.infraestructure.service;

import br.com.desafio.domain.exception.EntityNotFoundException;
import br.com.desafio.infraestructure.entity.ChargeEntity;
import br.com.desafio.infraestructure.repository.ChargeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static br.com.desafio.util.Assertions.assertThrowsWithMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ChargeServiceTest {

    @Mock
    private ChargeRepository repository;

    @InjectMocks
    private ChargeService chargeService;

    @Test
    void testFindById() {
        String id = "123";
        ChargeEntity expectedCharge = new ChargeEntity();
        expectedCharge.setId(id);
        expectedCharge.setOriginalAmount(BigDecimal.TEN);

        when(repository.findById(id)).thenReturn(Optional.of(expectedCharge));

        assertEquals(expectedCharge.getOriginalAmount(),
                chargeService.getOriginalAmountFromId(id));

        verify(repository, times(1)).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        String invalidId = "321";
        when(repository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrowsWithMessage(EntityNotFoundException.class,
                () -> chargeService.getOriginalAmountFromId(invalidId),
                "Charge not found");

        verify(repository, times(1)).findById(invalidId);
    }
}