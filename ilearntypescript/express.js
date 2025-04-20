const { MongoClient, ObjectId } = require('mongodb');
const express = require('express');
const { randomInt, randomUUID } = require('crypto');

const client = new MongoClient("xyz");
const app = express();

//write a function to get a object from the monogodb
async function getDocument(client){
    return await client.db('sample_training').collection('companies').findOne({});
}
//write a function to get a object from the mongodb using an id or key
async function getDocumentById(client, coll, __id){
    console.log(__id);
    //return await client.db('sample_training').collection('log1').findOne({ _id: new ObjectId("67d6c2ff90def80a5c0fc317")});
    return await client.db('sample_training').collection(coll).findOne({ _id: __id});
}

//write a function to get a object from the mongodb using an object id

//write a function to save a object to the monogodb
async function saveDocument(client, coll, doc){
    return await client.db('sample_training').collection(coll).insertOne(doc);
}
// Define a route for the root URL
app.get('/apis/savelog1', async (req, res) => {
    fresult = null;
    try {
        // Connect to the MongoDB cluster
        await client.connect();

        _id = null;
        await saveDocument(client, "log1", {name: 'Company Inc', location: 'USA', r: randomUUID()}).then(result => {
            _id = result.insertedId;
        });

        if (_id != null){
            await getDocumentById(client, "log1", _id).then(result => {
                fresult = result;
            })
        }

        //await getDocument(client).then(result => {   
        //   fresult = result;
        //});
    } catch (e) {
        console.error(e);
    } finally {
        await client.close();
    }
    // Send Hello, World! as the response
    res.send(fresult);
});

// Define a route for the root URL
app.get('/apis/savelog2', async (req, res) => {
    fresult = null;
    try {
        // Connect to the MongoDB cluster
        await client.connect();

        _id = null;
        await saveDocument(client, "log2", {name: 'Company Inc', location: 'USA', r: randomUUID()}).then(result => {
            _id = result.insertedId;
        });

        if (_id != null){
            await getDocumentById(client, "log2", _id).then(result => {
                fresult = result;
            })
        }

        //await getDocument(client).then(result => {   
        //   fresult = result;
        //});
    } catch (e) {
        console.error(e);
    } finally {
        await client.close();
    }
    // Send Hello, World! as the response
    res.send(fresult);
});

// Start the server on port 3000
app.listen(3000, () => {
    console.log('Server is running on http://localhost:3000');
});