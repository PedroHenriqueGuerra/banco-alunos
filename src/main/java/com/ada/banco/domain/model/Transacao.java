package com.ada.banco.domain.model;

import com.ada.banco.domain.enums.TransacaoEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Conta contaOrigem;

    @ManyToOne
    private Conta contaDestino;

    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TransacaoEnum tipoTransacao;

    public Transacao() {
    }

    public Transacao(Conta contaOrigem, BigDecimal valor, TransacaoEnum transacaoEnum) { //Deposito e Saque
        this.contaOrigem = contaOrigem;
        this.valor = valor;
        this.tipoTransacao = transacaoEnum;
    }

    public Transacao(Long id, Conta contaOrigem, BigDecimal valor, TransacaoEnum transacaoEnum) { //Deposito e Saque
        this.contaOrigem = contaOrigem;
        this.valor = valor;
        this.tipoTransacao = transacaoEnum;
    }

    public Transacao(Conta contaOrigem, Conta contaDestino, BigDecimal valor, TransacaoEnum transacaoEnum) { //Transferencia
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.tipoTransacao = transacaoEnum;
    }

    public Transacao(Long id, Conta contaOrigem, Conta contaDestino, BigDecimal valor, TransacaoEnum tipoTransacao) {
        this.id = id;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Conta conta) {
        this.contaOrigem = conta;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TransacaoEnum getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TransacaoEnum tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return Objects.equals(id, transacao.id) && tipoTransacao == transacao.tipoTransacao;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoTransacao);
    }
}