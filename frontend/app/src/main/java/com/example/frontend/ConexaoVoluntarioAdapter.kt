import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.API.RetrofitClient
import com.example.frontend.API.models.Refugiado
import com.example.frontend.API.services.ProfilePictureService
import com.example.frontend.API.services.RefugiadoService
import com.example.frontend.ConexaoAdapter
import com.example.frontend.ConexaoData
import com.example.frontend.R
import com.example.frontend.SolicitacoesData
import com.example.frontend.perfilRefugiado
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ConexaoVoluntarioAdapter(var mList: List<SolicitacoesData>, var context: Context) : RecyclerView.Adapter<ConexaoVoluntarioAdapter.ConexoesViewHolder>() {

    inner class ConexoesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPerfil: ImageView = itemView.findViewById<ShapeableImageView>(R.id.imgPerfil)
        val nomeUsuario: TextView = itemView.findViewById(R.id.nomeRefugiado)
        val user: TextView = itemView.findViewById(R.id.userRefugiado)
    }

    fun setFilteredList(mList: List<SolicitacoesData>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConexoesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_card_conexao, parent, false)
        Log.d("sera", "foi??????")
        return ConexoesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConexoesViewHolder, position: Int) {
        holder.nomeUsuario.text = mList[position].nomeRefugiado
        holder.user.text = "@" + mList[position].userRefugiado
        Log.d("aaasasaads", mList[position].nomeRefugiado)
        getProfileImage(mList[position].userRefugiado, holder.imgPerfil)

        holder.itemView.setOnClickListener {
            val userRef = mList[position].userRefugiado
            val intent = Intent(context, perfilRefugiado::class.java)
            var refugiado = Refugiado("", "", "", "", "", "", "")
            intent.putExtra("ondeVeio", "conexoes")
            getRefugiado(userRef, refugiado, intent)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun getProfileImage(user: String, imgPerfil: ImageView){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(ProfilePictureService::class.java)
        val callback: Call<ResponseBody> = service.getPicture(user)

        callback.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response?.isSuccessful == true) {
                    val responseBody = response.body()?.string()
                    val jsonResponse = JSONObject(responseBody)
                    val fileUrl = jsonResponse.optString("url")
                    Picasso.get().load(fileUrl).into(imgPerfil)
                    //val drawable = ContextCompat.getDrawable(context, R.drawable.name_icon)
                    //imgPerfil.setImageDrawable(drawable)
                    //Log.d("atualizou", "atualzou")
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

    fun getRefugiado(ref : String, refugiado: Refugiado, intent: Intent){
        val retrofitClient = RetrofitClient.getRetrofit()
        val refugiadoService = retrofitClient.create(RefugiadoService::class.java)
        val refCallback = refugiadoService.getRefugiado(ref)

        refCallback.enqueue(object : retrofit2.Callback<List<Refugiado>> {
            override fun onResponse(
                call: Call<List<Refugiado>>?,
                response: Response<List<Refugiado>>?
            ) {
                val refugiadoList = response?.body()
                if (refugiadoList != null) {
                    if (response!!.isSuccessful) {
                        refugiado.nome = refugiadoList[0].nome
                        refugiado.username = refugiadoList[0].username
                        refugiado.senha = refugiadoList[0].senha
                        refugiado.idioma = refugiadoList[0].idioma
                        refugiado.paisOrigem = refugiadoList[0].paisOrigem
                        refugiado.telefone = refugiadoList[0].telefone
                        refugiado.email = refugiadoList[0].email
                        intent.putExtra("refugiado", refugiado)
                        context.startActivity(intent)
                    } else {
                        val errorMessage = response?.errorBody().toString()
                    }
                }
            }

            override fun onFailure(call: Call<List<Refugiado>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}