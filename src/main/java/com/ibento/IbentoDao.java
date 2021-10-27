package com.ibento;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.*;
import com.mongodb.client.model.Filters;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class IbentoDao {

    private static final String ID = "_id";
    private static final String URL_DB_HOST = "localhost:27017";
    private static final String DB_NAME = "local";
    private static final String COLLECTION_NAME = "ibentos";

    private DBCollection dbCollection;

    @PostConstruct
    public void initDataBaseCollection() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(URL_DB_HOST);
        this.dbCollection = mongoClient.getDB(DB_NAME).getCollection(COLLECTION_NAME);
    }

    public DBObject get(String id) {
        DBObject query = new BasicDBObject(ID, new ObjectId(id));
        DBCursor cursor = this.dbCollection.find(query);
        return cursor.one();
    }

    public List<DBObject> getAll() {
        return this.dbCollection.find().toArray();
    }

    public List<DBObject> find(String name) {
        BasicDBObject query = new BasicDBObject("name", compile(name, CASE_INSENSITIVE));
        return this.dbCollection.find(query).toArray();
    }

    public DBObject create(DBObject dbObjectToInsert) {
        this.dbCollection.insert(dbObjectToInsert);
        return dbObjectToInsert;
    }

    public DBObject update(DBObject dbObjectToUpdate) {
        BasicDBObject query = new BasicDBObject(ID, new ObjectId((String) dbObjectToUpdate.get(ID)));
        Object id = dbObjectToUpdate.removeField(ID);
        this.dbCollection.update(query, new BasicDBObject("$set", dbObjectToUpdate));
        dbObjectToUpdate.put(ID, new ObjectId(id.toString()));
        return dbObjectToUpdate;
    }

    public void delete(String id) {
        this.dbCollection.remove(new BasicDBObject(ID, new ObjectId(id)));
    }
}
