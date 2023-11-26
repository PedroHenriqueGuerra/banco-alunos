package com.ada.banco.domain.model;

import com.ada.banco.domain.enums.TransacaoEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TransacaoTest {


    Conta conta1 = new Conta(1L, 2L, 3L, BigDecimal.valueOf(100.0), "Jose", "12345678900");
    Conta conta2 = new Conta(2L, 3L, 4L, BigDecimal.valueOf(100.0), "Joao", "98765432100");
    Transacao transferir = new Transacao(1L, conta1, conta2, BigDecimal.valueOf(20.0), TransacaoEnum.TRANSFERENCIA);
    Transacao transferirIgual = new Transacao(1L, conta1, conta2, BigDecimal.valueOf(20.0), TransacaoEnum.TRANSFERENCIA);
    Transacao depositar = new Transacao(2L, conta1, BigDecimal.valueOf(10.0), TransacaoEnum.DEPOSITO);
    Transacao sacar = new Transacao(3L, conta2, BigDecimal.valueOf(10.0), TransacaoEnum.SAQUE);


    @Test
    public void transacaoEquals(){
        Assertions.assertEquals(transferir, transferirIgual);
    }

    @Test
    public void transacaoNotEquals(){
        Assertions.assertNotEquals(depositar, sacar);
    }


}
