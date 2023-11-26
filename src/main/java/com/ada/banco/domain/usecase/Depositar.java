package com.ada.banco.domain.usecase;

import com.ada.banco.domain.enums.TransacaoEnum;
import com.ada.banco.domain.gateway.TransacaoGateway;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.model.Transacao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class Depositar {

    private TransacaoGateway transacaoGateway;

    public Transacao execute(Conta conta, BigDecimal valor) throws Exception{
        if(conta == null){
            throw new Exception("Conta não encontrada.");
        }

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new Exception("O valor para depósito deve ser maior que R$0,00.");
        }

        valor = conta.getSaldo().add(valor);
        conta.setSaldo(valor);

        Transacao transacao = new Transacao(conta, valor, TransacaoEnum.DEPOSITO);
        return transacaoGateway.salvar(transacao);
    }
}
