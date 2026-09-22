package com.exemplo.banco.service;

import com.exemplo.banco.model.Correntista;
import com.exemplo.banco.repository.CorrentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorrentistaService {

    @Autowired
    private CorrentistaRepository correntistaRepository;

    public Correntista cadastrar(Correntista correntista) {
        return correntistaRepository.save(correntista);
    }

    public List<Correntista> listarTodos() {
        return correntistaRepository.findAll();
    }

    public Correntista buscarPorId(Long id) {
        return correntistaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Correntista não encontrado com id: " + id));
    }
}