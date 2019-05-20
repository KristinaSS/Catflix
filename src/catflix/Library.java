package catflix;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Library implements MethodsInterface{
    private int numberOfTimesWatched;
    private int id;

    private Movies movies;
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ClientID")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MovieID")
    public Movies getMovies() {
        return movies;
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
    }

    @Basic
    @Column(name = "NumberOfTimesWatched")
    public int getNumberOfTimesWatched() {
        return numberOfTimesWatched;
    }

    public void setNumberOfTimesWatched(int numberOfTimesWatched) {
        this.numberOfTimesWatched = numberOfTimesWatched;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return numberOfTimesWatched == library.numberOfTimesWatched &&
                id == library.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfTimesWatched, id);
    }
}
