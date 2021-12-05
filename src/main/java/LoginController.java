import database.LoginEntity;
import gui.Login;
import com.mysql.cj.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class LoginController {
    private Login loginView;
    private LoginEntity loginEntity;

    public LoginController(Login l){
        loginView = l;
        loginView.getLoginButton().addActionListener(e->botonPresionado());

    }

    public void botonPresionado(){ // DB Query
      String user = loginView.getUsernameField().getText();
      String pw = String.valueOf(loginView.getPasswordField().getPassword());

      try(Session session = HibernateStartUp.getSessionFactory().openSession()){
          Query query = session.createQuery("FROM LoginEntity WHERE user = '" + user + "' AND password = '" + pw + "'");
          List list = query.list();
          if(list.isEmpty()){
              System.out.println("No encontrado");
              loginView.failure();
          } else{
              System.out.println("EXITO");
              loginView.success();
          }

      } catch (Exception e) {
          e.printStackTrace();
      }

    }

}
