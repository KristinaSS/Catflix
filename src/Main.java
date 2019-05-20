
import org.hibernate.Session;
import utils.HibernateUtil;

import javax.persistence.criteria.*;
import java.util.*;

import static utils.Methods.*;

public class Main {
    final static private Scanner scanner = new Scanner(System.in);

    public static void main(final String[] args) throws Exception {
        try (Session session = HibernateUtil.getSession()) {

            session.beginTransaction();

            int clientID;
            CriteriaBuilder builder = session.getCriteriaBuilder();
            top2Movies(session, builder);
            //seeMostEarnedMovieByCatagory(session,builder);
            if (home() == 1)
                clientID = login(builder, session);
            else
                clientID = signUp(builder, session);
            while (true) {
                switch (printList()) {
                    case 1:
                        depositMoney(clientID, session, builder);
                        continue;
                    case 2:
                        buyMovie(clientID, session, builder);
                        continue;
                    case 3:
                        watchMovie(clientID, session, builder);
                        continue;
                    case 4:
                        seeAccountDetails(clientID, session);
                        continue;
                    case 5:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("You have chosen non of the options try again!");
                        continue;
                }
                session.close();
                break;
            }
        } catch (Exception e) {
            System.out.println("Exception caught!");
        }
    }
}


