package com.aschuman.beansApi;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;

@Path("api")
public class BeansServelet extends HttpServlet {
    @GET
    @Path("coffee")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(HttpServletRequest req) {


        List<Bean> beansList = new ArrayList<>();
        DataSource pool = (DataSource) req.getServletContext().getAttribute("my-pool");

        Bean bean =  new Bean.Builder()
                .setName("This is coffee")
                .setNotes("Damn fine")
                .setOrigin("Sumatra")
                .setRating(4.5)
                .setSupplier("Sweet Maria's")
                .setUrl("internet")
                .build();

        beansList.add(bean);

        return Response.status(Status.OK).entity(bean).build();
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
    public String createRecord() {
        // First, echo the result
        // Then decompose and create objects
        // Push to DB, catch errors,
        // return response with UUID
        return "{\"id\": \"something\"}";

    }

}
