package com.vix.exercise.service.impl;

import com.vix.exercise.database.impl.DatabaseConnectionImpl;
import com.vix.exercise.service.poller.PollLookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PollingResource {
    private final static Logger LOGGER = LoggerFactory.getLogger(PollingResource.class);

    private final static String SELECT_QUERY = "select * from polling";
    private final static String INSERT_QUERY = "insert into Polling (status) values (?)";
    private final static String UPDATE_QUERY = "update polling set status = ? where id = ?";
    private final static String DELETE_QUERY = "delete from polling where id = ?";

    @Autowired
    private DatabaseConnectionImpl databaseConnection;

    public PollingResource(DatabaseConnectionImpl dbConnection) {
        databaseConnection = dbConnection;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPollingRecordList()  {
        List<Polling> pollingRecordsList = new ArrayList<>();
        try {
            pollingRecordsList =  databaseConnection.getPollingRecords(SELECT_QUERY);

            if (pollingRecordsList.isEmpty()) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }

            return Response.status(200).entity(pollingRecordsList).build();

        } catch (RuntimeException | SQLException ex) {
            ex.printStackTrace();
            return Response.serverError().entity(ex.getMessage()).build();
        }

    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createPollingRecord(String status){
        try {
            int rowsAffected = databaseConnection.createPollingRecords(INSERT_QUERY, status);
            return Response.ok().build();
        } catch (RuntimeException | SQLException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response poll(String url) {
        try {
            String pollingResponse = PollLookupService.getVixDigitalResponse(url);
            int rowsAffected = databaseConnection.createPollingRecords(INSERT_QUERY, pollingResponse);
            return Response.ok().build();
        } catch (RuntimeException | SQLException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }


    }

    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("{id}")
    public Response updatePollingRecord(@PathParam("id") int id, String status) {

        try {
            Polling polling = new Polling(id, status);
            int result = databaseConnection.updatePollingRecords(UPDATE_QUERY, polling);
            if (result == 0) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return Response.ok().build();
        } catch (RuntimeException | SQLException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }

    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {

        try {
            boolean result = databaseConnection.deletePollingRecord(DELETE_QUERY, id);
            if (result) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return Response.status(Response.Status.GONE).build();
        } catch (RuntimeException | SQLException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }

}
