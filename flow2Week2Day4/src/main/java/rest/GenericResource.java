package rest;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    private static Person person = new Person("Mike","Man");
    private static Gson gson = new Gson();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getName() {
        return gson.toJson(person);
    }    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putXml(String content) {
        Person newP = gson.fromJson(content, Person.class);
        person = newP;
    }
}
