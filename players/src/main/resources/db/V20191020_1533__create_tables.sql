CREATE TABLE players
(
	id UUID NOT NULL DEFAULT gen_random_uuid(),

	score BIGINT NOT NULL DEFAULT 0,

	PRIMARY KEY(id)
);
