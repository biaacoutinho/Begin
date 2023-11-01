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
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.frontend.API.RetrofitClient
import com.example.frontend.API.models.ProfilePicture
import com.example.frontend.API.models.Refugiado
import com.example.frontend.API.services.ProfilePictureService
import com.example.frontend.API.services.RefugiadoService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class perfilRefugiado : AppCompatActivity() {
    lateinit var imgPerfil : ImageView
    //var user: Refugiado
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_refugiado)

        val tvNome = findViewById<TextView>(R.id.tvNome)
        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        val tvIdiomas = findViewById<TextView>(R.id.tvIdiomas)
        val tvPais = findViewById<TextView>(R.id.tvPais)
        val tvTelefone = findViewById<TextView>(R.id.tvTelefone)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val btnDeslogin = findViewById<TextView>(R.id.btLogOut)
        val btnVoltar = findViewById<TextView>(R.id.btnVoltar)
        val btnEditRef = findViewById<FloatingActionButton>(R.id.btnEditarRefugiado)
        imgPerfil = findViewById<ShapeableImageView>(R.id.imgPerfilRefugiado)

        var ondeVeio = intent.getStringExtra("ondeVeio")

        if(ondeVeio == "solicitacao" || ondeVeio == "conexoes")
        {
            btnDeslogin.visibility = View.INVISIBLE
            btnDeslogin.isClickable = false
            btnEditRef.visibility = View.INVISIBLE
            btnEditRef.isClickable = false
            val user: Refugiado = intent.getSerializableExtra("refugiado") as Refugiado
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

            btnVoltar.setOnClickListener{
                if (ondeVeio == "solicitacao")
                    startActivity(Intent(this, Solicitacoes::class.java))
                else
                    startActivity(Intent(this, Conexoes::class.java))

            }
            atualizarImagem(user.username)
        }
        else{
            val gUser = application as GlobalUser
            val user = gUser.getGlobalRefugiado()!!

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

            atualizarImagem(user.username)
        }


    }

    private fun seletorDeImagem(){
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        launchSomeActivity.launch(i)
    }

    var launchSomeActivity = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode
            == RESULT_OK
        ) {
            val data = result.data
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

                    val file = bitmapToBase64(selectedImageBitmap)
                    uploadImageToAPI(file!!)
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

    // Função para converter uma imagem em Base64
    fun bitmapToBase64(bitmap: Bitmap): String {
        // Comprima o Bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 76, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        // Converta em Base64
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }



    // Função para enviar a imagem para a API
    fun uploadImageToAPI(base64Image: String) {
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(ProfilePictureService::class.java)
        Log.d("aaa", base64Image)
        val requestBody = ProfilePicture(base64Image);
        val callback: Call<ResponseBody> = service.uploadPicture("Rajah", requestBody)

        callback.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response?.isSuccessful == true) {
                    // A imagem foi enviada com sucesso
                    Toast.makeText(
                        this@perfilRefugiado,
                        "Imagem enviada com sucesso",
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

    fun atualizarImagem(username: String){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(ProfilePictureService::class.java)
        val callback: Call<ResponseBody> = service.getPicture(username)

        callback.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response?.isSuccessful == true) {
                    val responseBody = response.body()?.string()
                    val jsonResponse = JSONObject(responseBody)
                    val fileUrl = jsonResponse.optString("url")
                    Log.d("link", fileUrl)
                    Picasso.get().load(fileUrl).into(imgPerfil)
                    Toast.makeText(
                        this@perfilRefugiado,
                        "Imagem enviada com sucesso",
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