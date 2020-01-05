package ascelion.kalah.players;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class PlayerMapperTest {

	@Test
	void run() {
		assertThat(Mappers.getMapper(PlayerMapper.class), is(notNullValue()));
	}

}


