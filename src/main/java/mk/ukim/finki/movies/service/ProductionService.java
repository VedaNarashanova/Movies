package mk.ukim.finki.movies.service;

import mk.ukim.finki.movies.model.Production;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ProductionService{
    public List<Production> findAll();
    void add(String name, String country, String address);
    Optional<Production> findById(Long id);
    void edit(Long productionId, String name, String country, String address);
}
