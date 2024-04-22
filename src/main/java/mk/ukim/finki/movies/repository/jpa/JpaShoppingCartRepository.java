package mk.ukim.finki.movies.repository.jpa;

import mk.ukim.finki.movies.model.ShoppingCart;
import mk.ukim.finki.movies.model.User;
import mk.ukim.finki.movies.model.enume.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findAllByUser(User user);
    List<ShoppingCart> findByUser(User user);
    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus status);
    List<ShoppingCart> findAllByUserAndDateCreatedBetween(User user, LocalDateTime from, LocalDateTime to);
    List<ShoppingCart> findShoppingCartByDateCreatedBetween(LocalDateTime from, LocalDateTime to);
    List<ShoppingCart> findAllByStatus(ShoppingCartStatus status);
}
