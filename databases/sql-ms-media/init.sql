
DROP DATABASE IF EXISTS MediaSqlDb;

CREATE DATABASE MediaSqlDb;

DROP TABLE IF EXISTS public.medias;

CREATE TABLE public.medias (
	media_id bigserial NOT NULL,
	media_name varchar(100) NOT NULL,
	media_genre varchar(50) NOT NULL,
	media_class varchar(50) NOT NULL,
	artist_id bigserial NOT NULL,
	CONSTRAINT medias_pkey PRIMARY KEY (media_id)
);

INSERT INTO medias(media_name, media_genre, media_class, artist_id) VALUES
('Flores', 'INDIE', 'SONG', 1),
('Marte ft Denom', 'HIPHOP','SONG', 1),
('Desierto', 'SAD','SONG', 1),
('Banzai', 'HIPHOP','VIDEOCLIP', 2),
('Gotham', 'HIPHOP','SONG', 2),
('Sweet nothing', 'ELECTRONIC','LIVE', 3);
