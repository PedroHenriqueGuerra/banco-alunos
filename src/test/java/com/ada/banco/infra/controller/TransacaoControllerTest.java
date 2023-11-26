package com.ada.banco.infra.controller;

import com.ada.banco.domain.enums.TransacaoEnum;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.model.Transacao;
import com.ada.banco.domain.usecase.Depositar;
import com.ada.banco.domain.usecase.Sacar;
import com.ada.banco.domain.usecase.Transferir;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransacaoControllerTest {

    @Mock
    private Depositar depositar;

    @Mock
    private Sacar sacar;

    @Mock
    private Transferir transferir;

    @InjectMocks
    private TransacaoController transacaoController;

    private Transacao transacaoTest;
    private Conta contaOrigem;
    private Conta contaDestino;

    @BeforeEach
    void beforeEach() {
        // Instanciando as contas
        contaOrigem = new Conta();
        contaDestino = new Conta();

        this.transacaoTest = new Transacao(contaOrigem, contaDestino, BigDecimal.valueOf(50.0), TransacaoEnum.TRANSFERENCIA);
    }

    @Test
    void depositarComSucesso() throws Exception {
        ResponseEntity responseEntity = transacaoController.depositar(transacaoTest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(depositar, times(1)).execute(any(Conta.class), any(BigDecimal.class));
        verifyNoInteractions(sacar, transferir);
    }

    @Test
    void sacarComSucesso() throws Exception {
        ResponseEntity responseEntity = transacaoController.sacar(transacaoTest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(sacar, times(1)).execute(any(Conta.class), any(BigDecimal.class));
        verifyNoInteractions(depositar, transferir);
    }

    @Test
    void transferirComSucesso() throws Exception {
        ResponseEntity responseEntity = transacaoController.transferir(transacaoTest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(transferir, times(1)).execute(any(Conta.class), any(Conta.class), any(BigDecimal.class));
        verifyNoInteractions(depositar, sacar);
    }
}
