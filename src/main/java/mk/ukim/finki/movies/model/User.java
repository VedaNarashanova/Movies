package mk.ukim.finki.movies.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import mk.ukim.finki.movies.model.converters.UserFullNameConverter;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "Movie_users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Convert(converter = UserFullNameConverter.class)
    private UserFullName userFullName;
    private String password;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ShoppingCart> carts;

    public User(String username, String name, String surname, String password, LocalDate dateOfBirth) {
        this.username = username;
        this.userFullName = new UserFullName();
        this.userFullName.setName(name);
        this.userFullName.setSurname(surname);
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }
}

