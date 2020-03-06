package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/startcode",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

//    @Path("add")
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    public String addPerson() {
//        return "{\"msg\":\"Person Added\"}";
//    }
    //getPersonwithID
    @Path("/id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonWithID(@PathParam("id") Long id) throws PersonNotFoundException {
        PersonDTO person = FACADE.getPerson(id);
        
        return GSON.toJson(person);
    }

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPerson(String per) {
        PersonDTO p = GSON.fromJson(per, PersonDTO.class);
        PersonDTO person = FACADE.addPerson(p.getfName(), p.getlName(), p.getPhone());
        return Response.ok(person).build();
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAll() {
        PersonsDTO d = FACADE.getAllPersons();
        return GSON.toJson(d);
    }

//Delete person    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public String deletePerson(String per) throws PersonNotFoundException {
        PersonDTO p = GSON.fromJson(per, PersonDTO.class);
        PersonDTO person = FACADE.deletePerson(p.getId());
        return "{\"status\": \"removed\"}";
    }

//Edit Person
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/edit")
    public String editPerson(String per) {
        PersonDTO p = GSON.fromJson(per, PersonDTO.class);
        PersonDTO person = FACADE.editPerson(p);
        return GSON.toJson(person);
    }

}
