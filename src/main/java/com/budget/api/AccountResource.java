package com.budget.api;

import com.budget.entities.Account;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/accounts")
public class AccountResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        return Response.status(200).entity(new ArrayList<Account>()).build();
    }
}
