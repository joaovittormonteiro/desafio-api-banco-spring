package com.exemplo.banco.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CORRENTE")
public class ContaCorrente extends Conta {

    @Column(nullable = false)
    private BigDecimal limite = BigDecimal.ZERO;

    @Override
    public boolean podeSacar(BigDecimal valor) {
        return getSaldo().add(limite).compareTo(valor) >= 0;
    }

    public BigDecimal getLimite() { return limite; }
    public void setLimite(BigDecimal limite) { this.limite = limite; }
}