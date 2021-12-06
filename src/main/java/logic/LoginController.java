package logic;

import database.HibernateStartUp;
import database.tables.LoginEntity;
import gui.Login;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import java.util.List;

public class LoginController {
    private Login loginView;
    private LoginEntity loginEntity;

    public LoginController(Login l){
        loginView = l;
        loginView.getLoginButton().addActionListener(e->botonPresionado());

    }

    public void botonPresionado(){ // DB Query
      loginView.loading();
      var barra = loginView.getProgressBar();

      String user = loginView.getUsernameField().getText();
      String pw = String.valueOf(loginView.getPasswordField().getPassword());

      barra.setValue(30);

      try(Session session = HibernateStartUp.getSessionFactory().openSession()){
          Query query = session.createQuery("FROM LoginEntity WHERE user = '" + user + "' AND password = '" + pw + "'");
          barra.setValue(70);
          List list = query.list();
          barra.setValue(100);
          if(list.isEmpty()){
              System.out.println("No encontrado");
              loginView.failure("Usuario y/o contraseña inválidos.");
          } else{
              System.out.println("EXITO");
              var userlogin = (LoginEntity) list.get(0);
              if(userlogin.getRol().equals("Regler")){
                  loginView.successAlemania();
              } else if (userlogin.getRol().equals("Regelgever")){
                  loginView.successHolanda();
              } else if (userlogin.getRol().equals("User")){
                  loginView.successUser();
              } else {
                  loginView.failure("Usuario erróneo en la base de datos.");
              }

          }

      } catch (Exception e) {
          e.printStackTrace();
      }

    }

}
