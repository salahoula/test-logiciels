const express = require("express");
const app = express();
app.use(express.json());
let users = [
    { id: 1, name: "Alice", email: "alice@example.com" },
    { id: 2, name: "Bob", email: "bob@example.com" },
];
// GET /api/users
app.get("/api/users", (req, res) => {
    res.json(users);
});
// GET /api/users/:id
app.get("/api/users/:id", (req, res) => {
    const user = users.find((u) => u.id === parseInt(req.params.id));
    if (!user) {
        return res.status(404).json({ message: "User not found" });
    }
    res.json(user);
});
// POST /api/users (nouveau point de terminaison pour ajouter un utilisateur)
app.post("/api/users", (req, res) => {
    const { name, email } = req.body;
    if (!name || !email) {
        return res.status(400).json({ message: "Name and email are required" });
    }
    const newUser = {
        id: users.length + 1,
        name,
        email,
    };
    users.push(newUser);
    res.status(201).json(newUser); // Renvoie l'utilisateur créé avec un statut 201
});

module.exports = app;
