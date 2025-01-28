package br.com.erudio.Data.VO.V1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorVO extends RepresentationModel<AuthorVO> implements Serializable {

    @JsonProperty("id")
    private Long key;
    private String name;
    private String nationality;
    private String gender;
    private boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AuthorVO authorVO = (AuthorVO) o;
        return enabled == authorVO.enabled && Objects.equals(key, authorVO.key) && Objects.equals(name, authorVO.name) && Objects.equals(nationality, authorVO.nationality) && Objects.equals(gender, authorVO.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, name, nationality, gender, enabled);
    }
}
