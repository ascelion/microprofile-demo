package ascelion.kalah.players;

import ascelion.kalah.shared.bbm.BeanToBean;

import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface PlayerMapper extends BeanToBean<Player, PlayerEntity> {
}
