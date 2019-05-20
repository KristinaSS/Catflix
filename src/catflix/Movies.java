package catflix;

import javax.persistence.*;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Movies extends BasicClass{
    private Float price;
    private int timesWatchedPublic;


    @Basic
    @Column(name = "timesWatched")
    public int getTimesWatchedPublic() {
        return timesWatchedPublic;
    }

    public void setTimesWatchedPublic(int timesWatchedPublic) {
        this.timesWatchedPublic = timesWatchedPublic;
    }

    private List<Library>libList = new ArrayList<>();
    private List<MovieCatagory> movieCatagoryList = new ArrayList<>();

    @ManyToMany(mappedBy = "moviesList") //the list in the other Class
    public List<MovieCatagory> getMovieCatagoryList() {
        return movieCatagoryList;
    }

    public void setMovieCatagoryList(List<MovieCatagory> movieCatagoryList) {
        this.movieCatagoryList = movieCatagoryList;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ClientID")// ID of the other table
    public List<Library> getLibList() {
        return libList;
    }

    public void setLibList(List<Library> libList) {
        this.libList = libList;
    }

    @Basic
    @Column(name = "price")
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movies movies = (Movies) o;
        return id == movies.id &&
                Objects.equals(name, movies.name) &&
                Objects.equals(price, movies.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
