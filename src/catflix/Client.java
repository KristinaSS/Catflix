package catflix;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Client extends BasicClass{
    private String email;
    private Float totalBalance;

    private List<Movment> movementList = new ArrayList<>();
    private List<Library> libraryList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ClientID")// ID of the other table
    public List<Library> getLibraryList() {
        return libraryList;
    }

    public void setLibraryList(List<Library> libraryList) {
        this.libraryList = libraryList;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ClientID")// ID of the other table
    public List<Movment> getMovementList() {
        return movementList;
    }

    public void setMovementList(List<Movment> movmentList) {
        this.movementList = movmentList;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "totalBalence")
    public Float getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Float totalBalance) {
        this.totalBalance = totalBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                Objects.equals(name, client.name) &&
                Objects.equals(email, client.email) &&
                Objects.equals(totalBalance, client.totalBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, totalBalance);
    }
}
