package br.com.leaf.sintasebemapp.infra.persistence.jpa.repository;

import br.com.leaf.sintasebemapp.infra.persistence.jpa.entity.EnderecosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecosEntity, UUID> {
}
