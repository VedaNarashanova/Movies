package mk.ukim.finki.movies.model.exception;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(){
        super("Movie with that id has not been found");
    }
}
