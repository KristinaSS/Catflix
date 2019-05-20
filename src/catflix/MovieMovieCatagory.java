package catflix;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Movie_MovieCatagory", schema = "dbo", catalog = "Catflix")
@IdClass(MovieMovieCatagoryPK.class)
public class MovieMovieCatagory {
    private int movieId;
    private int movieCatagoryId;

    @Id
    @Column(name = "MovieID")
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Id
    @Column(name = "MovieCatagoryID")
    public int getMovieCatagoryId() {
        return movieCatagoryId;
    }

    public void setMovieCatagoryId(int movieCatagoryId) {
        this.movieCatagoryId = movieCatagoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieMovieCatagory that = (MovieMovieCatagory) o;
        return movieId == that.movieId &&
                movieCatagoryId == that.movieCatagoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, movieCatagoryId);
    }
}
