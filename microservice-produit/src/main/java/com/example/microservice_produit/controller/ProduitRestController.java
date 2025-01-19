package com.example.microservice_produit.controller;

import com.example.microservice_produit.model.Produit;
import com.example.microservice_produit.repository.ProduitRepository;
import org.springframework.web.bind.annotation.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitRestController {
    private final ProduitRepository produitRepository;

    public ProduitRestController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    // Get all products
    @GetMapping
    public List<Produit> listProduits() {
        return produitRepository.findAll();
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "produitService", fallbackMethod = "fallbackProduitById")
    @Retry(name = "produitService")
    public Produit produitById(@PathVariable Long id) throws InterruptedException {
        if (id == 1) {
            throw new RuntimeException("Simulated failure for testing Circuit Breaker");
        }
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit with ID " + id + " not found"));
    }

    // Fallback method for produitById
    public Produit fallbackProduitById(Long id, Throwable throwable) {
        Produit fallbackProduit = new Produit();
        fallbackProduit.setId(id);
        fallbackProduit.setNom("Fallback Produit");
        fallbackProduit.setPrix(0.0);
        return fallbackProduit;
    }

    // Create a new product
    @PostMapping
    public Produit saveProduit(@RequestBody Produit produit) {
        return produitRepository.save(produit);
    }

    // Update an existing product
    @PutMapping("/{id}")
    public Produit updateProduit(@RequestBody Produit produit, @PathVariable Long id) {
        produit.setId(id);
        return produitRepository.save(produit);
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public void deleteProduit(@PathVariable Long id) {
        produitRepository.deleteById(id);
    }
}
