const express = require('express')
const ctrRef = require('./Controller/refugiadoController')
const router = express.Router()

router.get('/refugiados', ctrRef.getRefugiados)
router.get('/refugiado/:username', ctrRef.getRefugiado)
router.put('/refugiado', ctrRef.putRefugiado)
router.post('/refugiado/:username', ctrRef.postRefugiado)
router.delete('/refugiado/:username', ctrRef.deleteRefugiado)

module.exports = router