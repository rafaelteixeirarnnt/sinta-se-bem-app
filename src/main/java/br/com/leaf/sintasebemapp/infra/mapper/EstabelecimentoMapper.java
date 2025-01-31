package br.com.leaf.sintasebemapp.infra.mapper;

import br.com.leaf.sintasebemapp.domain.models.Estabelecimento;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.entity.EstabelecimentosEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstabelecimentoMapper {

    EstabelecimentosEntity toEntity(Estabelecimento estabelecimento);

    Estabelecimento toEstabelecimento(EstabelecimentosEntity entity);

}
