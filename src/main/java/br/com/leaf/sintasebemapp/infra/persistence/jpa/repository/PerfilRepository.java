package br.com.leaf.sintasebemapp.infra.persistence.jpa.repository;

import br.com.leaf.sintasebemapp.infra.persistence.jpa.entity.PerfisEntity;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.enums.PerfilEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PerfilRepository extends JpaRepository<PerfisEntity, UUID> {

    Optional<PerfisEntity> findByDescricao(String descricao);

}
