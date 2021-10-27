package com.ibento;

import static com.mongodb.client.model.Filters.eq;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Sorts.descending;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class IbentoDao {

    private static final String ID = "_id";
    private static final String DB_NAME = "dev";
    private static final String COLLECTION_NAME = "ibentos";
    private static final ConnectionString connectionString =
            new ConnectionString("mongodb+srv://kumo:kumo@dev.ijikl.mongodb.net/ibentos?retryWrites=true&w=majority");

    private MongoCollection<Document> dbCollection;

    //TODO: Créer une config générique d'accès aux données (spring config)
    @PostConstruct
    public void initDataBaseCollection() throws UnknownHostException {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        this.dbCollection = database.getCollection(COLLECTION_NAME);
    }

    public Document get(String id) {
        return this.dbCollection.find(eq(ID, new ObjectId(id))).first();
    }

    public List<Document> getAll() {
        return this.dbCollection.find().sort(sorting()).into(new ArrayList<Document>());
    }

    public List<Document> find(String name) {
        Document query = new Document("name", compile(name, CASE_INSENSITIVE));
        return this.dbCollection.find(query).sort(sorting()).into(new ArrayList<Document>());
    }

    public Document create(Document documentToInsert) {
        documentToInsert.remove(ID);
        this.dbCollection.insertOne(documentToInsert);
        return documentToInsert;
    }

    public Document update(Document documentToUpdate) {
        Document query = new Document(ID, new ObjectId((String) documentToUpdate.get(ID)));
        Object id = documentToUpdate.remove(ID);
        this.dbCollection.updateOne(query, new BasicDBObject("$set", documentToUpdate));
        documentToUpdate.put(ID, new ObjectId(id.toString()));
        return documentToUpdate;
    }

    public void delete(String id) {
        this.dbCollection.deleteOne(new BasicDBObject(ID, new ObjectId(id)));
    }

    private Bson sorting() {
        return descending("startDate");
    }
}
