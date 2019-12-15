package com.aschuman.beansApi;

        import jdk.nashorn.internal.objects.annotations.Constructor;

        import javax.servlet.ServletContext;
        import javax.servlet.http.HttpServletRequest;
        import javax.sql.DataSource;
        import javax.ws.rs.*;
        import javax.ws.rs.core.GenericEntity;
        import javax.ws.rs.core.MediaType;
        import javax.ws.rs.core.Context;
        import javax.ws.rs.core.Response;
        import javax.ws.rs.core.Response.Status;
        import java.io.IOException;
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
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
        List<Bean> beansList = new ArrayList<>();
        DataSource pool = (DataSource) servletContext.getAttribute("my-pool");

        try (Connection conn = pool.getConnection()) {
            PreparedStatement getAll = conn.prepareStatement(
                    "SELECT bean_id, created, name, origin, supplier, url, rating, notes" +
                            " FROM beans ORDER BY created DESC"
            );
            ResultSet allBeans = getAll.executeQuery();

            while(allBeans.next()) {
                Bean new_bean = new Bean.Builder()
                        .setId(allBeans.getString(1))
                        .setName(allBeans.getString(3))
                        .setOrigin(allBeans.getString(4))
                        .setSupplier(allBeans.getString(5))
                        .setUrl(allBeans.getString(6))
                        .setRating(allBeans.getInt(7))
                        .setNotes(allBeans.getString(8))
                        .build();
                beansList.add(new_bean);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        GenericEntity<List<Bean>> outEntity = new GenericEntity<List<Bean>>(beansList) {};

        return Response
                .status(Status.OK)
                .entity(outEntity)
                .build();
    }

    @GET
    @Path("coffee/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") String id) {
        List<Bean> beansList = new ArrayList<>();
        DataSource pool = (DataSource) servletContext.getAttribute("my-pool");

        try (Connection conn = pool.getConnection()) {
            PreparedStatement getById = conn.prepareStatement(
                    "SELECT bean_id, created, name, origin, supplier, url, rating, notes" +
                            " FROM beans WHERE bean_id = ? ORDER BY created DESC"
            );
            getById.setString(1, id);
            ResultSet allBeans = getById.executeQuery();

            while(allBeans.next()) {
                Bean new_bean = new Bean.Builder()
                        .setId(allBeans.getString(1))
                        .setName(allBeans.getString(3))
                        .setOrigin(allBeans.getString(4))
                        .setSupplier(allBeans.getString(5))
                        .setUrl(allBeans.getString(6))
                        .setRating(allBeans.getInt(7))
                        .setNotes(allBeans.getString(8))
                        .build();
                beansList.add(new_bean);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        GenericEntity<List<Bean>> outEntity = new GenericEntity<List<Bean>>(beansList) {};

        return Response
                .status(Status.OK)
                .entity(outEntity)
                .build();
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
