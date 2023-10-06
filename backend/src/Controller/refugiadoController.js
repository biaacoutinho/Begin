const db = require('../db')

exports.getRefugiados = ('/refugiados', async(req, res) => {
  try {
    const { data, error } = await db.from('Refugiado').select('*');
    if (error) {
      throw error;
    }
    res.json(data);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

exports.getRefugiado = ('/refugiado/:username', async(req, res) => {
  const username = req.params.username;
  console.log(username)
  try {
    const { data, error } = await db.from('Refugiado').select('*').eq('username', username);
    if (error) {
      throw error;
    }
    res.json(data);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
})

exports.putRefugiado = ('/refugiado', async(req, res) => {
  const username = req.body.username;
  const nome = req.body.nome;
  const senha = req.body.senha;
  const idioma = req.body.idioma;
  const pais = req.body.paisDeOrigem;
  const telefone = req.body.telefone;
  const email = req.body.email;

  try {
    const { data, error } = await db
      .from('Refugiado')
      .insert([
        { username: username, nome: nome, senha: senha, idioma: idioma, paisDeOrigem: pais, telefone: telefone, email: email },
      ])
      .select()

    if (error) {
    throw error;
    }
    res.json(data);
  } catch (error) {
      res.status(400).json({ error: error.message });
  }
});

exports.postRefugiado = ('/refugiado/:username', async(req, res) => {
  const username = req.params.username;
  const nome = req.body.nome;
  const senha = req.body.senha;
  const idioma = req.body.idioma;
  const pais = req.body.paisDeOrigem;
  const telefone = req.body.telefone;
  const email = req.body.email;

  try {
    const { data, error } = await db
      .from('Refugiado')
      .update({ nome: nome, senha: senha, idioma: idioma, paisDeOrigem: pais, telefone: telefone, email: email })
      .eq('username', username)
      .select()

    if (error) {
    throw error;
    }
    res.json(data);
  } catch (error) {
      res.status(400).json({ error: error.message });
  }
});

exports.deleteRefugiado = ('/refugiado/:username', async(req, res) => {
  const username = req.params.username; 

  try {
    const { data, error } = await db
      .from('Refugiado')
      .delete()
      .eq('username', username)
      .select()

    if (error) {
    throw error;
    }
    res.json(data);
  } catch (error) {
      res.status(400).json({ error: error.message });
  }
})