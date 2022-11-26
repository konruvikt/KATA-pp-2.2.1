package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUsersByCar(String model, int series) {

      String hqlId = "select id from Car car where car.model = :model and car.series = :series";
      String hqlUser = "from User user where user.id = :id";

      Long id;

      Session session = sessionFactory.getCurrentSession();

      Query queryId = session.createQuery(hqlId);
      queryId.setParameter("model", model);
      queryId.setParameter("series", series);
      id = (Long) queryId.getSingleResult();

      Query queryUser = session.createQuery(hqlUser);
      queryUser.setParameter("id", id);
      return (User) queryUser.getSingleResult();
   }
}
