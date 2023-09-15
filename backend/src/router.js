const express = require('express')
const ctrRef = require('./Controller/refugiadoController')
const ctrVolun = require('./Controller/voluntarioController')
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

module.exports = router