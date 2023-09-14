const express = require('express');
const { createClient } = require('@supabase/supabase-js');
const morgan = require('morgan');
const bodyParser = require('body-parser');
require('dotenv').config();

const app = express();

app.use(morgan('combined'));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const supabaseClient = createClient(process.env.DATABASE_URL, process.env.DATABASE_KEY);

app.get('/', async (req, res) => {
    try {
      const { data, error } = await supabaseClient
        .from('Voluntario')
        .select()
      
      if (error) {
        throw error;
      }

      res.send(data);
    } catch (err) {
      console.error('Supabase error:', err.message);
      res.status(500).send('Internal Server Error');
    }
  });

app.listen(3000, () => {
    console.log(`> Ready on http://localhost:3000`);
});