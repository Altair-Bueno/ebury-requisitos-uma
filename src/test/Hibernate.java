import database.HibernateStartUp;
import database.tables.AddressEntity;
import database.tables.ClientEntity;
import database.tables.LoginEntity;
import database.types.Rol;
import database.types.Status;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;

public class Hibernate {
    @Test
    public void test1() {
        try (var session = HibernateStartUp.getSessionFactory().openSession()) {
            var client = new ClientEntity(Status.Active,"1111","pepe",new Date(1000));
            var address = new AddressEntity(client,"aaa","aaaa","aaaaa","aaaa","aaa");
            session.persist(client);
            session.persist(address);
            session.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
