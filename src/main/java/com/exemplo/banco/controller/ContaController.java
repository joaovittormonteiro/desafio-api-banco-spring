package com.exemplo.banco.controller;

import com.exemplo.banco.dto.ValorRequest;
import com.exemplo.banco.model.Conta;
import com.exemplo.banco.model.Transacao;
import com.exemplo.banco.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Conta abrirConta(@RequestBody Conta conta, @RequestParam Long correntistaId) {
        return contaService.abrirConta(conta, correntistaId);
    }

    @GetMapping("/{id}")
    public Conta buscarPorId(@PathVariable Long id) {
        return contaService.buscarPorId(id);
    }

    @PostMapping("/{id}/depositar")
    public Conta depositar(@PathVariable Long id, @RequestBody ValorRequest request) {
        return contaService.depositar(id, request.getValor());
    }

    @PostMapping("/{id}/sacar")
    public Conta sacar(@PathVariable Long id, @RequestBody ValorRequest request) {
        return contaService.sacar(id, request.getValor());
    }

    @GetMapping("/{id}/extrato")
    public List<Transacao> extrato(@PathVariable Long id) {
        return contaService.extrato(id);
    }
}