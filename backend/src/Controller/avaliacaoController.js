const db = require('../db')

exports.getAvaliacoes = ('/avaliacoes', async(req, res) => {
    try {
      const { data, error } = await db.from('Avaliacao').select('*');
      if (error) {
        throw error;
      }
      res.json(data);
    } catch (error) {
      res.status(500).json({ error: error.message });
    }
  });

exports.getAvaliacao = ('/avaliacao/:local', async(req, res) => {
    const local = req.params.local;

    try {
        const { data, error } = await db.from('Avaliacao').select('*').eq('local', local);
        if (error) {
        throw error;
        }
        res.json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
})

exports.getAvaliacaoByUser = ('/avaliacao/:username', async(req, res) => {
    const username = req.params.username;

    try {
        const { data, error } = await db.from('Avaliacao').select('*').eq('username', username);
        if (error) {
        throw error;
        }
        res.json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
})

exports.putAvaliacao = ('/avaliacao', async(req, res) => {
    const username = req.body.username;
    const local = req.body.local;
    const avaliacao = req.body.avaliacao;
  
    try {
      const { data, error } = await db
        .from('Avaliacao')
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

exports.postAvaliacao = ('/avaliacao/:id', async(req, res) => {
    const id = req.params.id;
    const avaliacao = req.body.avaliacao;
  
    try {
      const { data, error } = await db
        .from('Avaliacao')
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

exports.deleteAvaliacao = ('/avaliacao/:id', async(req, res) => {
    const id = req.params.id; 
  
    try {
      const { data, error } = await db
        .from('Avaliacao')
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