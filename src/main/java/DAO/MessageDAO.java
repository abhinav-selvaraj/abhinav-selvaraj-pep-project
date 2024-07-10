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
            String sql = "SELECT * FROM msg WHERE message_id = ?";
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
    public Message deleteMessageById(int message_id){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE * FROM message WHERE message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message ms = new Message(rs.getInt("message_id"),rs.getInt("posted_by"), 
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return ms;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
