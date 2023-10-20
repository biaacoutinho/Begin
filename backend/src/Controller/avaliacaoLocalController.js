const db = require('../db')

exports.getAvaliacoesLocal = ('/avaliacoesLocal', async(req, res) => {
    try {
      const { data, error } = await db.from('AvaliacaoLocal').select('*');
      if (error) {
        throw error;
      }
      res.json(data);
    } catch (error) {
      res.status(500).json({ error: error.message });
    }
  });

exports.getAvaliacaoLocal = ('/avaliacaoLocal/:local', async(req, res) => {
    const local = req.params.local;

    try {
        const { data, error } = await db.from('AvaliacaoLocal').select('*').eq('local', local);
        if (error) {
        throw error;
        }
        res.json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
})

exports.getAvaliacaoByUser = ('/avaliacaoLocal/:username', async(req, res) => {
    const username = req.params.username;

    try {
        const { data, error } = await db.from('AvaliacaoLocal').select('*').eq('username', username);
        if (error) {
        throw error;
        }
        res.json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
})

exports.putAvaliacaoLocal = ('/avaliacaoLocal', async(req, res) => {
    const username = req.body.username;
    const local = req.body.local;
    const avaliacao = req.body.avaliacao;
  
    try {
      const { data, error } = await db
        .from('AvaliacaoLocal')
        .insert([
          { username: username, local: local, avaliacao: avaliacao },
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

exports.postAvaliacaoLocal = ('/avaliacaoLocal/:id', async(req, res) => {
    const id = req.params.id;
    const avaliacao = req.body.avaliacaoLocal;
  
    try {
      const { data, error } = await db
        .from('AvaliacaoLocal')
        .update({ avaliacao: avaliacao })
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

exports.deleteAvaliacaoLocal = ('/avaliacaoLocal/:id', async(req, res) => {
    const id = req.params.id; 
  
    try {
      const { data, error } = await db
        .from('AvaliacaoLocal')
        .delete()
        .eq('id', id)
        .select()
  
      if (error) {
      throw error;
      }
      res.json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
  })