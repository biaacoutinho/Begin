const express = require('express')
const ctrRef = require('./Controller/refugiadoController')
const ctrVolun = require('./Controller/voluntarioController')
const ctrCon = require('./Controller/conexaoController')
const crtAv = require('./Controller/avaliacaoController')
const router = express.Router()

router.get('/refugiados', ctrRef.getRefugiados)
router.get('/refugiado/:username', ctrRef.getRefugiado)
router.put('/refugiado', ctrRef.putRefugiado)
router.post('/refugiado/:username', ctrRef.postRefugiado)
router.delete('/refugiado/:username', ctrRef.deleteRefugiado)

router.get('/voluntarios', ctrVolun.getVoluntarios)
router.get('/voluntario/:username', ctrVolun.getVoluntario)
router.put('/voluntario', ctrVolun.putVoluntario)
router.post('/voluntario/:username', ctrVolun.postVoluntario)
router.delete('/voluntario/:username', ctrVolun.deleteVoluntario)

router.get('/conexoes', ctrCon.getConexoes)
router.get('/conexao/:role/:username', ctrCon.getConexao)
router.put('/conexao', ctrCon.putConexao)
router.delete('/conexao/:usernameRef/:usernameVolun', ctrCon.deleteConexao)

router.get('/avaliacoes', crtAv.getAvaliacoes)
router.get('/avaliacao/:local', crtAv.getAvaliacao)
router.put('/avaliacao', crtAv.putAvaliacao)
router.post('/avaliacao/:id', crtAv.postAvaliacao)
router.delete('/avaliacao/:id', crtAv.deleteAvaliacao)

module.exports = router