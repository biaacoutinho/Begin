const db = require('../db');
const fs = require('fs');

exports.postPicture = ('/upload/:username', async (req, res) => {
    try {
        const username = req.params.username;
        console.log(username)

        const base64Image = req.body.base64Image; 
        const binaryImage = Buffer.from(base64Image, 'base64');
        console.log(base64Image)
        console.log(binaryImage)

        const { data, error } = await db
            .storage
            .from('profilePictures')
            .upload( username, binaryImage, {
                cacheControl: '30',
                upsert: false
            });

        console.log(data)
        console.log("erro " + erro)
        res.status(200).json({ error: data.message });
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
});
