create schema if not exists locationms;

create TABLE IF NOT EXISTS locationms.location (
	id uuid NOT NULL DEFAULT random_uuid(),
	suburbs varchar(24),
	state varchar(24),
	country varchar(24),
	pincode varchar(10),
	PRIMARY KEY(id)
);

INSERT INTO locationms.location VALUES ('a731fda1-aaad-42ea-bdbc-a27eeebe2cc0','Melbourne', 'VIC', 'Australia', '3000');