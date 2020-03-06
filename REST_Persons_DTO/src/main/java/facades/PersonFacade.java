package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getRenameMeCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }

    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone) {

        String first = fName;
        String last = lName;
        String pho = phone;

        EntityManager em = emf.createEntityManager();
        Person person = new Person(first, last, pho);

        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO deletePerson(Long id) throws PersonNotFoundException {
       
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            em.remove(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        
        }catch(Exception e){
            throw new PersonNotFoundException("Could not delete, provided id does not exist");
        
        } finally {
            em.close();
        }
        
    }

    
    @Override
    public PersonDTO getPerson(Long id) throws PersonNotFoundException{

        EntityManager em = emf.createEntityManager();
        try {
            Person person = em.find(Person.class, id);
            return new PersonDTO(person);
        } catch (Exception e) {
            throw new PersonNotFoundException("No person with provided id found");
        } finally {
            em.close();
        }
    }

    @Override
    public PersonsDTO getAllPersons() {

        EntityManager em = emf.createEntityManager();

        try {

            TypedQuery<Person> query = em.createQuery("select p from Person p", Person.class);
            List<Person> dbList = query.getResultList();
            PersonsDTO result = new PersonsDTO(dbList);
            return result;

        } finally {
            em.close();

        }
    }

    @Override
    public PersonDTO editPerson(PersonDTO p){

        EntityManager em = emf.createEntityManager();
        try {
            Person person = em.find(Person.class, p.getId());

            em.getTransaction().begin();
            person.setPhone(p.getPhone());
            person.setfName(p.getfName());
            person.setlName(p.getlName());
            person.setLastEdited(new Date());
            em.getTransaction().commit();

            return new PersonDTO(person);

        } finally {
            em.close();
        }

    }

}
