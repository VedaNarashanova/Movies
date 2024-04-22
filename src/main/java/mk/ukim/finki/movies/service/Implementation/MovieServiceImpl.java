package mk.ukim.finki.movies.service.Implementation;

import mk.ukim.finki.movies.model.Movie;
import mk.ukim.finki.movies.model.Production;
import mk.ukim.finki.movies.model.exception.MovieNotFoundException;
import mk.ukim.finki.movies.model.exception.ProductionNotFoundException;
import mk.ukim.finki.movies.repository.jpa.JpaMovieRepository;
import mk.ukim.finki.movies.repository.jpa.JpaProductionRepository;
import mk.ukim.finki.movies.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final JpaMovieRepository movieRepository;
    private final JpaProductionRepository productionRepository;

    public MovieServiceImpl(JpaMovieRepository movieRepository,
                            JpaProductionRepository productionRepository) {
        this.movieRepository = movieRepository;
        this.productionRepository = productionRepository;
    }

    @Override
    public List<Movie> listAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> filterByText(String text) {
        return movieRepository.findBySummaryContainingIgnoreCaseOrTitleContainingIgnoreCase(text, text);
    }
    @Override
    public List<Movie> filterAboveRating(double ranking) {
        return this.movieRepository.findByRatingBetween(ranking, 10.0);
    }

    @Override
    public List<Movie> filterByTextAndAboveRating(String text, double ranking) {
        List<Movie> byText = movieRepository.findBySummaryContainingIgnoreCaseOrTitleContainingIgnoreCase(text, text);
        List<Movie> aboveRating = this.movieRepository.findByRatingBetween(ranking-0.1, 10.1);
        return byText.stream()
                .filter(aboveRating::contains)
                .collect(Collectors.toList());
    }

    @Override
    public void rateMovie(Long rateMovieId, double rateMovieRating) {
        if(this.movieRepository.findById(rateMovieId).isPresent()){
            Movie m = this.movieRepository.findById(rateMovieId).get();
            if(m.getRating() == -1) m.setRating(rateMovieRating);
            else m.setRating((m.getRating() + rateMovieRating) / 2);
            this.movieRepository.save(m);
        }
    }

    @Override
    public void delete(Long id) {
        this.movieRepository.deleteById(id);
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return this.movieRepository.findById(id);
    }

    @Override
    public void add(String movieTitle, String summary, Double rating, Long productionId) {
        Production production = productionRepository.findById(productionId)
                .orElseThrow(ProductionNotFoundException::new);

        this.movieRepository.save(new Movie(movieTitle, summary, rating, production));
    }

    @Override
    public void edit(Long movieId, String movieTitle, String summary, Double rating, Long productionId) {
        Production production = productionRepository.findById(productionId)
                .orElseThrow(ProductionNotFoundException::new);

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(MovieNotFoundException::new);

        movie.setTitle(movieTitle);
        movie.setSummary(summary);
        movie.setProduction(production);
        this.movieRepository.save(movie);
    }
}
