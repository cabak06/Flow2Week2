
package entities;

import facades.PersonFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;


public class MakeDummmies {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/startcode",
            "dev",
            "ax2",
            EMF_Creator.Strategy.DROP_AND_CREATE);
    private static final PersonFacade FACADE = PersonFacade.getFacadeExample(EMF);
    
    public static void main(String[] args) {
        
        List<Person> list = new ArrayList();
        list.add(new Person("Cahit", "The Man", "12312631"));
        list.add(new Person("Michael", "The Rock", "81274131"));
        list.add(new Person("Andreas", "The Spider", "21323631"));
        
        for (Person person : list) {
            FACADE.addPerson(person.getfName(), person.getlName(), person.getPhone());
        }
        
    }
    
}
