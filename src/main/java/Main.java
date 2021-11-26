import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Main {
    public static void main(String[] args){
        try {

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Query qry = em.createQuery( "select z from Zona z where z.descripcion='Vigo y alrededores'");

            em.getTransaction().commit();
            em.close();
            emf.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
