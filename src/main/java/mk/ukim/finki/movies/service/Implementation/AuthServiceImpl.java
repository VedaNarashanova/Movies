package mk.ukim.finki.movies.service.Implementation;

import mk.ukim.finki.movies.model.User;
import mk.ukim.finki.movies.model.exception.InvalidArgumentsException;
import mk.ukim.finki.movies.model.exception.PasswordsDoNotMatchException;
import mk.ukim.finki.movies.model.exception.UserNotFoundException;
import mk.ukim.finki.movies.repository.jpa.JpaUserRepository;
import mk.ukim.finki.movies.service.AuthService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService{

    private final JpaUserRepository userRepository;

    public AuthServiceImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> listAll() {
        return this.userRepository.findAll();
    }
    @Override
    public User login(String username, String password) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty()){
            throw new InvalidArgumentsException();
        }
        return this.userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(UserNotFoundException::new);
    }



    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, LocalDate dateOfBirth) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty()){
            throw new InvalidArgumentsException();
        }

        if(!password.equals(repeatPassword)){
            throw new PasswordsDoNotMatchException();
        }

        return this.userRepository.save(new User(username, name, surname, password, dateOfBirth));
    }
}