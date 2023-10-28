const db = require('../db')

exports.getConexoes = ('/conexoes', async(req, res) => {
  try {
    const { data, error } = await db.from('ConexaoRefugiadoVoluntario').select('*');
    if (error) {
      throw error;
    }
    res.json(data);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

exports.getConexao = ('/conexao/:role/:username', async(req, res) => {
    const role = req.params.role
    const username = req.params.username
    let column = ''

    if (role == 'voluntario')
        column = 'usernameVoluntario'
    else
        column = 'usernameRefugiado'

    try {
        const { data, error } = await db.from('ConexaoRefugiadoVoluntario').select('*').eq(column, username);
        if (error) {
        throw error;
        }
        console.log(data)
        res.json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

exports.putConexao = ('/conexao', async(req, res) => {
    const usernameRef = req.body.usernameRefugiado
    const usernameVolun = req.body.usernameVoluntario
    const pendente = req.body.pendente

    try {
        const { data, error } = await db
            .from('ConexaoRefugiadoVoluntario')
            .insert([
                { usernameRefugiado: usernameRef, usernameVoluntario: usernameVolun, pendente: pendente },
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

exports.postConexao = ('/conexao/:usernameRef/:usernameVolun', async(req, res) => {
  const usernameRef = req.params.usernameRef;
  const usernameVolun = req.params.usernameVolun;
  const pendente = req.body.pendente

  try {
    const { data, error } = await db
      .from('Refugiado')
      .update({ usernameRefugiado: usernameRef, usernameVoluntario: usernameVolun, pendente: pendente })
      .eq('usernameRefugiado', usernameRef)
      .eq('usernameVoluntario', usernameVolun)
      .select()

    if (error) {
    throw error;
    }
    res.json(data);
  } catch (error) {
      res.status(400).json({ error: error.message });
  }
});

exports.deleteConexao = ('/conexao/:usernameRef/:usernameVolun', async(req, res) => {
    const usernameRef = req.params.usernameRef;
    const usernameVolun = req.params.usernameVolun;

    console.log(usernameRef, usernameVolun)

    try {
      const { data, error } = await db
        .from('ConexaoRefugiadoVoluntario')
        .delete()
        .eq('usernameRefugiado', usernameRef)
        .eq('usernameVoluntario', usernameVolun)
        .select()
  
      if (error) {
      throw error;
      }
      res.json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
  })