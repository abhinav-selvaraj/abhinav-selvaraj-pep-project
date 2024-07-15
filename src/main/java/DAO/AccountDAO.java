package DAO;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class AccountDAO {

    public Account login(String username, String password){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account where username = ? AND password = ? ";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Account user = new Account(resultSet.getInt("account_id"), resultSet.getString("username"), resultSet.getString("password"));
                return user;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Account registerUser(String username, String password){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "Insert into Account (username, password) values (? ,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            return login(username, password);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getAllMessages(int account_id){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "Select * FROM message left join account on account.account_id = message.posted_by where account.account_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            List<Message> list = new ArrayList<>();
            while(rs.next()){
                Message ms = new Message(rs.getInt("message_id"),rs.getInt("posted_by"), 
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                list.add(ms);
            }
            return list;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
