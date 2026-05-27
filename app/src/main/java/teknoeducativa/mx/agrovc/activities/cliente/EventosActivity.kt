package teknoeducativa.mx.agrovc.activities.cliente

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.adapters.EventoAdapter
import teknoeducativa.mx.agrovc.models.Evento

class EventosActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var recycler: RecyclerView
    private val lista = mutableListOf<Evento>()
    private lateinit var adapter: EventoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        recycler = findViewById(R.id.recyclerEventos)

        recycler.layoutManager = LinearLayoutManager(this)

        adapter = EventoAdapter(lista)
        recycler.adapter = adapter

        cargarEventos()
    }

    private fun cargarEventos() {

        db.collection("EVENTOS") // 🔥 OJO: debe llamarse EXACTO igual en Firebase
            .get()
            .addOnSuccessListener { result ->

                lista.clear()

                for (doc in result) {

                    val evento = doc.toObject(Evento::class.java)

                    evento.id_evento = doc.id

                    lista.add(evento)
                }

                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {

                Toast.makeText(this, "Error cargando eventos", Toast.LENGTH_SHORT).show()
            }
    }
}