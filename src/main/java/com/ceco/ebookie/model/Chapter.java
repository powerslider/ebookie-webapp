package com.ceco.ebookie.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class representing a chapter of an ebook.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 02-Jan-2016
 */
@Entity
@Table(name = "chapter")
public class Chapter {

    private Integer id;

    private String title;

    private Ebook ebook;

    private Integer chapterNumber;

    private Set<Section> sections;

    private String content;


    public Chapter() {
        this.sections = new HashSet<>();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ebook_id")
    public Ebook getEbook() {
        return ebook;
    }

    public void setEbook(Ebook ebook) {
        this.ebook = ebook;
    }

    @Column(name = "chapter_title", nullable = false, length = 150)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "chapter_number", nullable = false)
    public Integer getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(Integer chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public void addNewSection(Section newSection) {
        this.sections.add(newSection);
    }

    @Column(name = "chapter_content")
    @Type(type = "text")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
