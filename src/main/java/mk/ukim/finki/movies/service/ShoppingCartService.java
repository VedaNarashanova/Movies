package mk.ukim.finki.movies.service;

import mk.ukim.finki.movies.model.Movie;
import mk.ukim.finki.movies.model.ShoppingCart;
import mk.ukim.finki.movies.model.TicketOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ShoppingCartService {
    List<ShoppingCart> listAllShoppingCartsOfUser(Long id);
    List<ShoppingCart> listAllShoppingCartsOfUserBetween(Long id,LocalDateTime from, LocalDateTime to);
    List<TicketOrder> listAllTicketOrdersInShoppingCart(Long cartId);
    ShoppingCart getActiveShoppingCart(Long userId);
    ShoppingCart addTicketOrderToShoppingCart(Long userId, Long ticketId);
    List<TicketOrder> findOrdersBetween(LocalDateTime from, LocalDateTime to);
    Map<Long, LocalDateTime> getTimeOfTicketOrders();
    void removeFromCart(Long userId, Long ticketId);
    void finishShoppingCart(Long id, LocalDateTime date);

}
