const { createClient } = require('@supabase/supabase-js');
require('dotenv').config();
const supabaseUrl = process.env.DATABASE_URL;
const supabaseKey = process.env.DATABASE_KEY;
const supabase = createClient(supabaseUrl, supabaseKey);

const express = require('express');
const app = express();
const port = 3000;

app.get('/api/data', async (req, res) => {
    try {
      const { data, error } = await supabase.from('Refugiado').select('*');
      if (error) {
        throw error;
      }
      res.json(data);
    } catch (error) {
      res.status(500).json({ error: error.message });
    }
  });
  
app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
