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
class SacarTest {

    @Mock
    private TransacaoGateway transacaoGateway;

    @InjectMocks
    private Sacar sacar;

    private Conta contaOrigemTest;

    @BeforeEach
    void beforeEach() {
        this.contaOrigemTest = new Conta(1L, 2L, 3L, BigDecimal.valueOf(100.0), "Pedro", "12345678900", TipoContaEnum.CONTA_CORRENTE);
    }

    @Test
    void saqueRealizadoComSucesso() throws Exception {
        when(transacaoGateway.salvar(any(Transacao.class))).thenReturn(new Transacao());

        Transacao transacao = sacar.execute(contaOrigemTest, BigDecimal.valueOf(50.0));

        assertEquals(50.0, contaOrigemTest.getSaldo().doubleValue());
        assertNotNull(transacao);

        verify(transacaoGateway, times(1)).salvar(any(Transacao.class));
    }

    @Test
    void contaNaoExistente() {
        Exception exception = assertThrows(Exception.class, () -> sacar.execute(null, BigDecimal.valueOf(10.0)));

        assertEquals("Conta não encontrada.", exception.getMessage());
    }

    @Test
    void saldoInsuficiente() {
        Exception exception = assertThrows(Exception.class, () -> sacar.execute(contaOrigemTest, BigDecimal.valueOf(150.0)));

        assertEquals("Saldo insuficiente", exception.getMessage());
    }

    @Test
    void valorSaqueMenorOuIgualAZero() {
        Exception exception = assertThrows(Exception.class, () -> sacar.execute(contaOrigemTest, BigDecimal.ZERO));

        assertEquals("O valor para saque deve ser maior que R$0,00.", exception.getMessage());
    }
}
