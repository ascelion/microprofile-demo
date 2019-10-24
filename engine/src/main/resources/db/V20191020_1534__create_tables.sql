CREATE TABLE boards
(
	id UUID NOT NULL DEFAULT gen_random_uuid(),

	south_id UUID NOT NULL,
	north_id UUID,
	player VARCHAR(20) NOT NULL,
	winner VARCHAR(20),

	PRIMARY KEY(id)
);

CREATE TABLE houses
(
	board_id UUID NOT NULL REFERENCES boards(id),
	index SMALLINT NOT NULL DEFAULT -1,
	seeds SMALLINT NOT NULL DEFAULT 0,

	PRIMARY KEY(board_id, index)
);
