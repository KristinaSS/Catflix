package utils;

import catflix.Client;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Scanner;

public class IterateClient {
    final static private Scanner scanner = new Scanner(System.in);
    private static IterateClient iterateClientInstance = null;
    private String email;
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private IterateClient(){
    }

    public static IterateClient getInstance(){
        if(iterateClientInstance ==null)
            iterateClientInstance = new IterateClient();
        return iterateClientInstance;
    }

    int iterateList(CriteriaBuilder builder, Session session) {
        System.out.println("Username");
        name = scanner.nextLine();
        System.out.println("Email");
        email = scanner.nextLine();

        CriteriaQuery<Client> query = builder.createQuery(Client.class);
        Root<Client> root = query.from(Client.class);
        query.select(root);
        List<Client> clients = session.createQuery(query).getResultList();
        for (Client client : clients) {
            if (client.getEmail().equals(email) && client.getName().equals(name)) {
                return client.getId();
            }
        }
        return -1;
    }

}
