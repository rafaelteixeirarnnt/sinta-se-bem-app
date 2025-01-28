package br.com.leaf.sintasebemapp.infra.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "TB_PERFIS", schema = "sinta_se_bem")
public class PerfisEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "ID", updatable = false, nullable = false)
        private UUID id;

        @Column(name = "TX_DESCRICAO", nullable = false, length = 50, updatable = false, insertable = false)
        private String descricao;

}
