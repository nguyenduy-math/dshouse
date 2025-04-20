
const { MongoClient, ObjectId } = require('mongodb');
const express = require('express');
const { randomInt, randomUUID } = require('crypto');

//write a function to get an object from the mongodb database by its ObjectId