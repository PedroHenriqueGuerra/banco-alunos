package com.ada.banco.infra.controller;

import com.ada.banco.domain.model.Transacao;
import com.ada.banco.domain.usecase.Depositar;
import com.ada.banco.domain.usecase.Sacar;
import com.ada.banco.domain.usecase.Transferir;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private Depositar depositar;
    private Sacar sacar;
    private Transferir transferir;

    public TransacaoController(Depositar depositar, Sacar sacar, Transferir transferir) {
        this.depositar = depositar;
        this.sacar = sacar;
        this.transferir = transferir;
    }

    @PostMapping("/depositar")
    public ResponseEntity depositar(@RequestBody Transacao transacao) {
        try {
            depositar.execute(transacao.getContaOrigem(), transacao.getValor());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/sacar")
    public ResponseEntity sacar(@RequestBody Transacao transacao) {
        try {
            sacar.execute(transacao.getContaOrigem(), transacao.getValor());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/transferir")
    public ResponseEntity transferir(@RequestBody Transacao transacao) {
        try {
            transferir.execute(transacao.getContaOrigem(), transacao.getContaDestino(), transacao.getValor());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
