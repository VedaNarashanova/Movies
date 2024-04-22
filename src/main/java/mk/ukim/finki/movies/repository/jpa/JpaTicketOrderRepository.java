package mk.ukim.finki.movies.repository.jpa;

import mk.ukim.finki.movies.model.TicketOrder;
import mk.ukim.finki.movies.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaTicketOrderRepository extends JpaRepository<TicketOrder,Long> {
    List<TicketOrder> findTicketOrderByMovieTitle(String title);

}
