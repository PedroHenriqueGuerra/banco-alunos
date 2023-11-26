package com.ada.banco.infra.gateway.bd;

import com.ada.banco.domain.gateway.ContaGateway;
import com.ada.banco.domain.model.Conta;
import org.springframework.stereotype.Component;

@Component
public class ContaGatewayDatabase implements ContaGateway {
    ContaRepository contaRepository;

    @Override
    public Conta buscarPorCpf(String cpf) {
        return this.contaRepository.findByCpf(cpf);
    }

    @Override
    public Conta salvar(Conta conta) {
        return this.contaRepository.save(conta);
    }
}
