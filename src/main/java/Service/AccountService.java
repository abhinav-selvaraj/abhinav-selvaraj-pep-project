package Service;
import DAO.AccountDAO;
import Model.Account;
import Model.Message;
import java.util.*;
public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account login(String username, String password){
        return accountDAO.login(username, password);
    }

    public Account registerUser(String username, String password){
        if(password.length() >= 4 && username.length() > 0){
            return accountDAO.registerUser(username, password);
        }
        return null;
    }

    public List<Message> getAllMessages(int account_id){
        return accountDAO.getAllMessages(account_id);
    }
}
