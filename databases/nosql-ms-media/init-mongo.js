
let conn = new Mongo();
db = conn.getDB("MediaNoSqlDb");

db.medias.createIndex({ "mediaId": 1 }, { "unique": true });

db.medias.insert({"mediaId":1, "mediaName":"Flores", "mediaGenre":"INDIE", "mediaClass":"SONG", "artistId":1});
db.medias.insert({"mediaId":2, "mediaName":"Marte ft Denom", "mediaGenre":"HIPHOP", "mediaClass":"SONG", "artistId":1});
db.medias.insert({"mediaId":3, "mediaName":"Desierto", "mediaGenre":"SAD", "mediaClass":"SONG", "artistId":1});
db.medias.insert({"mediaId":4, "mediaName":"Banzai", "mediaGenre":"HIPHOP", "mediaClass":"VIDEOCLIP", "artistId":2});
db.medias.insert({"mediaId":5, "mediaName":"Gotham", "mediaGenre":"HIPHOP", "mediaClass":"SONG", "artistId":2});
db.medias.insert({"mediaId":6, "mediaName":"Sweet nothing", "mediaGenre":"ELECTRONIC", "mediaClass":"LIVE", "artistId":3});

