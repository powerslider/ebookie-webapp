package com.ceco.ebookie.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Model class representing a subsection of a section of a chapter of an ebook.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 03-Jan-2016
 */
@Entity
@Table(name = "subsection")
public class Subsection {

    private Integer id;

    private String title;

    private Section section;

    private String content;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subsection_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "subsection_title", nullable = false, length = 150)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @Column(name = "subsection_content")
    @Type(type = "text")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
