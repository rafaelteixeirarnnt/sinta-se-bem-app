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

import java.time.LocalTime;
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

    @Column(name = "TX_NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "TX_CNPJ", nullable = false, length = 14, unique = true)
    private String cnpj;

    @Column(name = "TX_TELEFONE", nullable = false, length = 11)
    private String telefone;

    @Column(name = "TX_WHATSAPP", nullable = false, length = 11)
    private String whatsapp;

    @Column(name = "TX_EMAIL", nullable = false, length = 50)
    private String email;

    @Column(name = "HR_INICIO_ATEND", nullable = false)
    private LocalTime inicioAtendimento;

    @Column(name = "HR_FIM_ATEND", nullable = false)
    private LocalTime fimAtendimento;

    @OneToOne
    @JoinColumn(name = "ID_ENDERECO", nullable = false, referencedColumnName = "id")
    private EnderecosEntity endereco;

    @OneToOne
    @JoinColumn(name = "ID_RESPONSAVEL", nullable = false, referencedColumnName = "id")
    private UsuariosEntity usuarioResponsavel;

}