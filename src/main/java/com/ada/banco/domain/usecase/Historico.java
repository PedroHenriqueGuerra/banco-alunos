package com.ada.banco.domain.usecase;

import com.ada.banco.domain.gateway.ContaGateway;
import com.ada.banco.domain.gateway.TransacaoGateway;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.model.Transacao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Historico {

    private ContaGateway contaGateway;
    private TransacaoGateway transacaoGateway;

    public Historico(ContaGateway contaGateway, TransacaoGateway transacaoGateway) {
        this.contaGateway = contaGateway;
        this.transacaoGateway = transacaoGateway;
    }

    public List<Transacao> execute(Conta conta) throws Exception {
        Conta contaBuscada = this.contaGateway.buscarPorCpf(conta.getCpf());

        if(contaBuscada == null) {
            throw new Exception("Conta não encotrada.");
        }

        return this.transacaoGateway.buscarConta(contaBuscada);
    }

}
