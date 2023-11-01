const express = require('express')
const router = express.Router()

const ctrRef = require('./Controller/refugiadoController')
const ctrVolun = require('./Controller/voluntarioController')
const ctrCon = require('./Controller/conexaoController')
const crtAvLocal = require('./Controller/avaliacaoLocalController')
const crtAvVolun = require('./Controller/avaliacaoVoluntarioController')
const ctrProfPicture = require('./Controller/profilePictureController')

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
router.post('/conexao/:usernameRef/:usernameVolun', ctrCon.postConexao)
router.delete('/conexao/:usernameRef/:usernameVolun', ctrCon.deleteConexao)

router.get('/avaliacoesLocal', crtAvLocal.getAvaliacoesLocal)
router.get('/avaliacaoLocal/:local', crtAvLocal.getAvaliacaoLocal)
router.put('/avaliacaoLocal', crtAvLocal.putAvaliacaoLocal)
router.post('/avaliacaoLocal/:id', crtAvLocal.postAvaliacaoLocal)
router.delete('/avaliacaoLocal/:id', crtAvLocal.deleteAvaliacaoLocal)

router.get('/avaliacoesVoluntario/:username', crtAvVolun.getAvaliacoesVoluntario)
router.get('/avaliacaoVoluntario/:role/:username', crtAvVolun.getAvaliacaoVoluntario)
router.get('/avaliacaoVoluntarioPorRef/:usernameRefugiado/:usernameVoluntario', crtAvVolun.getAvaliacaoPorRefugiado)
router.put('/avaliacaoVoluntario', crtAvVolun.putAvaliacaoVoluntario)
router.post('/avaliacaoVoluntario/:id', crtAvVolun.postAvaliacaoVoluntario)
router.delete('/avaliacaoVoluntario/:usernameRefugiado/:usernameVoluntario', crtAvVolun.deleteAvaliacaoVoluntario)

router.put('/picture/:username', ctrProfPicture.postPicture)
router.get('/picture/:username', ctrProfPicture.getPicture)

module.exports = router