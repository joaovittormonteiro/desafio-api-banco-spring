package com.exemplo.banco.service;

import com.exemplo.banco.model.Conta;
import com.exemplo.banco.model.Correntista;
import com.exemplo.banco.model.Transacao;
import com.exemplo.banco.model.TipoTransacao;
import com.exemplo.banco.repository.ContaRepository;
import com.exemplo.banco.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private CorrentistaService correntistaService;

    public Conta abrirConta(Conta conta, Long correntistaId) {
        Correntista correntista = correntistaService.buscarPorId(correntistaId);
        conta.setCorrentista(correntista);
        return contaRepository.save(conta);
    }

    public Conta buscarPorId(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada com id: " + id));
    }

    @Transactional
    public Conta depositar(Long contaId, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do depósito deve ser positivo.");
        }
        Conta conta = buscarPorId(contaId);
        conta.depositar(valor);
        contaRepository.save(conta);
        transacaoRepository.save(new Transacao(TipoTransacao.DEPOSITO, valor, conta));
        return conta;
    }

    @Transactional
    public Conta sacar(Long contaId, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do saque deve ser positivo.");
        }
        Conta conta = buscarPorId(contaId);
        conta.sacar(valor); // já valida saldo/limite internamente (podeSacar)
        contaRepository.save(conta);
        transacaoRepository.save(new Transacao(TipoTransacao.SAQUE, valor, conta));
        return conta;
    }

    public List<Transacao> extrato(Long contaId) {
        Conta conta = buscarPorId(contaId);
        return transacaoRepository.findByConta(conta);
    }
}