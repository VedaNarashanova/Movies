package mk.ukim.finki.movies.service.Implementation;


import mk.ukim.finki.movies.model.exception.ProductionNotFoundException;
import mk.ukim.finki.movies.repository.jpa.JpaProductionRepository;
import mk.ukim.finki.movies.service.ProductionService;
import mk.ukim.finki.movies.model.Production;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductionServiceImpl implements ProductionService {

    private final JpaProductionRepository productionRepository;

    public ProductionServiceImpl(JpaProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    @Override
    public List<Production> findAll() {
        return this.productionRepository.findAll();
    }

    @Override
    public void add(String name, String country, String address) {
        this.productionRepository.save(new Production(name, country, address));
    }

    @Override
    public Optional<Production> findById(Long id) {
        return productionRepository.findById(id);
    }

    @Override
    public void edit(Long productionId, String name, String country, String address) {
        Production production = productionRepository.findById(productionId)
                .orElseThrow(ProductionNotFoundException::new);
        production.setName(name);
        production.setCountry(country);
        production.setAddress(address);
        this.productionRepository.save(production);
    }
}
