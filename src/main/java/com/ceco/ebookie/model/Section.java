package com.ceco.ebookie.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

/**
 * Model class representing a section of a chapter containing subsections.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 03-Jan-2016
 */
@Entity
@Table(name = "section")
public class Section {

    private Integer id;

    private String title;

    private Chapter chapter;

    private Set<Subsection> subsection;

    private String content;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "section_title", nullable = false, length = 150)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    public Set<Subsection> getSubsection() {
        return subsection;
    }

    public void setSubsection(Set<Subsection> subsection) {
        this.subsection = subsection;
    }

    @Column(name = "section_content")
    @Type(type = "text")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
