package com.ada.banco.domain.usecase;

import com.ada.banco.domain.enums.TipoContaEnum;
import com.ada.banco.domain.gateway.ContaGateway;
import com.ada.banco.domain.model.Conta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Iniciar os mocks sem necessidade de iniciar no beforeEach
public class CriarNovaContaTest {
    @Mock
    private ContaGateway contaGateway;
    @InjectMocks
    private CriarNovaConta criarNovaConta;

    @Test
    public void deveCriarNovaConta() throws Exception {
        // Given
        Conta conta =
                new Conta(1L, 3L, BigDecimal.ZERO, "Pedro", "222222222", TipoContaEnum.CONTA_CORRENTE);

        Conta novaConta =
                new Conta(1L, 1L, 3L, BigDecimal.ZERO, "Pedro", "222222222");

        // When
        // Mocks response
        when(contaGateway.buscarPorCpf(conta.getCpf())).thenReturn(null); // stub
        when(contaGateway.salvar(any())).thenReturn(conta);

       criarNovaConta.execute(conta);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertEquals("Pedro", novaConta.getTitular())
        );

        verify(contaGateway, times(1)).buscarPorCpf(conta.getCpf());
        verify(contaGateway, times(1)).salvar(any());
    }

    @Test
    public void deveLancarExceptionCasoAContaJaExista() {
        // Given
        Conta conta =
                new Conta(2L, 3L, BigDecimal.ZERO, "Pedro", "123456789", TipoContaEnum.CONTA_CORRENTE);

        // When Then
        when(contaGateway.buscarPorCpf(conta.getCpf())).thenReturn(conta);

        Throwable throwable = Assertions.assertThrows(
                Exception.class,
                () -> criarNovaConta.execute(conta)
        );

        Assertions.assertEquals("Usuario ja possui uma conta", throwable.getMessage());

        verify(contaGateway, times(1)).buscarPorCpf(conta.getCpf());
        verify(contaGateway, never()).salvar(conta);
    }
}