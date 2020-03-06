package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import utils.EMF_Creator;
import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person person1, person2, person3;
    private static ArrayList<Person> personList = new ArrayList();

    public PersonFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = PersonFacade.getFacadeExample(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = PersonFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();

            person1 = new Person("Mike The Destroyer", "Tha'Brain", "12345");
            person2 = new Person("Marcus The Rock", "Heating Bowl", "98765");
            person3 = new Person("Andreas The Player", "Batcher", "45678");

            em.persist(person1);
            em.persist(person2);
            em.persist(person3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
    }

    @AfterEach
    public void tearDown() {
        for (Person person : personList) {
            person = null;
        }
        personList.clear();
    }

    // TODO: Delete or change this method 
    @Test
    public void testAFacadeMethod() {
        assertEquals(3, facade.getRenameMeCount(), "Expects three rows in the database");
    }

    @Test
    public void testDeletePerson() throws PersonNotFoundException {
        Person expectedPerson = person2;
        PersonDTO result = facade.deletePerson(expectedPerson.getId());

        //simple asserts
        assertTrue(expectedPerson.getfName().equals(result.getfName()));
        assertTrue(expectedPerson.getlName().equals(result.getlName()));
        assertTrue(expectedPerson.getPhone().equals(result.getPhone()));
        assertEquals(expectedPerson.getId(), result.getId());

        //Pull from db
        List<Person> dbList = pullFromDB();

        //check size
        int expectedResultSize = personList.size() - 1;
        assertEquals(expectedResultSize, dbList.size());

        //check expectedPerson is gone from db
        for (Person dbPerson : dbList) {
            if (Objects.equals(dbPerson.getId(), result.getId())) {
                fail("expectedPerson was found in db after deletion");
            }
        }

    }

    private List<Person> pullFromDB() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> tq = em.createQuery("SELECT p FROM Person p", Person.class);
            return tq.getResultList();
        } catch (Exception e) {
            fail("Could not get Persons from the DB");
            return null;
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetPerson() throws PersonNotFoundException {

        Person expectedPerson = person2;

        String firstName = expectedPerson.getfName();
        String lastName = expectedPerson.getlName();
        String phone = expectedPerson.getPhone();
        Long id = expectedPerson.getId();

        PersonDTO result = facade.getPerson(id);

        assertEquals(result.getfName(), firstName);
        assertEquals(result.getlName(), lastName);
        assertEquals(result.getPhone(), phone);

    }

    @Test
    public void testGetAllPersons() {
        List<Person> dbList = pullFromDB();

        PersonsDTO result = facade.getAllPersons();

        int expectedListSize = dbList.size();
        assertEquals(expectedListSize, result.getAll().size());
        
        for (Person person : dbList) {
            boolean foundMatch = false;
            for (PersonDTO personDTO : result.getAll()) {
                if(Objects.equals(person.getId(), personDTO.getId())){
                    foundMatch = true;
                    assertTrue(person.getfName().equals(personDTO.getfName()));
                    assertTrue(person.getlName().equals(personDTO.getlName()));
                    assertTrue(person.getPhone().equals(personDTO.getPhone()));
                    break;
                }
            }
            assertTrue(foundMatch);
        }

    }

    @Test
    public void testAddPerson() {

        String first = "FirstNew";
        String last = "LastNew";
        String phone = "12345";

        int expectedListSize = personList.size() + 1;

        PersonDTO result = facade.addPerson(first, last, phone);

        List<Person> dbList = pullFromDB();

        int actualListSize = dbList.size();

        //expecting 4 as one person is added to the list of 3 persons
        assertEquals(expectedListSize, 4);
        assertEquals(expectedListSize, actualListSize);

        //asserting that the person has been added succesfully
        assertEquals(result.getPhone(), phone);
        assertEquals(result.getfName(), first);
        assertEquals(result.getlName(), last);

    }

}
