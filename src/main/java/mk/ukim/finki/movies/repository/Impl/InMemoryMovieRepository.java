package mk.ukim.finki.movies.repository.Impl;


import mk.ukim.finki.movies.bootstrap.DataHolder;
import mk.ukim.finki.movies.model.Movie;
import mk.ukim.finki.movies.model.Production;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryMovieRepository {

    public List<Movie> findAll(){
        return DataHolder.movies;
    }

    public List<Movie> searchMovies(String text){
        return DataHolder.movies.stream()
                .filter(m->m.getTitle().contains(text) || m.getSummary().contains(text))
                .collect(Collectors.toList());
    }

    public Movie findMovieByTitle(String title) {
        return DataHolder.movies.stream()
                .filter(movie -> movie.getTitle().equals(title))
                .findFirst().get();
    }

    public void deleteById(Long id){
        DataHolder.movies.removeIf(movie->movie.getId().equals(id));
    }

    public Optional<Movie> findById(Long id){
        return DataHolder.movies.stream()
                .filter(m->m.getId().equals(id))
                .findFirst();
    }
    public void add(String title, String summary, Double rating, Production production) {
        DataHolder.movies.add(new Movie(title,summary,rating,production));
    }
    public void edit(Movie movie, String title, String summary, Double rating, Production production) {
        movie.setTitle(title);
        movie.setSummary(summary);
        movie.setRating(rating);
        movie.setProduction(production);
    }

//    public List<Movie> getMoviesByTitle(List<String> movieTitles) {
//        return movieTitles.stream()
//                .map(this::findMovieByTitle)
//                .collect(Collectors.toList());
//    }



}

