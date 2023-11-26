package com.ada.banco.domain.model;

import com.ada.banco.domain.gateway.ContaGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ContaTest {

    Conta conta1 = new Conta(1L, 2L, 3L, BigDecimal.valueOf(10.0), "Jose", "12345678900");
    Conta contaIgual = new Conta(1L, 2L, 3L, BigDecimal.valueOf(10.0), "Jose", "12345678900");
    Conta contaDiferente = new Conta(10L, 2L, 4L, BigDecimal.valueOf(100.0), "Joao", "98765432100");

    @Test
    public void contaEquals(){
        Assertions.assertEquals(conta1, contaIgual);
    }

    @Test
    public void contaNotEquals(){
        Assertions.assertNotEquals(conta1, contaDiferente);
    }


}
