package com.exemplo.banco.model;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipo;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime data = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    public Transacao() {}

    public Transacao(TipoTransacao tipo, BigDecimal valor, Conta conta) {
        this.tipo = tipo;
        this.valor = valor;
        this.conta = conta;
    }

    // getters (sem setters — uma transação não deve ser alterada depois de criada)
    public Long getId() { return id; }
    public TipoTransacao getTipo() { return tipo; }
    public BigDecimal getValor() { return valor; }
    public LocalDateTime getData() { return data; }
    public Conta getConta() { return conta; }
}