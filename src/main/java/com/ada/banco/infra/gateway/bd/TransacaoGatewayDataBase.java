package com.ada.banco.infra.gateway.bd;

import com.ada.banco.domain.gateway.TransacaoGateway;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.model.Transacao;

import java.util.List;

public class TransacaoGatewayDataBase implements TransacaoGateway {

    private TransacaoRepository transacaoRepository;

    @Override
    public List<Transacao> buscarConta(Conta conta) {
        return this.transacaoRepository.findAllByConta(conta);
    }

    @Override
    public Transacao salvar(Transacao transacao) {
        return this.transacaoRepository.save(transacao);
    }
}
