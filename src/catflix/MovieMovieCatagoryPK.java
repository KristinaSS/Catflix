package catflix;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class MovieMovieCatagoryPK implements Serializable,MethodsInterface {
    private int movieId;
    private int movieCatagoryId;

    @Column(name = "MovieID")
    @Id
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Column(name = "MovieCatagoryID")
    @Id
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
        MovieMovieCatagoryPK that = (MovieMovieCatagoryPK) o;
        return movieId == that.movieId &&
                movieCatagoryId == that.movieCatagoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, movieCatagoryId);
    }
}
