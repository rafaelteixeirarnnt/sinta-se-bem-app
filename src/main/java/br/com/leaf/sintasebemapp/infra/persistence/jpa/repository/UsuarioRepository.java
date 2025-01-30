package br.com.leaf.sintasebemapp.infra.persistence.jpa.repository;

import br.com.leaf.sintasebemapp.infra.persistence.jpa.entity.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuariosEntity, UUID> {


    Optional<UsuariosEntity> findByCpf(String cpf);
}
