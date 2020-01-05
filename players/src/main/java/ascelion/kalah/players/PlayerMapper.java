package ascelion.kalah.players;

import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface PlayerMapper {

	PlayerEntity vtoe(Player view);

	Player etov(PlayerEntity ent);
}
