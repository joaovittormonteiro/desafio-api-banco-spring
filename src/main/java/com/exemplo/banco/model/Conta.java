package com.exemplo.banco.model;

import javax.persistence.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ContaCorrente.class, name = "CORRENTE"),
    @JsonSubTypes.Type(value = ContaPoupanca.class, name = "POUPANCA")
})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_conta")
public abstract class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(nullable = false)
    private BigDecimal saldo = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "correntista_id", nullable = false)
    @JsonIgnoreProperties("contas")
    private Correntista correntista;

    public abstract boolean podeSacar(BigDecimal valor);

    public void depositar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
    }

    public void sacar(BigDecimal valor) {
        if (!podeSacar(valor)) {
            throw new IllegalStateException("Saldo insuficiente para saque.");
        }
        this.saldo = this.saldo.subtract(valor);
    }

    public Long getId() { return id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public BigDecimal getSaldo() { return saldo; }
    public Correntista getCorrentista() { return correntista; }
    public void setCorrentista(Correntista correntista) { this.correntista = correntista; }
}