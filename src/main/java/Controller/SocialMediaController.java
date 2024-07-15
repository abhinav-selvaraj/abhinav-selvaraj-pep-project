package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService as;
    MessageService ms;
    public SocialMediaController(){
        this.as = new AccountService();
        this.ms = new MessageService();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerUserHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserHandler);
        

        return app;
    }

    
    private void registerUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(ctx.body(), Account.class);
        Account registeredUser = as.registerUser(user.getUsername(), user.getPassword());
        if(registeredUser != null){
            ctx.json(mapper.writeValueAsString(registeredUser));
        }else{
            ctx.status(400);
        }

    }

    private void loginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(ctx.body(), Account.class);
        Account validatedUser = as.login(user.getUsername(), user.getPassword());
        if(validatedUser != null){
            ctx.json(mapper.writeValueAsString(validatedUser));
        }else{
            ctx.status(401);
        }
    }


    private void createNewMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = ms.creatNewMessage(message);
        if(newMessage != null){
            ctx.json(mapper.writeValueAsString(newMessage));
        }else{
            ctx.status(400);
        }
    }
    
    private void getAllMessagesHandler(Context ctx){
        List<Message> list = ms.getAllMessages();
        ctx.json(list);
    }
    
    private void getMessageByIdHandler(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = ms.getMessageById(id);

        if(message != null){
            ctx.json(message);
        }else{
            ctx.status(200);
        }
        
    }
    
    private void deleteMessageByIdHandler(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = ms.deleteMessageById(id);
        if(message != null){
            ctx.json(message);
        }else{
            ctx.status(200);
        }
    }


    

    

    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = ms.updateMessageById(id, message.getMessage_text());
        if(updatedMessage != null){
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }else{
            ctx.status(400);
        }
    }

    private void getMessagesByUserHandler(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages= as.getAllMessages(id);
        ctx.json(messages);
    }
}