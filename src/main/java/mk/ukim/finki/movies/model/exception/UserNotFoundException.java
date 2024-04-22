package mk.ukim.finki.movies.model.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("This username does not exist");
    }
}
