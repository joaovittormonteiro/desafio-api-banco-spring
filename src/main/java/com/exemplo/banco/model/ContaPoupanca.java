package com.exemplo.banco.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("POUPANCA")
public class ContaPoupanca extends Conta {

    @Override
    public boolean podeSacar(BigDecimal valor) {
        return getSaldo().compareTo(valor) >= 0;
    }
}