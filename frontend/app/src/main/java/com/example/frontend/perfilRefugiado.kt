package com.example.frontend

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import java.io.IOException

class perfilRefugiado : AppCompatActivity() {
    lateinit var imgPerfil : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_refugiado)

        val tvNome = findViewById<TextView>(R.id.tvNome)
        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        val tvIdiomas = findViewById<TextView>(R.id.tvIdiomas)
        val tvPais = findViewById<TextView>(R.id.tvPais)
        val tvTelefone = findViewById<TextView>(R.id.tvTelefone)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val btnDeslogin = findViewById<FloatingActionButton>(R.id.btLogOut)
        imgPerfil = findViewById<ShapeableImageView>(R.id.imgPerfilRefugiado)

        val gUser = application as GlobalUser
        val user = gUser.getGlobalRefugiado()

        btnDeslogin.setOnClickListener(){
            val gUser = application as GlobalUser
            gUser.setGlobalRefugiado(null)

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        imgPerfil.setOnClickListener(){
            seletorDeImagem()
        }

        tvNome.text = user?.nome
        tvUsername.text = "@" + user?.username
        tvIdiomas.text = user?.idioma
        tvPais.text = user?.paisOrigem

        if (user?.telefone != null)
            tvTelefone.text = user?.telefone
        else
            tvTelefone.text = "Nenhum telefone foi cadastrado"

        if (user?.email != null)
            tvEmail.text = user?.email
        else
            tvEmail.text = "Nenhum email foi cadastrado"
    }

    private fun seletorDeImagem(){
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        // pass the constant to compare it
        // with the returned requestCode

        // pass the constant to compare it
        // with the returned requestCode
        launchSomeActivity.launch(i)
    }

    var launchSomeActivity = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode
            == RESULT_OK
        ) {
            val data = result.data
            // do your operation from here....
            if (data != null
                && data.data != null
            ) {
                val selectedImageUri = data.data
                val selectedImageBitmap: Bitmap
                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                        this.contentResolver,
                        selectedImageUri
                    )

                    imgPerfil.clipToOutline = true
                    imgPerfil.setImageBitmap(
                        selectedImageBitmap
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}