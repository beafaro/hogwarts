import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import entity.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args){
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
            EntityManager em = emf.createEntityManager();

            //Apartado 1
            Query qry = em.createQuery( "select p from Person p join p.courses c where c.teacher.firstName = :name ");
            qry.setParameter("name", "Minerva");

            List<Person> lPerson = qry.getResultList();
            for (Person p: lPerson) {
                System.out.println(p.toString());
            }
            System.out.println(lPerson.size());

            //Apartado 2
            Query q=em.createNamedQuery("person.maxPuntos");
            List<Person> lista=q.getResultList();
            for (Person p: lista){
                System.out.print("El personaje que ha recibido más puntos es: ");
                System.out.println(p.getFirstName());
            }

            Query q2 = em.createNamedQuery("persona.EntregaPuntos");
            Object[] giver = (Object[]) q2.getSingleResult();
            System.out.println("El que ha entregado más puntos es :" + giver[0] +" "+ giver[1]);




            //Apartado 3
            Connection conexion = DriverManager.getConnection ("jdbc:mysql://localhost/hogwarts","root", "root");
            //consulta con un innerjoin
            Statement s=conexion.createStatement();
            String linea;
            ResultSet rs=s.executeQuery("select person.first_name, person.last_name, house.name from person inner join house on house.head_teacher = person.id;");
            System.out.println("\nInnerjoin para head teachers: \n");
            while(rs.next()){
                linea=rs.getString("first_name") + " " + rs.getString("last_name");
                linea+=" is the head teacher of "+rs.getString("name");
                System.out.println(linea);
            }


            //3.2. Una consulta con un outerjoin
            rs=s.executeQuery("select person.first_name, person.last_name, course.name from person right outer join course on person.id = course.teacher_id");
            System.out.println("\nOuterjoin para materias y profesores: \n");
            while(rs.next()){
                linea=rs.getString("first_name") + " " + rs.getString("last_name");
                if (linea.endsWith("null")){
                    linea="No hay profesor asignado para ";
                }else{
                    linea+=" es profesor/a de ";
                }
                linea+=(rs.getString("name"));
                System.out.println(linea);
            }


            //3.3. Una consulta con una subconsulta
            rs=s.executeQuery("select person.first_name, person.last_name, house_points.points from person, house_points where house_points.points = (select MAX(points) from house_points) and person.id=house_points.receiver");
            System.out.println("\nSubconsulta para quien recibió mas puntos: \n");
            while(rs.next()){
                linea=rs.getString("first_name") + " " + rs.getString("last_name");
                linea+=(" recibió "+rs.getString("points")+" puntos.");
                System.out.println(linea);
            }
            conexion.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}