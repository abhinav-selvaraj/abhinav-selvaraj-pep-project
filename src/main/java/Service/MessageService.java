package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.*;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    //Return all message
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessage();
    }

    //Return Message given Id
    public Message getMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    //Delete Message given Id
    public Message deleteMessageById(int id){
        return messageDAO.deleteMessageById(id);
    }
}
