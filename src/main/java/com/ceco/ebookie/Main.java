package com.ceco.ebookie;

import com.ceco.ebookie.hibernate.Transactional;
import com.ceco.ebookie.model.Ebook;
import com.ceco.ebookie.model.User;
import org.hibernate.Session;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 03-Jan-2016
 */
public class Main {
    public static void main(String[] args) {

        new Transactional<Void>() {
            @Override
            protected void doInTransaction(Session session) throws Exception {
                User user1 = new User();
                user1.setFirstName("Tsvetan");
                user1.setLastName("Dimitrov");
                user1.setUsername("powerslider");
                user1.setPassword("abc");
                user1.setEmail("tsvetan.dimitrov23@gmail.com");

                User user2 = new User();
                user2.setFirstName("Monica");
                user2.setLastName("Shopova");
                user2.setUsername("monnn");
                user2.setPassword("123");
                user2.setEmail("monica.shopova@gmail.com");

                Ebook ebook1 = new Ebook();
                ebook1.setTitle("Ebook1_title");
                ebook1.setSubtitle("Ebook1_subtitle");
                ebook1.setDescription("Ebook1_description");
                ebook1.addNewUserLike(user2);
                ebook1.setAuthor(user1);

                Ebook ebook2 = new Ebook();
                ebook2.setTitle("Ebook2_title");
                ebook2.setSubtitle("Ebook2_subtitle");
                ebook2.setDescription("Ebook2_description");
                ebook2.addNewUserLike(user1);
                ebook2.addNewUserLike(user2);
                ebook2.setAuthor(user2);

                user1.addFavouriteBook(ebook1);
                user1.addFavouriteBook(ebook2);
                user2.addFavouriteBook(ebook1);

                session.save(user1);
                session.save(user2);
                session.save(ebook1);
                session.save(ebook2);
            }
        }.execute();
    }
}
