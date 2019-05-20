package catflix;

import net.bytebuddy.dynamic.TypeResolutionStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "MovieCatagory")
public class MovieCatagory extends BasicClass{

    private List<Movies> moviesList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Movie_MovieCatagory",
        joinColumns = {
                @JoinColumn(name = "MovieCatagoryID", nullable = false, updatable = false)},
            inverseJoinColumns = {
            @JoinColumn(name = "MovieID", nullable = false, updatable = false)
            })

    public List<Movies> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(List<Movies> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieCatagory that = (MovieCatagory) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
