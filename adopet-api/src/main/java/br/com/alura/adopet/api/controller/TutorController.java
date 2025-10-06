package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorRepository repository;

    @GetMapping
    public ResponseEntity<List<Tutor>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutor> buscarPorId(@PathVariable Long id) {
        Optional<Tutor> optionalTutor = repository.findById(id);
        if (optionalTutor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalTutor.get());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid Tutor tutor) {
        boolean telefoneJaCadastrado = repository.existsByTelefone(tutor.getTelefone());
        boolean emailJaCadastrado = repository.existsByEmail(tutor.getEmail());

        if (telefoneJaCadastrado || emailJaCadastrado) {
            return ResponseEntity.badRequest().body("Dados já cadastrados para outro tutor!");
        } else {
            repository.save(tutor);
            return ResponseEntity.ok("Tutor cadastrado com sucesso!");
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Tutor> atualizar(@RequestBody @Valid Tutor tutor) {
        if (tutor.getId() == null || !repository.existsById(tutor.getId())) {
            return ResponseEntity.notFound().build();
        }
        Tutor tutorAtualizado = repository.save(tutor);
        return ResponseEntity.ok(tutorAtualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.ok("Tutor deletado com sucesso!");
    }

}