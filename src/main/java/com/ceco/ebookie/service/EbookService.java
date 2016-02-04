package com.ceco.ebookie.service;

import com.ceco.ebookie.hibernate.Transactional;
import com.ceco.ebookie.model.Ebook;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 03-Feb-2016
 */
@Service
public class EbookService {

    public Boolean createNewBook(Ebook ebook) {
        if (ebook != null) {
            new Transactional<Void>() {
                @Override
                protected void doInTransaction(Session session) throws Exception {
                    session.save(ebook);
                }
            }.execute();

            return true;
        }

        return false;
    }
}
