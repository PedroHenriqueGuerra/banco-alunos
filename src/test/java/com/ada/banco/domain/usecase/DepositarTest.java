package com.ada.banco.domain.usecase;

import com.ada.banco.domain.enums.TipoContaEnum;
import com.ada.banco.domain.gateway.TransacaoGateway;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.model.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositarTest {

    @Mock
    private TransacaoGateway transacaoGateway;

    @InjectMocks
    private Depositar depositar;

    private Conta contaOrigemTest;

    @BeforeEach
    void beforeEach() {
        this.contaOrigemTest = new Conta(1L, 2L, 3L, BigDecimal.valueOf(100.0), "Pedro", "12345678900", TipoContaEnum.CONTA_CORRENTE);
    }

    @Test
    void depositoRealizadoComSucesso() throws Exception {
        when(transacaoGateway.salvar(any(Transacao.class))).thenReturn(new Transacao());

        Transacao transacao = depositar.execute(contaOrigemTest, BigDecimal.valueOf(50.0));

        assertEquals(150.0, contaOrigemTest.getSaldo().doubleValue());
        assertNotNull(transacao);

        verify(transacaoGateway, times(1)).salvar(any(Transacao.class));
    }

    @Test
    void contaNaoExistente() {
        Exception exception = assertThrows(Exception.class, () -> depositar.execute(null, BigDecimal.valueOf(10.0)));

        assertEquals("Conta não encontrada.", exception.getMessage());
    }

    @Test
    void valorMenorOuIgualAZero() {
        assertThrows(Exception.class, () -> depositar.execute(contaOrigemTest, BigDecimal.ZERO));

        verify(transacaoGateway, never()).salvar(any(Transacao.class));
    }
}
