import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import entity.*;

import java.util.List;

public class Main {
    public static void main(String[] args){
        try {

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
            EntityManager em = emf.createEntityManager();

            Query qry = em.createQuery( "select p from Person p join p.courses c where c.teacher.firstName = :name ");
            qry.setParameter("name", "Minerva");

            List<Person> lPerson = qry.getResultList();
            for (Person p: lPerson) {
                System.out.println(p.toString());
            }
            System.out.println(lPerson.size());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
/*
* select p from Person p JOIN p.courses c WHERE c.name='Potions'

Si queremos parametrizar la consulta para que sea flexible:

Opción 1: select p from Person p JOIN p.courses c WHERE c.name=?1
Opción 2: select p from Person p JOIN p.courses c WHERE c.name=:nombreCurso
*
*
            Query qry = em.createQuery( "select p from Person p inner join House h on h.id= p.houseByHouseId.id where h.personByHeadTeacher.firstName = :name");
            qry.setParameter("name", "Minerva");
* */