const db = require('../db')

exports.getAvaliacoesVoluntario = ('/avaliacaoVoluntario/:username', async(req, res) => {
  const username = req.params.username
  try {
    const { data, error } = await db.from('AvaliacaoVoluntario').select('*').eq('usernameVoluntario', username);
    if (error) {
      throw error;
    }
    res.json(data);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

exports.getAvaliacaoPorRefugiado = ('/avaliacaoVoluntarioPorRef/:usernameRefugiado/:usernameVoluntario', async(req, res) => {
  const usernameRef = req.params.usernameRefugiado;
  const usernameVolun = req.params.usernameVoluntario;

  try {
    const { data, error } = await db.from('AvaliacaoVoluntario').select('*').eq('usernameRefugiado', usernameRef).eq('usernameVoluntario', usernameVolun);
    if (error) {
      throw error;
    }
    res.json(data);
    console.log(data)
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})

exports.getAvaliacaoVoluntario = ('/avaliacaoVoluntario/:role/:username', async(req, res) => {
    const role = req.params.role
    const username = req.params.username
    let column = ''

    if (role == 'voluntario')
        column = 'usernameVoluntario'
    else
        column = 'usernameRefugiado'

    try {
        const { data, error } = await db.from('AvaliacaoVoluntario').select('*').eq(column, username);
        if (error) {
        throw error;
        }
        res.json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

exports.putAvaliacaoVoluntario = ('/avaliacaoVoluntario/', async(req, res) => {
    const usernameRef = req.body.usernameRefugiado
    const usernameVolun = req.body.usernameVoluntario
    const like = req.body.like;
    const dislike = req.body.dislike;

    try {
        const { data, error } = await db
            .from('AvaliacaoVoluntario')
            .insert([
                { usernameRefugiado: usernameRef, usernameVoluntario: usernameVolun, like: like, dislike: dislike},
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

exports.postAvaliacaoVoluntario = ('/avaliacaoVoluntario/:id', async(req, res) => {
    const id = req.params.id;
    const like = req.body.like;
    const dislike = req.body.dislike;
    try {
        const { data, error } = await db
          .from('AvaliacaoVoluntario')
          .update({ like: like, dislike: dislike })
          .eq('id', id)
          .select()
    
        if (error) {
        throw error;
        }
        res.json(data);
      } catch (error) {
          res.status(500).json({ error: error.message });
      }
});

exports.deleteAvaliacaoVoluntario = ('/conavaliacaoVoluntarioexao/:usernameRefugiado/:usernameVoluntario', async(req, res) => {
    const usernameRef = req.params.usernameRefugiado;
    const usernameVolun = req.params.usernameVoluntario;

    try {
      const { data, error } = await db
        .from('AvaliacaoVoluntario')
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