package mk.ukim.finki.movies.model.exception;

public class ProductionNotFoundException extends RuntimeException {

    public ProductionNotFoundException(){
        super("Production Not Fount");
    }
}
