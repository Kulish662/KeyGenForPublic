package ru.vniia.keygen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="keys")
public class Key {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String serial;
    private String key;
    //Фио для кого генерируется ключ
    private String licenceOwner;
    private String comment;
    private Date expireDate;
    private Date generateDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User author;

    @OneToOne
    @JoinColumn(name = "organisation_id")
    private Organization organization;

    public Key() {
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date date) {
        this.expireDate = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getLicenceOwner() {
        return licenceOwner;
    }

    public void setLicenceOwner(String licenceOwner) {
        this.licenceOwner = licenceOwner;
    }
}
