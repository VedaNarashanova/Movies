package mk.ukim.finki.movies.repository.Impl;

import mk.ukim.finki.movies.bootstrap.DataHolder;
import mk.ukim.finki.movies.model.Production;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductionRepository {

    public List<Production> findAll(){
        return DataHolder.productions;
    }

    public Optional<Production> findById(Long id){
        return DataHolder.productions.stream()
                .filter(p->p.getId().equals(id))
                .findFirst();
    }

}
