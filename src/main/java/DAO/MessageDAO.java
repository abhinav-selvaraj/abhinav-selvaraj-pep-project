package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class MessageDAO {
    //Get all messages
    public List<Message> getAllMessage(){
        Connection con = ConnectionUtil.getConnection();
        List<Message> list = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message ms = new Message(rs.getInt("message_id"),rs.getInt("posted_by"), 
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                list.add(ms);
            
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    //Retrieve one message given ID
    public Message getMessageById(int messageId){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, messageId);
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                Message ms = new Message(rs.getInt("message_id"),rs.getInt("posted_by"), 
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return ms;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //Delete one message given an id
    public Message deleteMessageById(int messageid){
        Connection con = ConnectionUtil.getConnection();
        Message message = getMessageById(messageid);
        if(message != null){
            try{
                String sql = "DELETE FROM message WHERE message_id = ? ";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, messageid);
                ps.executeUpdate();
                return message;
                // while(rs.next()){
                //     Message ms = new Message(rs.getInt("message_id"),rs.getInt("posted_by"), 
                //     rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                //     return ms;
                // }
            }catch(SQLException e){
                System.out.println("exception occured");
                e.printStackTrace();
            }
        }
         return null;
       
    }

     //Update message given message id
    public Message updateMessageById(int messageId, String newText){
        Connection con = ConnectionUtil.getConnection();
        Message message = getMessageById(messageId);
        if(message != null){
            try{
                String sql = "UPDATE message SET message_text = ? Where message_id = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, newText);
                ps.setInt(2, messageId);
                ps.executeUpdate();
                return getMessageById(messageId);
            }catch(SQLException e){
                e.printStackTrace();
            }
            
        }
        return null;
    }
    
    //Create a new message
    public Message createMessage(Message message){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "Insert into message (posted_by, message_text, time_posted_epoch) values (?, ? ,?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int messageId = generatedKeys.getInt(1);
                return getMessageById(messageId);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
