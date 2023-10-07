const db = require('../db')

exports.getVoluntarios = ('/voluntarios', async(req, res) => {
  try {
    const { data, error } = await db.from('Voluntario').select('*');
    if (error) {
      throw error;
    }
    res.json(data);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

exports.getVoluntario = ('/voluntario/:username', async(req, res) => {
  const username = req.params.username;

  try {
    const { data, error } = await db.from('Voluntario').select('*').eq('username', username);
    if (error) {
      throw error;
    }
    res.json(data);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})

exports.putVoluntario = ('/voluntario', async(req, res) => {
  const username = req.body.username;
  const nome = req.body.nome;
  const senha = req.body.senha;
  const idioma = req.body.idioma;
  const cpf = req.body.cpf;
  const telefone = req.body.telefone;
  const habilidade = req.body.habilidade;
  const email = req.body.email;
  console.log("foiii")
  try {
    const { data, error } = await db
      .from('Voluntario')
      .insert([
        { username: username, nome: nome, senha: senha, idioma: idioma, cpf: cpf, telefone: telefone, habilidade: habilidade, email: email },
      ])
      .select()

    if (error) {
    throw error;
    }
    res.json(data);
  } catch (error) {
      res.status(500).json({ error: error.message });
  }
});

exports.postVoluntario  = ('/voluntario/:username', async(req, res) => {
  const username = req.params.username;
  const nome = req.body.nome;
  const senha = req.body.senha;
  const idioma = req.body.idioma;
  const pais = req.body.paisDeOrigem;
  const telefone = req.body.telefone;
  const email = req.body.email;

  try {
    const { data, error } = await db
      .from('Voluntario')
      .update({ nome: nome, senha: senha, idioma: idioma, paisDeOrigem: pais, telefone: telefone, email: email })
      .eq('username', username)
      .select()

    if (error) {
    throw error;
    }
    res.json(data);
  } catch (error) {
      res.status(500).json({ error: error.message });
  }
});

exports.deleteVoluntario = ('/voluntario/:username', async(req, res) => {
  const username = req.params.username; 

  try {
    const { data, error } = await db
      .from('Voluntario')
      .delete()
      .eq('username', username)
      .select()

    if (error) {
    throw error;
    }
    res.json(data);
  } catch (error) {
      res.status(500).json({ error: error.message });
  }
})