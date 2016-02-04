package com.ceco.ebookie.controller;

import com.ceco.ebookie.model.Ebook;
import com.ceco.ebookie.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 03-Feb-2016
 */
@RequestMapping(value = "/api/ebooks")
@RestController
public class EbookController {

    @Autowired
    private EbookService ebookService;

    @RequestMapping(
            value = "/new",
            method = { RequestMethod.POST }
    )
    public ResponseEntity<String> createNewBook(@RequestBody Ebook newBook) {
        Boolean isEbookCreated = ebookService
                .createNewBook(newBook);
        if (isEbookCreated) {;
            return new ResponseEntity<>(
                    "Successfully added new book '" + newBook.getTitle() + "'", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "Error while adding new ebook '" + newBook.getTitle() + "'", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
