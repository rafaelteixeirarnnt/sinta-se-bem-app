package br.com.leaf.sintasebemapp.infra.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "TB_USUARIOS", schema = "sinta_se_bem")
public class UsuariosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "TX_NOME", nullable = false, length = 50)
    private String nome;

    @Column(name = "TX_EMAIL", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "TX_SENHA", nullable = false, length = 10)
    private String senha;

    @Column(name = "TX_TELEFONE", nullable = false, length = 11)
    private String telefone;

    @Column(name = "FL_PRESTADOR_SERVICO", nullable = false)
    private Boolean prestadorServico;

    @ManyToOne
    @JoinColumn(name = "ID_PERFIL", nullable = false, referencedColumnName = "id")
    private PerfisEntity perfil;

    @OneToOne
    @JoinColumn(name = "ID_ENDERECO", nullable = false, referencedColumnName = "id")
    private EnderecosEntity endereco;

}
