package mk.ukim.finki.movies.repository.jpa;

import mk.ukim.finki.movies.model.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductionRepository extends JpaRepository <Production,Long> {
}
