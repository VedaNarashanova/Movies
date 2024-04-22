package mk.ukim.finki.movies.service;

import mk.ukim.finki.movies.model.User;

import java.time.LocalDate;
import java.util.List;

public interface AuthService {
    List<User> listAll();
    User login(String username, String password);
    User register(String username, String password, String repeatPassword, String name, String surname, LocalDate dateOfBirth);

}
