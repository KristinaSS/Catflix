package utils;

import catflix.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

import static utils.CreateLists.*;

public class Methods {
    private static Scanner scanner = new Scanner(System.in);
    private static Methods methodsInstance = null;
    private Methods() {
    }

    public static Methods getInstance(){
        if(methodsInstance ==null)
            methodsInstance = new Methods();
        return methodsInstance;
    }

    public static int printList() {
        System.out.println("Choose:");
        System.out.println();
        List<String> menu = new ArrayList<>();
        menu.add("Menu");
        menu.add("1. Deposit Money");
        menu.add("2. Buy Movie");
        menu.add("3. Watch Movie");
        menu.add("4. View Account");
        menu.add("5. Exit");

        for (String string : menu)
            System.out.println(string);

        return scanner.nextInt();
}

    public static int login(CriteriaBuilder builder, Session session) {
            Transaction tx =session.beginTransaction();

            IterateClient iterateClient = IterateClient.getInstance();

            int id = iterateClient.iterateList(builder, session);
            if (id > 0) {
                System.out.println("Welcome to Catflix, choose one of the following:");

                if (tx.isActive())
                    session.getTransaction().commit();
                return id;
            }
            System.out.println("No client exists with this Username and email");

        if (tx.isActive())
            session.getTransaction().commit();

        System.exit(0);
        return -1;
    }

    public static int signUp(CriteriaBuilder builder, Session session) {

            Transaction tx = session.beginTransaction();
            IterateClient iterateClient = IterateClient.getInstance();
            int id;
            while (true) {
                id = iterateClient.iterateList(builder, session);
                if (id > 0) {
                    System.out.println("A user with this username and email already exists!" +
                            "Try again");
                    continue;
                }
                Client client = new Client();
                client.setEmail(iterateClient.getEmail());
                client.setName(iterateClient.getName());
                Float balence = (float) 0;
                client.setTotalBalance(balence);
                session.save(client);

                if (tx.isActive())
                    session.getTransaction().commit();

                return client.getId();
            }
    }

    public static int home() {
        int res;
        do {
            System.out.println("Press 1 to log in and 2 to sign up");
            res = scanner.nextInt();
        } while (res != 1 && res != 2);
        return res;
    }

    public static void depositMoney(int ClientID, Session session, CriteriaBuilder builder){
        Transaction tx = session.beginTransaction();

        Client client = (Client) session.get(Client.class, ClientID);

        System.out.println("Amount of Money to deposit: ");
        float amount = scanner.nextFloat();

        int res;

        List<PaymentType>paymentTypes = generics(builder,session, PaymentType.class);

        for (PaymentType paymentType : paymentTypes) {
            System.out.println(paymentType.getId() + " " + paymentType.getName());
        }
        do {
            res = scanner.nextInt();
        } while (res < 1 || res > 4);

        Movment movment = new Movment();
        movment.setAmount(amount);

        for (PaymentType paymentType : paymentTypes) {
            if (paymentType.getId() == res) {
                movment.setPaymentType(paymentType);
                break;
            }
        }

        client.getMovementList().add(movment);

        if(client.getTotalBalance() ==null)
            client.setTotalBalance(amount);
        else
            client.setTotalBalance(client.getTotalBalance() + amount);

        session.save(client);


        if (tx.isActive())
            session.getTransaction().commit();
        System.out.println("Successfully deposited money!");
    }

    public static void buyMovie(int clientID, Session session, CriteriaBuilder builder){

        Transaction tx = session.beginTransaction();

        Client client = (Client) session.get(Client.class, clientID);

        System.out.println("Which movie would you like to buy?");
        System.out.println("Name        /       Price");

        List<Movies> movies = generics(builder,session,Movies.class);
        int res;

        List<Library> libraries = generics(builder, session,Library.class);

        System.out.println("Choose Movie");
        for(Movies m: movies){
            String s1 =String.format("%-50s",m.getName());
            String s2 =String.format("%03.2f",m.getPrice());
            System.out.println(m.getId() + " " + s1 + s2);
        }
        do {
            res = scanner.nextInt();
        }while( res < 1 || res>movies.size());

        for (Movies m : movies) {
            if (m.getId() == res) {

                for(Library l: libraries){
                    if((l.getClient().getId() ==clientID) && (l.getMovies().getId() == m.getId())){
                        if (tx.isActive())
                            session.getTransaction().commit();
                        System.out.println("You already have this movie in your library!");
                        return;
                    }
                }
                Library library = new Library();
                library.setClient(client);

                library.setMovies(m);
                float balance = client.getTotalBalance();
                if(balance - m.getPrice()< 0){
                    if (tx.isActive())
                        session.getTransaction().commit();
                    System.out.println("You dont have enough money to purchase the movie!");
                    return;
                }
                client.setTotalBalance(client.getTotalBalance()-m.getPrice());
                Movment movment = new Movment();
                movment.setAmount(m.getPrice()*(-1));
                client.getMovementList().add(movment);


                session.save(movment);
                session.save(library);
                session.save(client);
                if (tx.isActive())
                    session.getTransaction().commit();
                break;
            }
        }
        if (tx.isActive())
            session.getTransaction().commit();

        System.out.println("Successfully Added movie to your library");
    }

