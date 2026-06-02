package teknoeducativa.mx.agrovc.activities.cliente

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.adapters.EventoAdapter
import teknoeducativa.mx.agrovc.models.Evento
import teknoeducativa.mx.agrovc.activities.admin.LoginActivity

class EventosActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var drawerLayout: DrawerLayout
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

        drawerLayout = findViewById(R.id.drawerLayout)

        val btnMenu = findViewById<ImageView>(R.id.btnMenu)

        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        setupMenu()
        cargarEventos()
    }

    private fun setupMenu() {

        findViewById<LinearLayout>(R.id.btnInicio).setOnClickListener {
            drawerLayout.closeDrawers()
            finish()
        }

        findViewById<LinearLayout>(R.id.btnProductores).setOnClickListener {
            startActivity(Intent(this, ProductoresActivity::class.java))
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.btnUbicacion).setOnClickListener {
            startActivity(Intent(this, UbicacionActivity::class.java))
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.btnEventos).setOnClickListener {
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.btnInfo).setOnClickListener {
            startActivity(Intent(this, InfoAgroActivity::class.java))
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.btnComite).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.btnCreditos).setOnClickListener {
            startActivity(Intent(this, CreditosActivity::class.java))
            drawerLayout.closeDrawers()
        }
    }

    private fun cargarEventos() {

        db.collection("EVENTOS")
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