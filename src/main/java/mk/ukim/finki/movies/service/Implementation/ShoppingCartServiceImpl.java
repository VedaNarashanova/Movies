package mk.ukim.finki.movies.service.Implementation;

import mk.ukim.finki.movies.model.ShoppingCart;
import mk.ukim.finki.movies.model.TicketOrder;
import mk.ukim.finki.movies.model.User;
import mk.ukim.finki.movies.model.enume.ShoppingCartStatus;
import mk.ukim.finki.movies.model.exception.ShoppingCartNotFoundException;
import mk.ukim.finki.movies.model.exception.TicketOrderAlreadyInShoppingCartException;
import mk.ukim.finki.movies.model.exception.TicketOrderNotFoundException;
import mk.ukim.finki.movies.model.exception.UserNotFoundException;
import mk.ukim.finki.movies.repository.jpa.JpaShoppingCartRepository;
import mk.ukim.finki.movies.repository.jpa.JpaTicketOrderRepository;
import mk.ukim.finki.movies.repository.jpa.JpaUserRepository;
import mk.ukim.finki.movies.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final JpaShoppingCartRepository shoppingCartRepository;
    private final JpaUserRepository userRepository;
    private final JpaTicketOrderRepository ticketOrderRepository;

    public ShoppingCartServiceImpl(JpaShoppingCartRepository shoppingCartRepository,
                                   JpaUserRepository userRepository,
                                   JpaTicketOrderRepository ticketOrderRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.ticketOrderRepository = ticketOrderRepository;
    }

    @Override
    public List<ShoppingCart> listAllShoppingCartsOfUser(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return this.shoppingCartRepository.findAllByUser(user).stream()
                .sorted(Comparator.comparing(ShoppingCart::getStatus).reversed()
                        .thenComparing(ShoppingCart::getDateCreated).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<ShoppingCart> listAllShoppingCartsOfUserBetween(Long userId, LocalDateTime from, LocalDateTime to) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return this.shoppingCartRepository.findAllByUserAndDateCreatedBetween(user, from, to)
                .stream().sorted(Comparator.comparing(ShoppingCart::getStatus).reversed()
                        .thenComparing(ShoppingCart::getDateCreated).reversed())
                .collect(Collectors.toList());

    }

    @Override
    public List<TicketOrder> listAllTicketOrdersInShoppingCart(Long cartId) {
        if (this.shoppingCartRepository.findById(cartId).isEmpty()) {
            throw new ShoppingCartNotFoundException();
        }
        return this.shoppingCartRepository.findById(cartId).get().getTicketOrders();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return this.shoppingCartRepository
                .findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCart addTicketOrderToShoppingCart(Long userId, Long ticketId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        TicketOrder order = this.ticketOrderRepository.findById(ticketId)
                .orElseThrow(TicketOrderNotFoundException::new);
        if(shoppingCart.getTicketOrders().stream().filter(p -> p.getId().equals(ticketId)).collect(Collectors.toList()).isEmpty()){
            shoppingCart.getTicketOrders().add(order);
            return this.shoppingCartRepository.save(shoppingCart);
        }else{
            throw new TicketOrderAlreadyInShoppingCartException();
        }
    }

    @Override
    public List<TicketOrder> findOrdersBetween(LocalDateTime from, LocalDateTime to) {
        return this.shoppingCartRepository.findShoppingCartByDateCreatedBetween(from,to)
                .stream().map(ShoppingCart::getTicketOrders).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Long, LocalDateTime> getTimeOfTicketOrders() {
        List<ShoppingCart> shoppingCarts = this.shoppingCartRepository.findAll();
        return shoppingCarts.stream()
                .flatMap(cart -> cart.getTicketOrders().stream()
                        .map(ticketOrder -> Map.entry(ticketOrder.getId(), cart.getDateCreated()))
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


    }

    @Override
    public void removeFromCart(Long userId, Long ticketId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        shoppingCart.getTicketOrders().removeIf(p -> p.getId().equals(ticketId));

        TicketOrder ticketOrder = this.ticketOrderRepository.findAll()
                .stream().filter(p -> p.getId().equals(ticketId)).findFirst().get();

        this.shoppingCartRepository.save(shoppingCart);
        this.ticketOrderRepository.delete(ticketOrder);
    }

    @Override
    public void finishShoppingCart(Long id, LocalDateTime date) {
        Optional<ShoppingCart> shoppingCart = this.shoppingCartRepository.findById(id);
        if(shoppingCart.isPresent()){
            shoppingCart.get().setStatus(ShoppingCartStatus.FINISHED);
            shoppingCart.get().setDateCreated(date);
            ShoppingCart newCart = new ShoppingCart(shoppingCart.get().getUser());
            this.shoppingCartRepository.save(newCart);
        }
    }
}
