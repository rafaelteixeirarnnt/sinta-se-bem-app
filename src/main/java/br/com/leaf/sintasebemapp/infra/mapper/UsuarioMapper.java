package br.com.leaf.sintasebemapp.infra.mapper;

import br.com.leaf.sintasebemapp.domain.models.Usuario;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.entity.UsuariosEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuariosEntity toEntity(Usuario usuario);

    Usuario toUsuario(UsuariosEntity entity);

}
