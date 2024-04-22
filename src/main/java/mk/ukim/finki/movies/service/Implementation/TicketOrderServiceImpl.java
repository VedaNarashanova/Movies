package mk.ukim.finki.movies.service.Implementation;

import mk.ukim.finki.movies.model.ShoppingCart;
import mk.ukim.finki.movies.model.TicketOrder;
import mk.ukim.finki.movies.model.User;
import mk.ukim.finki.movies.model.enume.ShoppingCartStatus;
import mk.ukim.finki.movies.model.exception.UserNotFoundException;
import mk.ukim.finki.movies.repository.jpa.JpaShoppingCartRepository;
import mk.ukim.finki.movies.repository.jpa.JpaTicketOrderRepository;
import mk.ukim.finki.movies.repository.jpa.JpaUserRepository;
import mk.ukim.finki.movies.service.TicketOrderService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class TicketOrderServiceImpl implements TicketOrderService {
    private final JpaTicketOrderRepository ticketOrderRepository;
    private final JpaShoppingCartRepository shoppingCartRepository;
    private final JpaUserRepository userRepository;


    public TicketOrderServiceImpl(JpaTicketOrderRepository ticketOrderRepository,
                                  JpaShoppingCartRepository shoppingCartRepository,
                                  JpaUserRepository userRepository) {
        this.ticketOrderRepository = ticketOrderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TicketOrder> listAll() {
        return ticketOrderRepository.findAll();
    }

    @Override
    public TicketOrder placeOrder(String movieTitle, int numberOfTickets) {
        TicketOrder tickedOrder = new TicketOrder(movieTitle, numberOfTickets);
        ticketOrderRepository.save(tickedOrder);
        return tickedOrder;
    }
    @Override
    public Map<String, Long> getTicketsPerMovie() {
        List<ShoppingCart> shoppingCarts = this.shoppingCartRepository.findAllByStatus(ShoppingCartStatus.FINISHED);

        return shoppingCarts.stream()
                .flatMap(cart -> cart.getTicketOrders().stream()
                        .collect(Collectors.groupingBy(TicketOrder::getMovieTitle,
                                Collectors.summingLong(TicketOrder::getNumberOfTickets)))
                        .entrySet().stream()
                        .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::sum));
    }


    @Override
    public Map<Long, String> getUserNamePerMovie() {
        List<ShoppingCart> shoppingCarts = this.shoppingCartRepository.findAll();
        return shoppingCarts.stream()
                .flatMap(cart -> cart.getTicketOrders().stream().map(order -> new AbstractMap.SimpleEntry<>(order.getId(), cart.getUser().getUserFullName().getName())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    @Override
    public Map<Long, String> getStatusOfTickets() {
        List<ShoppingCart> shoppingCarts = this.shoppingCartRepository.findAll();
        return shoppingCarts.stream()
                .flatMap(cart -> cart.getTicketOrders().stream().map(order -> new AbstractMap.SimpleEntry<>(order.getId(), cart.getStatus().toString())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public List<TicketOrder> filterByMovie(String title) {
        return ticketOrderRepository.findTicketOrderByMovieTitle(title);
    }

    @Override
    public List<TicketOrder> filterByUser(String name) {
        //TODO change this logic
        String username = this.userRepository.findAll().stream()
                .filter(u -> u.getUserFullName().getName().equals(name)).toList()
                .stream().map(User::getUsername).findFirst().get();

        Optional<User> user = this.userRepository.findByUsername(username);
        if(user.isPresent()){
            List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUser(user.get());
            return shoppingCarts.stream()
                    .map(ShoppingCart::getTicketOrders)
                    .flatMap(Collection::stream).toList();
        }
        else throw new UserNotFoundException();
    }
}

