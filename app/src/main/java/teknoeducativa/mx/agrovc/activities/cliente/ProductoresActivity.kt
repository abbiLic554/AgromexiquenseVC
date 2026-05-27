package teknoeducativa.mx.agrovc.activities.cliente

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.adapters.ProductorAdapter
import teknoeducativa.mx.agrovc.models.Productor

class ProductoresActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var recycler: RecyclerView
    private val lista = mutableListOf<Productor>()
    private lateinit var adapter: ProductorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productores)

        recycler = findViewById(R.id.recyclerProductores)

        recycler.layoutManager = LinearLayoutManager(this)

        adapter = ProductorAdapter(lista) { productor ->

            // 🔥 CLICK ABRE PERFIL
            val intent = Intent(this, DetalleProductorActivity::class.java)

            intent.putExtra("id", productor.id)

            startActivity(intent)
        }

        recycler.adapter = adapter

        cargarProductores()
    }

    private fun cargarProductores() {

        db.collection("PRODUCTORES")
            .get()
            .addOnSuccessListener { result ->

                lista.clear()

                for (doc in result) {
                    val prod = doc.toObject(Productor::class.java)
                    prod.id = doc.id
                    lista.add(prod)
                }

                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error cargando productores", Toast.LENGTH_SHORT).show()
            }
    }
}