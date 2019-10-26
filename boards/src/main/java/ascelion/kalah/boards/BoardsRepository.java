package ascelion.kalah.boards;

import ascelion.kalah.engine.model.Board;
import ascelion.kalah.shared.persistence.EntityRepo;

import org.apache.deltaspike.data.api.Repository;

@Repository
public interface BoardsRepository extends EntityRepo<Board> {
}
