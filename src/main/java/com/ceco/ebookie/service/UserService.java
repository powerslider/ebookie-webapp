package com.ceco.ebookie.service;

import com.ceco.ebookie.hibernate.Transactional;
import com.ceco.ebookie.model.User;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 10-Jan-2016
 */
@Service
public class UserService {

    public User getAvailableUser(String username) {
        return new Transactional<User>() {
            @Override
            protected void doInTransaction(Session session) throws Exception {
                User user = (User) session.createCriteria(User.class)
                        .add(Restrictions.eq("username", username))
                        .setFetchMode("ebook", FetchMode.JOIN)
                        .uniqueResult();

                setReturnValue(user);
            }
        }.execute();
    }

    public Integer isUserAuthenticated(String username, String password) {
        return new Transactional<Integer>() {
            @Override
            protected void doInTransaction(Session session) throws Exception {
                User user = (User) session.createCriteria(User.class)
                        .add(Restrictions.eq("username", username))
                        .add(Restrictions.eq("password", password))
                        .uniqueResult();

                if (user != null) {
                    setReturnValue(user.getId());
                } else {
                    setReturnValue(Integer.MAX_VALUE);
                }
            }
        }.execute();
    }

    public Boolean createNewUser(User user) {
//        StatelessSession s = HibernateUtil.getStatelessSession();
//        s.beginTransaction();
//
//        s.createSQLQuery(
//                "insert into user (first_name, last_name, email, username, password) " +
//                        "values(:firstName, :lastName, :email, :username, :password)")
//                .setString("firstName", user.getFirstName())
//                .setString("lastName", user.getLastName())
//                .setString("email", user.getEmail())
//                .setString("username", user.getUsername())
//                .setString("password", user.getPassword())
//                .executeUpdate();
//
//        s.getTransaction().commit();
//        s.close();

        if (user != null) {
            new Transactional<Void>() {
                @Override
                protected void doInTransaction(Session session) throws Exception {
                    session.save(user);
                }
            }.execute();

            return true;
        }

        return false;
    }
}
