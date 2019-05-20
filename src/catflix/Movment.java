package catflix;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Movment implements MethodsInterface{
    private int id;
    private Float amount;
    private Boolean isDepost;
    private Date date;
    private String time;
    private PaymentType paymentType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PaymentTypeID")
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
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

    @Basic
    @Column(name = "Amount")
    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "time")
    public String  getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movment movment = (Movment) o;
        return id == movment.id &&
                Objects.equals(amount, movment.amount) &&
                Objects.equals(isDepost, movment.isDepost) &&
                Objects.equals(date, movment.date) &&
                Objects.equals(time, movment.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, isDepost, date, time);
    }
}
