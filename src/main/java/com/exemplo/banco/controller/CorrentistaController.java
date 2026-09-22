package com.exemplo.banco.controller;

import com.exemplo.banco.model.Correntista;
import com.exemplo.banco.service.CorrentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/correntistas")
public class CorrentistaController {

    @Autowired
    private CorrentistaService correntistaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Correntista cadastrar(@RequestBody Correntista correntista) {
        return correntistaService.cadastrar(correntista);
    }

    @GetMapping
    public List<Correntista> listarTodos() {
        return correntistaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Correntista buscarPorId(@PathVariable Long id) {
        return correntistaService.buscarPorId(id);
    }
}