    public static void watchMovie(int clientID, Session session, CriteriaBuilder builder){

        Transaction tx = session.beginTransaction();

        List<Library> libraries = generics(builder,session,Library.class);

        System.out.println("Choose the movie: ");

        int count = 0;

        libraries.sort(new Comparator<Library>() {
            @Override
            public int compare(Library o1, Library o2) {
                return Integer.valueOf(o1.getMovies().getId()).compareTo(o2.getMovies().getId());
            }
        });
        for(Library l : libraries){
            if(l.getClient().getId() == clientID) {
                System.out.println(l.getMovies().getId() + " " + l.getMovies().getName());
                count++;
            }
        }

        if(count == 0){
            System.out.println("You have no movies in your library!");
            if (tx.isActive())
                session.getTransaction().commit();
            return;
        }
        int res = scanner.nextInt();

        List<Movies> movies = generics(builder, session,Movies.class);

        for(Movies m: movies){
            if(m.getId() == res) {
                m.setTimesWatchedPublic(m.getTimesWatchedPublic() + 1);
                session.save(m);
            }
        }
        for(Library l : libraries){
            if((l.getClient().getId() == clientID)&&(l.getMovies().getId()==res)){
                l.setNumberOfTimesWatched(l.getNumberOfTimesWatched()+1);
                session.save(l);
            }
        }
        if (tx.isActive())
            session.getTransaction().commit();

        System.out.println("Successfully watched movie");

    }

    public static void top2Movies(Session session, CriteriaBuilder builder){

        System.out.println("Here are the top 2 movies of each Movie Catagory: ");

        List<MovieCatagory> movieCatagories = generics(builder,session,MovieCatagory.class);

        int count = 1;
        for(MovieCatagory mc: movieCatagories){

            mc.getMoviesList().sort((o1, o2) -> Integer.compare(o2.getTimesWatchedPublic(), o1.getTimesWatchedPublic()));
            System.out.println();
            System.out.println(mc.getName());
            System.out.println("________________________");

            for(Movies m:  mc.getMoviesList()){
                String s1 =String.format("%-50s",m.getName());
                String s2 =String.format("%03d",m.getTimesWatchedPublic());
                System.out.println(count + " " + s1 + s2);
                if(count == 2)
                    break;
                count++;
            }
            count = 1;
        }
        System.out.println();

            session.getTransaction().commit();
    }

    public static void seeAccountDetails(int ClientID, Session session){
        Transaction tx = session.beginTransaction();

        Client client = (Client) session.get(Client.class, ClientID);

        System.out.println("Name: " + client.getName());

        System.out.println("Email: " + client.getEmail());

        System.out.println("Total Balence: " + client.getTotalBalance());

        if (tx.isActive())
            session.getTransaction().commit();
    }

    public static void seeMostEarnedMovieByCatagory(Session session, CriteriaBuilder builder){
        List<MovieCatagory> movieCatagories = generics(builder,session,MovieCatagory.class);

        int count = 1;
        System.out.println("Here is the movie earned the most money for each catagory: ");
        for(MovieCatagory mc : movieCatagories){
           mc.getMoviesList().sort(new Comparator<Movies>() {
                public int compare(Movies o1, Movies o2) {

                    return Float.valueOf(o2.getTimesWatchedPublic() * o2.getPrice()).compareTo(o1.getTimesWatchedPublic() * o1.getPrice());
                }
            });
           System.out.println();
            System.out.println(mc.getName());
            System.out.println("________________________");
            for(Movies m:  mc.getMoviesList()){
                String s1 =String.format("%-50s",m.getName());
                String s2 =String.format("%03.2f",m.getPrice()* m.getTimesWatchedPublic());
                System.out.println(count + " " + s1 + s2);
                if(count == 1)
                    break;
                count++;
            }
            count = 1;
        }
        System.out.println();

        session.getTransaction().commit();
        }
    }