package ascelion.kalah.boards;

import java.util.UUID;

import ascelion.kalah.engine.model.Board;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface BoardsRepository extends EntityRepository<Board, UUID> {
}
