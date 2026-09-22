package com.exemplo.banco.repository;

import com.exemplo.banco.model.Conta;
import com.exemplo.banco.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByConta(Conta conta);
}