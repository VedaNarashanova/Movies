package mk.ukim.finki.movies.repository.jpa;

import mk.ukim.finki.movies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaMovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findBySummaryContainingIgnoreCaseOrTitleContainingIgnoreCase(String sumText, String titText);
    List<Movie> findByRatingBetween(Double min, Double max);
    List<Movie> findBySummaryContaining(String text);

}
