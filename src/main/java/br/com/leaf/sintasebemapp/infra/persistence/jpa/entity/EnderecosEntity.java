package br.com.leaf.sintasebemapp.infra.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_ENDERECOS", schema = "sinta_se_bem")
public class EnderecosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "TX_LOGRADOURO", nullable = false, length = 100)
    private String logradouro;

    @Column(name = "TX_BAIRRO", nullable = false, length = 100)
    private String bairro;

    @Column(name = "TX_CIDADE", nullable = false, length = 100)
    private String cidade;

    @Column(name = "TX_ESTADO", nullable = false, length = 2)
    private String estado;

    @Column(name = "TX_CEP", nullable = false, length = 8)
    private String cep;

    @Column(name = "TX_NUMERO", length = 10)
    private String numero;

    @Column(name = "TX_COMPLEMENTO", length = 100)
    private String complemento;

}
