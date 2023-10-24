const db = require('../db');
const fs = require('fs');
const path = require('path'); // Importe o módulo 'path'

exports.postPicture = ('/upload/:username', async (req, res) => {
    try {
        const username = req.params.username;
        // Use __dirname para obter o diretório atual do seu arquivo JavaScript
        //const currentDir = __dirname;

        // Construa o caminho completo para o arquivo 'fotinho.png'
        //const avatarFilePath = path.join(currentDir, 'fotinho.jpg');

        // Leia o arquivo do sistema de arquivos
        //const avatarFile = fs.readFileSync(avatarFilePath);

        const avatarFile = req.body.profilePicture;

        const { data, error } = await db
            .storage
            .from('profilePictures')
            .upload( username, avatarFile, {
                cacheControl: '3600',
                upsert: false
            });

        // Resto do seu código aqui...

        console.log(data)

    } catch (error) {
        res.status(400).json({ error: error.message });
    }
});
