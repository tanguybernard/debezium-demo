db = db.getSiblingDB('company-sync');

db.createCollection('user');

/*
db.users.insertMany([
    { name: "Alice", email: "alice@example.com" },
    { name: "Bob", email: "bob@example.com" }
]);
 */