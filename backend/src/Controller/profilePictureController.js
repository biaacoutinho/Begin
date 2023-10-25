const db = require('../db');
const fs = require('fs');

exports.postPicture = ('/upload/:username', async (req, res) => {
    try {
        const username = req.params.username;
        console.log(username)

        const imagePath = req.body.path; 
        const imageFile = fs.readFileSync(imagePath);
        console.log(imageFile)
        const { data, error } = await db
            .storage
            .from('profilePictures')
            .upload( username, imageFile, {
                cacheControl: '30',
                upsert: false
            });

        console.log(data)
        console.log("erro " + erro)
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
});
