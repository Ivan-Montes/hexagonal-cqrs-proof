

let conn = new Mongo();
db = conn.getDB("ArtistNoSqlDb");

db.artists.createIndex({ "artistId": 1 }, { "unique": true });

db.artists.insert({ "artistId":1,  "artistName": "Barbara", "artistSurname": "Guillén", "artisticName": "Babi" });
db.artists.insert({ "artistId":2,  "artistName": "Ana", "artistSurname": "García", "artisticName": "Gata Cattana" });
db.artists.insert({ "artistId":3,  "artistName": "Adam", "artistSurname": "Richard", "artisticName": "Calvin Harris" });