const express = require("express");
const db = require("./database");
const app = express();
app.use(express.json());
const insert = db.prepare("INSERT INTO users (name, email) VALUES (?, ?)");
insert.run("Alice", "alice@example.com");
insert.run("Bob", "bob@example.com");
app.get("/api/users", (req, res) => {
    const users = db.prepare("SELECT * FROM users").all();
    res.json(users);});
app.get("/api/users/:id", (req, res) => {
    const user = db
        .prepare("SELECT * FROM users WHERE id = ?")
        .get(req.params.id);
    if (!user) {
        return res.status(404).json({ message: "User not found" });}
    res.json(user);});
app.post("/api/users", (req, res) => {
    const { name, email } = req.body;
    const insert = db.prepare("INSERT INTO users (name, email) VALUES (?, ?)");
    const result = insert.run(name, email);
    const newUser = db
        .prepare("SELECT * FROM users WHERE id = ?")
        .get(result.lastInsertRowid);
    res.status(201).json(newUser);
});
module.exports = app;