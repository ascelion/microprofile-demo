package ascelion.kalah.players;

import java.util.UUID;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface PlayersRepository extends EntityRepository<Player, UUID> {
}
