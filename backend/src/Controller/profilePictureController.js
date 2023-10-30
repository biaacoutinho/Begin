const db = require('../db');
const fs = require('fs');

exports.postPicture = ('/picture/:username', async (req, res) => {
    try {
        const username = req.params.username;
        console.log(username)

        const base64Image = req.body.base64Image; 
        const binaryImage = Buffer.from(base64Image, 'base64');

        const { data, error } = await db
            .storage
            .from('profilePictures')
            .upload( username, binaryImage, {
                cacheControl: '30',
                upsert: true
            });

        console.log("data: " + data)
        res.status(200).json({ error: data });
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
});

exports.getPicture = ('/picture/:username', async(req, res) => {
    try{
        const username = req.params.username;
        const {data, error } = await db.storage
        .from('profilePictures')
        .getPublicUrl(username)

        res.status(200).json({ url: data.publicUrl });
    }
    catch (error) {
        res.status(400).json({ error: error.message });
    }
})