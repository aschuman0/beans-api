package com.aschuman.beansApi;

        import jdk.nashorn.internal.objects.annotations.Constructor;

        import javax.servlet.ServletContext;
        import javax.servlet.http.HttpServletRequest;
        import javax.sql.DataSource;
        import javax.ws.rs.*;
        import javax.ws.rs.core.MediaType;
        import javax.ws.rs.core.Context;
        import javax.ws.rs.core.Response;
        import javax.ws.rs.core.Response.Status;
        import java.io.IOException;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.List;

@Path("api")
public class BeansServlet {
    @Context
    private ServletContext servletContext;
    @Context
    private HttpServletRequest httpServletRequest;

    @GET
    @Path("coffee")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        // Get connection pool from the servlet context
        DataSource pool = (DataSource) servletContext.getAttribute("my-pool");

        List<Bean> beansList = new ArrayList<>();
        Bean bean =  new Bean.Builder()
                .setName("This is coffee")
                .setNotes("Damn fine")
                .setOrigin("Sumatra")
                .setRating(4)
                .setSupplier("Sweet Maria's")
                .setUrl("internet")
                .build();

        beansList.add(bean);

        return Response
                .status(Status.OK)
                .entity(bean.toJson())
                .build();
    }

    @GET
    @Path("coffee/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getById(@PathParam("id") String id) {
        return id;
    }

    @POST
    @Path("coffee")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRecord(Bean bean) {
        // Get connection pool
        DataSource pool = (DataSource) servletContext.getAttribute("my-pool");
        // Call commit method on bean instance
        try {
            bean.createInDb(pool);
        } catch (SQLException ex) {
            throw new RuntimeException("Issue committing to the DB");
        }
        // Return bean with id
        return Response
                .status(Status.OK)
                .entity(bean.toJson())
                .build();
    }
}
