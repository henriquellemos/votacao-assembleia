package br.com.sicredi.votacao.integration.datastore.entity;

import br.com.sicredi.votacao.integration.datastore.entity.enumerator.StatusVoto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@Entity
@Table(name = "associado")
@AllArgsConstructor
@NoArgsConstructor
public class AssociadoEntity {

    @Id
    private Long cpf;

    private String nome;

    @Enumerated(EnumType.STRING)
    private StatusVoto status;

    @OneToMany(mappedBy = "associado")
    @ToString.Exclude
    @JsonManagedReference
    private List<RegistroVotoEntity> registros;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AssociadoEntity that = (AssociadoEntity) o;
        return cpf != null && Objects.equals(cpf, that.cpf);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
