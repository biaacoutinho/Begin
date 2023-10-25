const express = require('express');
const app = express();
const routes = require('./src/router')
const port = 3000;

app.use(express.urlencoded({extended: true}))
app.use(express.json())
app.use('/', routes)

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
