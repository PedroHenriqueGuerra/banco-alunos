package com.ada.banco.domain.usecase;

import com.ada.banco.domain.enums.TipoContaEnum;
import com.ada.banco.domain.enums.TransacaoEnum;
import com.ada.banco.domain.gateway.ContaGateway;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistoricoTest {

    @Mock
    private ContaGateway contaGateway;

    @Mock private TransacaoGateway transacaoGateway;

    @InjectMocks
    private Historico historico;

    private Conta contaOrigemTest, contaDestinoTest;
    private Transacao transacaoTest;
    private List<Transacao> historicoTest;

    @BeforeEach
    public void beforeEach(){
        this.contaOrigemTest =  new Conta(1L, 2L, 3L, BigDecimal.valueOf(100.0), "Pedro", "12345678900", TipoContaEnum.CONTA_CORRENTE);
        this.contaDestinoTest = new Conta(2L, 3L, 4L, BigDecimal.valueOf(100.0), "Joao", "98765432100", TipoContaEnum.CONTA_CORRENTE);
        this.transacaoTest = new Transacao(1L, contaOrigemTest, contaDestinoTest, BigDecimal.valueOf(10.0), TransacaoEnum.DEPOSITO);
        this.historicoTest = new ArrayList<>();
        this.historicoTest.add(transacaoTest);
    }

    @Test
    public void mostrarHistorico() throws Exception{

        when(contaGateway.buscarPorCpf(anyString())).thenReturn(contaOrigemTest);

        when(transacaoGateway.buscarConta(eq(contaOrigemTest))).thenReturn(historicoTest);

        List<Transacao> historicoRetornado = historico.execute(contaOrigemTest);

        assertNotNull(historicoRetornado);
        assertEquals(historicoTest, historicoRetornado);

        verify(contaGateway, times(1)).buscarPorCpf(anyString());
        verify(transacaoGateway, times(1)).buscarConta(eq(contaOrigemTest));

    }

    @Test
    public void contaNaoExiste() throws Exception{
        when(contaGateway.buscarPorCpf(anyString())).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> historico.execute(contaOrigemTest));

        assertEquals("Conta não encotrada.", exception.getMessage());

        verify(contaGateway, times(1)).buscarPorCpf(anyString());
        verify(transacaoGateway, never()).buscarConta(any(Conta.class));
    }

}
