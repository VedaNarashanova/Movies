package mk.ukim.finki.movies.repository.Impl;

import mk.ukim.finki.movies.bootstrap.DataHolder;
import mk.ukim.finki.movies.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemoryUserRepository {
    public Optional<User> findByUsername(String username){//za da ne se povtaraat isti usernames
        return DataHolder.users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    //ja prebaruva listata po korisnicko ime i lozinka
    public Optional<User> findByUsernameAndPassword(String username, String password){
        return DataHolder.users.stream()
                .filter(user->user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst();
    }

    //ako postoi takov username sto veke postoi go briseme prethodniot i go dodavame nvioe
    public User saveOrUpdate(User user){
        DataHolder.users.removeIf(r->r.getUsername().equals(user.getUsername()));
        DataHolder.users.add(user);
        return user;
    }

    public void delete(String username){
        DataHolder.users.removeIf(user -> user.getUsername().equals(username));
    }
}