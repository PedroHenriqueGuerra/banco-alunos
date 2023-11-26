package com.ada.banco.domain.usecase;

import com.ada.banco.domain.enums.TransacaoEnum;
import com.ada.banco.domain.gateway.TransacaoGateway;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.model.Transacao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class Transferir {
    private TransacaoGateway transacaoGateway;

    public Transacao execute(Conta contaOrigem, Conta contaDestino, BigDecimal valor) throws Exception{
        if(contaOrigem == null || contaDestino == null){
            throw new Exception(("Conta origem ou destino não encontrada."));
        }

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new Exception("O valor da transferência deve ser maior que R$ 0,00.");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        Transacao transacao = new Transacao(contaOrigem, contaDestino, valor, TransacaoEnum.TRANSFERENCIA);
        return transacaoGateway.salvar(transacao);
    }

}
