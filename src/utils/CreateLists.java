package utils;

import catflix.Movies;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CreateLists {
    private static CreateLists createListsInstance = null;

    private CreateLists() {
    }

    public static CreateLists getInstance(){
        if(createListsInstance ==null)
            createListsInstance = new CreateLists();
        return createListsInstance;
    }

    static <T> List <T> generics(CriteriaBuilder builder, Session session, Class<T> clazz){
        CriteriaQuery<T> query = builder.createQuery(clazz);
        Root<T> root = query.from(clazz);
        query.select(root);
        return session.createQuery(query).getResultList();
    }

}


/*    public static List<PaymentType> createPaymentTypeList(CriteriaBuilder builder, Session session){
        CriteriaQuery<PaymentType> query = builder.createQuery(PaymentType.class);
        Root<PaymentType> root = query.from(PaymentType.class);
        query.select(root);
        List<PaymentType> list = session.createQuery(query).getResultList();
        return list;
    }

    public static List<Movies> createMovieList(CriteriaBuilder builder, Session session){
        CriteriaQuery<Movies> query = builder.createQuery(Movies.class);
        Root<Movies> root = query.from(Movies.class);
        query.select(root);
        List<Movies> list = session.createQuery(query).getResultList();
        return list;
    }

    public static List<Library> createLibraryList(CriteriaBuilder builder, Session session){

        CriteriaQuery<Library> query1 = builder.createQuery(Library.class);
        Root<Library> root1 = query1.from(Library.class);
        query1.select(root1);
        List<Library> libraries = session.createQuery(query1).getResultList();
        return libraries;
    }

    public static List<MovieCatagory> createMovieCatagoryList(CriteriaBuilder builder, Session session){
        CriteriaQuery<MovieCatagory> query = builder.createQuery(MovieCatagory.class);
        Root<MovieCatagory> root = query.from(MovieCatagory.class);
        query.select(root);
        List<MovieCatagory> list = session.createQuery(query).getResultList();
        return list;
    }*/
