package com.ada.banco.infra.controller;

import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.usecase.CriarNovaConta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContaControllerTest {

    @Mock
    private CriarNovaConta criarNovaConta;

    @InjectMocks
    private ContaController contaController;

    private Conta contaTest;

    @BeforeEach
    void beforeEach() {
        this.contaTest = new Conta(1L, 2L, 3L, null, "Pedro", "12345678900", null);
    }

    @Test
    void criarContaComSucesso() throws Exception {
        when(criarNovaConta.execute(any(Conta.class))).thenReturn(contaTest);

        ResponseEntity responseEntity = contaController.criarConta(contaTest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(contaTest, responseEntity.getBody());

        verify(criarNovaConta, times(1)).execute(any(Conta.class));
    }

    @Test
    void criarContaComErro() throws Exception {
        when(criarNovaConta.execute(any(Conta.class))).thenThrow(new Exception("Erro ao criar conta"));

        ResponseEntity responseEntity = contaController.criarConta(contaTest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Erro ao criar conta", responseEntity.getBody());

        verify(criarNovaConta, times(1)).execute(any(Conta.class));
    }
}
