package br.com.leaf.sintasebemapp.infra.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "TB_ESTABELECIMENTOS", schema = "sinta_se_bem")
public class EstabelecimentosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "TX_NOME_ESTABELECIMENTO", nullable = false, length = 100)
    private String nomeEstabelecimento;

    @Column(name = "TX_CNPJ", nullable = false, length = 14, unique = true)
    private String cnpj;

    @OneToOne
    @JoinColumn(name = "ID_ENDERECO", nullable = false, referencedColumnName = "id")
    private EnderecosEntity endereco;


}
