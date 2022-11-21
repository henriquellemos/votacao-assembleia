package br.com.sicredi.votacao.integration.datastore.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@Builder
@Entity
@Table(name = "pauta")
@AllArgsConstructor
@NoArgsConstructor
public class PautaEntity {

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

    private String nome;
    private String descricao;
    private Boolean votada;

    @OneToOne(mappedBy = "pauta")
    @JsonManagedReference
    private SessaoEntity sessao;

    @OneToMany(mappedBy = "pauta")
    @ToString.Exclude
    @JsonManagedReference
    private List<VotoEntity> votos;

    @OneToMany(mappedBy = "pautaVotada")
    @ToString.Exclude
    @JsonManagedReference
    private List<RegistroVotoEntity> registros;

    @Column(name = "total_votos")
    private Long totalVotos;

    @Column(name = "total_sim")
    private Long totalSim;

    @Column(name = "total_nao")
    private Long totalNao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PautaEntity that = (PautaEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
