

let conn = new Mongo();
db = conn.getDB("artistmongodb");

db.artists.createIndex({ "artist_id": 1 }, { "unique": true });

db.artists.insert({ "artist_id":1,  "artist_name": "Barbara", "artist_surname": "Guillén", "artistic_name": "Babi" });
db.artists.insert({ "artist_id":2,  "artist_name": "Ana", "artist_surname": "García", "artistic_name": "Gata Cattana" });
db.artists.insert({ "artist_id":3,  "artist_name": "Adam", "artist_surname": "Richard", "artistic_name": "Calvin Harris" });