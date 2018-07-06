package com.budget.api;

import com.budget.PostgressUtils;
import com.budget.entities.Account;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.*;
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

        Connection connection = PostgressUtils.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT id, name FROM Account");
        connection.close();

        return Response.status(200).entity(parseResultSet(result)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAccount(Account account) throws SQLException, JsonProcessingException {
        LOGGER.log(Level.INFO, "Add account");

        Connection connection = PostgressUtils.getInstance().getConnection();

        String sql = "INSERT INTO Account (name) values(?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, account.getName());
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            int  id = rs.getInt(1);
            account.setId(id);
            LOGGER.log(Level.INFO, "Account was added with id: " + id);
        }

        connection.close();
        return Response.status(200).entity(account.toString()).build();
    }
}
