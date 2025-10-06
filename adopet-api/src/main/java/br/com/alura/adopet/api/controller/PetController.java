package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository repository;

    @GetMapping
    public ResponseEntity<List<Pet>> listarTodosDisponiveis() {
        List<Pet> pets = repository.findAll();
        List<Pet> disponiveis = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getAdotado() == false) {
                disponiveis.add(pet);
            }
        }
        return ResponseEntity.ok(disponiveis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> buscarPorId(@PathVariable Long id) {
        Optional<Pet> optionalPet = repository.findById(id);
        if (optionalPet.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalPet.get());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Pet> atualizar(@RequestBody @Valid Pet pet) {
        if (pet.getId() == null || !repository.existsById(pet.getId())) {
            return ResponseEntity.notFound().build();
        }
        // Garante que o pet continue associado ao mesmo abrigo e status de adoção
        Pet petExistente = repository.findById(pet.getId()).get();
        pet.setAbrigo(petExistente.getAbrigo());
        pet.setAdotado(petExistente.getAdotado());

        Pet petAtualizado = repository.save(pet);
        return ResponseEntity.ok(petAtualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.ok("Pet deletado com sucesso!");
    }
}