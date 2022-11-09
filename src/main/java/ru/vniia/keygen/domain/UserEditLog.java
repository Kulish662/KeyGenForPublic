package ru.vniia.keygen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="logs")
public class UserEditLog {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="target_id")
    private User target;

    private Date date;

    private String oldRoles;

    private String newRoles;

    public UserEditLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOldRoles() {
        return oldRoles;
    }

    public void setOldRoles(String oldRoles) {
        this.oldRoles = oldRoles;
    }

    public String getNewRoles() {
        return newRoles;
    }

    public void setNewRoles(String newRoles) {
        this.newRoles = newRoles;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User authorId) {
        this.author = authorId;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User targetId) {
        this.target = targetId;
    }


}
