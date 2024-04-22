package mk.ukim.finki.movies.service;

import mk.ukim.finki.movies.model.Movie;
import mk.ukim.finki.movies.model.Production;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> listAll();
    List<Movie> filterByText(String text);
    List<Movie> filterAboveRating(double ranking);
    List<Movie> filterByTextAndAboveRating(String text, double ranking);

    void rateMovie(Long rateMovieId, double rateMovieRating);

    void delete(Long id);
    Optional<Movie> findById(Long id);

    void add(String movieTitle, String summary, Double rating, Long production);
    void edit(Long movieId, String movieTitle, String summary, Double rating, Long production);


}

