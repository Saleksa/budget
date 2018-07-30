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

    private List<Account> parseAccounts(ResultSet set) {
        List<Account> list = new ArrayList<>();
        try {
            while (set.next()) {
                list.add(parseAccount(set));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
        return list;
    }

    private Account parseAccount(ResultSet set) {
        try {
            return new Account(
                    set.getInt("id"),
                    set.getString("name"),
                    set.getString("description"),
                    set.getBigDecimal("balance"),
                    set.getBoolean("is_credit"),
                    set.getBigDecimal("credit_limit")
            );
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccountNames() throws SQLException, IOException {
        LOGGER.log(Level.INFO, "Getting all accounts");

        Connection connection = PostgressUtils.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Account");
        connection.close();

        return Response.status(200).entity(parseAccounts(result)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getAccount(@PathParam("id") int id) throws SQLException {
        LOGGER.log(Level.INFO, "Get account " + id);
        Connection connection = PostgressUtils.getInstance().getConnection();

        String sql = "SELECT * FROM Account WHERE id=(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();

        connection.close();
        result.next();
        return Response.status(200).entity(parseAccount(result)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAccount(Account account) throws SQLException, JsonProcessingException {
        LOGGER.log(Level.INFO, "Add account");

        Connection connection = PostgressUtils.getInstance().getConnection();

        String sql = "INSERT INTO Account (name,description,balance,is_credit,credit_limit) values(?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, account.getName());
        statement.setString(2, account.getDescription());
        statement.setBigDecimal(3, account.getBalance());
        statement.setBoolean(4, account.isCredit());
        statement.setBigDecimal(5, account.getCreditLimit());
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            int  id = rs.getInt(1);
            account.setId(id);
            LOGGER.log(Level.INFO, "Account was added with id: " + id);
        }

        connection.close();
        return Response.status(200).entity(account).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response editAccount(@PathParam("id") int id, Account newAccount) throws SQLException {
        LOGGER.log(Level.INFO, "Edit account");

        Connection connection = PostgressUtils.getInstance().getConnection();

        String sql = "UPDATE Account SET name=(?) WHERE id=(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, newAccount.getName());
        statement.setInt(2, id);
        statement.execute();

        LOGGER.log(Level.INFO, "Account was updated with id: " + id);
        connection.close();

        newAccount.setId(id);
        return Response.status(200).entity(newAccount).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAccount(@PathParam("id") int id) throws SQLException {
        LOGGER.log(Level.INFO, "Edit account");

        Connection connection = PostgressUtils.getInstance().getConnection();
        String sql = "DELETE FROM Account WHERE id=(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.execute();
        connection.close();
        return Response.status(200).entity(id).build();
    }

}
