package com.example.frontend

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.frontend.API.RetrofitClient
import com.example.frontend.API.models.Refugiado
import com.example.frontend.API.services.ProfilePictureService
import com.example.frontend.API.services.RefugiadoService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class perfilRefugiado : AppCompatActivity() {
    lateinit var imgPerfil : ImageView
    lateinit var user: Refugiado

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
        val btnEditRef = findViewById<FloatingActionButton>(R.id.btnEditarRefugiado)
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

        btnEditRef.setOnClickListener(){
            startActivity(Intent(this, EditarPerfilRefugiado::class.java))
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

                    val file = bitmapToFile(this, selectedImageBitmap)
                    salvarImagem(file!!)
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

    fun bitmapToFile(context: Context, bitmap: Bitmap): File? {
        Log.d("aaaa3", "foi3")

        val file = File(context.cacheDir, "Rajah" + ".jpg") // Use cacheDir ou outro diretório adequado

        Log.d("aaaa4", "foi4")

        try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream) // Escolha o formato e qualidade desejados
            Log.d("aaaa5", "foi5")

            stream.flush()
            stream.close()
            return file
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    fun salvarImagem(file: File) {
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(ProfilePictureService::class.java)
        Log.d("aaaa1", "foi1")
        val callback: Call<ResponseBody> = service.uploadPicture("Rajah", file)
        Log.d("aaaa2", "foi2")
        Log.d("arquivo", file.extension)
        callback!!.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response?.isSuccessful == true) {
                    Toast.makeText(
                        this@perfilRefugiado,
                        "Cadastro feito com sucesso",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    // Lidar com a resposta sem sucesso aqui
                    val errorBody = response?.errorBody()?.string()
                    Log.d("erro", "Erro na resposta: $errorBody")
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                val messageProblem: String = t?.message.toString()
                Log.d("erro", messageProblem)
            }
        })
    }
}