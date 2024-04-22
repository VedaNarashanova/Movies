package mk.ukim.finki.movies.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.movies.model.Movie;
import mk.ukim.finki.movies.model.Production;
import mk.ukim.finki.movies.model.TicketOrder;
import mk.ukim.finki.movies.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class DataHolder {
    public static List<Movie> movies = new ArrayList<>();
    public static List<TicketOrder> ticketOrders = new ArrayList<>();
    public static List<Production> productions = new ArrayList<>();
    public static List<User> users = new ArrayList<>();

//    @PostConstruct
//    public void init(){
//        Production universal = new Production("Universal Pictures", "United States", "100 Universal City Plaza Drive in Universal City, CA");
//        Production paramount = new Production("Paramount Pictures", "United States", "5555 Melrose Avenue, Hollywood, CA");
//        Production warner = new Production("Warner Bros. Pictures", "United States", "3400 Warner Blvd., Burbank, CA 91505");
//        Production disney = new Production("Walt Disney Studios", "United States", "500 South Buena Vista Street, Burbank");
//        Production sony = new Production("Sony Pictures", "United States", "10202 West Washington Boulevard, Culver City, CA");
//
//        productions.add(universal);
//        productions.add(paramount);
//        productions.add(warner);
//        productions.add(disney);
//        productions.add(sony);
//
//        movies.add(new Movie("Harry Potter", "Chronicles of the life of a young wizard, Harry Potter, and his friends Hermione Granger and Ron Weasley, all of whom are students at Hogwarts School of Witchcraft and Wizardry", 7.6, universal));
//        movies.add(new Movie("Titanic", "Seventeen-year-old Rose hails from an aristocratic family and is set to be married. When she boards the Titanic, she meets Jack Dawson, an artist, and falls in love with him.", 8.1, paramount));
//        movies.add(new Movie("Schindler's List", "Oscar Schindler, a successful and narcissistic German businessman, slowly starts worrying about the safety of his Jewish workforce after witnessing their persecution in Poland during World War II.", 9.0, universal));
//        movies.add(new Movie("Shrek", "In a bid to get his land back, Shrek agrees to retrieve Princess Fiona for the fairytale-hating Lord Farquaad of Duloc, but falls in love with her on the way.", 7.9, sony));
//        movies.add(new Movie("La La Land", "When Sebastian, a pianist, and Mia, an actress, follow their passion and achieve success in their respective fields, they find themselves torn between their love for each other and their careers.", 8.0, sony));
//        movies.add(new Movie("Get out", "Chris, an African-American man, decides to visit his Caucasian girlfriend's parents during a weekend getaway. Although they seem normal at first, he is not prepared to experience the horrors ahead.", 7.8, disney));
//        movies.add(new Movie("The Social Network", "Mark Zuckerberg creates a social networking site, Facebook, with his friend Eduardo's help. Though it turns out to be a successful venture, he severs ties with several people along the way.", 7.7, sony));
//        movies.add(new Movie("Fight Club", "Unhappy with his capitalistic lifestyle, a white-collared insomniac forms an underground fight club with Tyler, a careless soap salesman. Soon, their venture spirals down into something sinister.", 8.8, warner));
//        movies.add(new Movie("Inception", "Cobb steals information from his targets by entering their dreams. He is wanted for his alleged role in his wife's murder and his only chance at redemption is to perform a nearly impossible task.", 8.7, paramount));
//        movies.add(new Movie("Parasite", "The struggling Kim family sees an opportunity when the son starts working for the wealthy Park family. Soon, all of them find a way to work within the same household and start living a parasitic life.", 9.9, warner));
//
//        ticketOrders.add(new TicketOrder("Parasite", "Stefanija", "1.1.1.1", 7L));
//        ticketOrders.add(new TicketOrder("Titanic", "Dejan", "1.1.1.1", 4L));
//        ticketOrders.add(new TicketOrder("Shrek", "Dejan", "1.1.1.1", 1L));
//        ticketOrders.add(new TicketOrder("Inception", "Stefanija", "1.1.1.1", 2L));
//        ticketOrders.add(new TicketOrder("Get out", "Stefanija", "1.1.1.1", 3L));

//        users.add(new User("stefanija.filipasikj", "sf", "Stefanija", "Filipasikj"));
//        users.add(new User("dejan.ristovski", "dr", "Dejan", "Ristovski"));
//    }
//}
}

