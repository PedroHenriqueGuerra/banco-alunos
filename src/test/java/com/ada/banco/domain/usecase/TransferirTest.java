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
class TransferirTest {

    @Mock
    private TransacaoGateway transacaoGateway;

    @InjectMocks
    private Transferir transferir;

    private Conta contaOrigemTest, contaDestinoTest;

    @BeforeEach
    void beforeEach() {
        this.contaOrigemTest = new Conta(1L, 2L, 3L, BigDecimal.valueOf(100.0), "Pedro", "12345678900", TipoContaEnum.CONTA_CORRENTE);
        this.contaDestinoTest = new Conta(2L, 3L, 4L, BigDecimal.valueOf(50.0), "Joao", "98765432100", TipoContaEnum.CONTA_CORRENTE);
    }

    @Test
    void transferenciaRealizadaComSucesso() throws Exception {
        when(transacaoGateway.salvar(any(Transacao.class))).thenReturn(new Transacao());

        Transacao transacao = transferir.execute(contaOrigemTest, contaDestinoTest, BigDecimal.valueOf(30.0));

        assertEquals(70.0, contaOrigemTest.getSaldo().doubleValue());
        assertEquals(80.0, contaDestinoTest.getSaldo().doubleValue());
        assertNotNull(transacao);

        verify(transacaoGateway, times(1)).salvar(any(Transacao.class));
    }

    @Test
    void contaOrigemNaoExistente() {
        when(transacaoGateway.salvar(any(Transacao.class))).thenReturn(new Transacao());

        Exception exception = assertThrows(Exception.class, () -> transferir.execute(null, contaDestinoTest, BigDecimal.valueOf(10.0)));

        assertEquals("Conta origem ou destino não encontrada.", exception.getMessage());

        verify(transacaoGateway, never()).salvar(any(Transacao.class));
    }

    @Test
    void contaDestinoNaoExistente() {
        when(transacaoGateway.salvar(any(Transacao.class))).thenReturn(new Transacao());

        Exception exception = assertThrows(Exception.class, () -> transferir.execute(contaOrigemTest, null, BigDecimal.valueOf(10.0)));

        assertEquals("Conta origem ou destino não encontrada.", exception.getMessage());

        verify(transacaoGateway, never()).salvar(any(Transacao.class));
    }

    @Test
    void saldoInsuficiente() {
        Exception exception = assertThrows(Exception.class, () -> transferir.execute(contaOrigemTest, contaDestinoTest, BigDecimal.valueOf(200.0)));

        assertEquals("Saldo insuficiente", exception.getMessage());

        verify(transacaoGateway, never()).salvar(any(Transacao.class));
    }

    @Test
    void valorTransferenciaMenorOuIgualAZero() {
        Exception exception = assertThrows(Exception.class, () -> transferir.execute(contaOrigemTest, contaDestinoTest, BigDecimal.ZERO));

        assertEquals("O valor da transferência deve ser maior que R$ 0,00.", exception.getMessage());

        verify(transacaoGateway, never()).salvar(any(Transacao.class));
    }
}
