package br.com.sicredi.votacao.integration.datastore.entity;

import br.com.sicredi.votacao.integration.datastore.entity.enumerator.OpcaoVoto;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@Table(name = "voto")
@AllArgsConstructor
@NoArgsConstructor
public class VotoEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    @Type(type = "uuid-char")
    private UUID id;

    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    private OpcaoVoto voto;

    @ManyToOne
    @JoinColumn(name = "pauta_id", nullable = false)
    private PautaEntity pauta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VotoEntity that = (VotoEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
