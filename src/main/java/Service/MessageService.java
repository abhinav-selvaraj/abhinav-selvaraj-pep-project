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

    //Update Message given Id
    public Message updateMessageById(int id, String newText){
        if(newText.length() > 0 && newText.length() <= 255){
            return messageDAO.updateMessageById(id, newText);
        }
        return null;
       
    }

    //Create message
    public Message creatNewMessage(Message message){
        if(message.getMessage_text().length() > 0 && message.getMessage_text().length() < 255){
            return messageDAO.createMessage(message);
        }
        return null;
    }

}
