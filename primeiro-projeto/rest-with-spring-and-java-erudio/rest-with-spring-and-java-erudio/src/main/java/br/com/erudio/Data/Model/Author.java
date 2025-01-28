package br.com.erudio.Data.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "author")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "nationality", nullable = false, length = 80)
    private String nationality;

    @Column(name = "gender", nullable = false, length = 9)
    private String gender;

    @Column(nullable = false)
    private boolean enabled;

    @JsonIgnore
    public Author disableAuthor() {
        this.enabled = false;
        return this;
    }
}
