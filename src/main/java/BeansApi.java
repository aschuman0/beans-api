import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("api")
public class BeansApi {
    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return "Hello, World!";
    }

    @GET
    @Path("coffee")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Bean bean =  new Bean.Builder()
                        .setName("This is coffee")
                        .setNotes("Damn fine")
                        .setOrigin("Sumatra")
                        .setRating(4.5)
                        .setSupplier("Sweet Marias")
                        .setUrl("internet")
                        .build();

        return Response.status(Status.OK).entity(bean.toJson()).build();
    }

    @GET
    @Path("coffee/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getById(@PathParam("id") String id) {
        return id;
    }

}
