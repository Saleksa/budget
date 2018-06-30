package com.budget.api;

import com.budget.PostgressConfig;
import com.budget.entities.Account;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/accounts")
public class AccountResource {

    private static final Logger LOGGER = Logger.getLogger(AccountResource.class.getName());

    private List<Account> parseResultSet(ResultSet set) {
        List<Account> list = new ArrayList<>();
        try {
            while (set.next()) {
                list.add(new Account(
                        set.getInt("id"),
                        set.getString("name"))
                );
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
        return list;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() throws SQLException, IOException {
        LOGGER.log(Level.INFO, "Getting all accounts");

        Connection connection = DriverManager.getConnection(
                PostgressConfig.getInstance().getUrl(),
                PostgressConfig.getInstance().getName(),
                PostgressConfig.getInstance().getPassword()
        );
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT id, name FROM Account");

        return Response.status(200).entity(parseResultSet(result)).build();
    }
}
