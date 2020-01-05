package ascelion.kalah.players;

import ascelion.kalah.shared.persistence.EntityRepo;

import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PlayerEntity.class)
public interface PlayersRepository extends EntityRepo<PlayerEntity> {
}
