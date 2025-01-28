package br.com.leaf.sintasebemapp.infra.persistence.jpa.repository;

import br.com.leaf.sintasebemapp.infra.persistence.jpa.entity.EstabelecimentosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EstabelecimentoRepository extends JpaRepository<EstabelecimentosEntity, UUID> {
}
