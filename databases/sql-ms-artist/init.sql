
DROP DATABASE IF EXISTS ArtistSqlDb;

CREATE DATABASE ArtistSqlDb;

DROP TABLE IF EXISTS public.artists;

CREATE TABLE public.artists (
	artist_id bigserial NOT NULL,
	artist_name varchar(50) NOT NULL,
	artist_surname varchar(50) NOT NULL,
	artistic_name varchar(50) NOT NULL,
	CONSTRAINT artists_pkey PRIMARY KEY (artist_id)
);

INSERT INTO artists(artist_name, artist_surname, artistic_name) VALUES
('Barbara', 'Guillén', 'Babi'),
('Ana', 'García', 'Gata Cattana'),
('Adam', 'Richard', 'Calvin Harris');
