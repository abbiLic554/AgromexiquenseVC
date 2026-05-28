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
import teknoeducativa.mx.agrovc.adapters.ProductorAdapter
import teknoeducativa.mx.agrovc.models.Productor
import teknoeducativa.mx.agrovc.activities.admin.LoginActivity

class ProductoresActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var recycler: RecyclerView

    private val lista = mutableListOf<Productor>()
    private lateinit var adapter: ProductorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productores)

        // RECYCLER
        recycler = findViewById(R.id.recyclerProductores)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = ProductorAdapter(lista) { productor ->

            val intent = Intent(this, DetalleProductorActivity::class.java)
            intent.putExtra("id", productor.id)
            startActivity(intent)
        }

        recycler.adapter = adapter

        // DRAWER
        drawerLayout = findViewById(R.id.drawerLayoutproductores)

        val btnMenu = findViewById<ImageView>(R.id.btnMenu)

        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        setupMenu()

        cargarProductores()
    }

    private fun setupMenu() {

        findViewById<LinearLayout>(R.id.btnInicio).setOnClickListener {
            drawerLayout.closeDrawers()
            finish()
        }

        findViewById<LinearLayout>(R.id.btnProductores).setOnClickListener {
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.btnUbicacion).setOnClickListener {
            startActivity(Intent(this, UbicacionActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnEventos).setOnClickListener {
            startActivity(Intent(this, EventosActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnInfo).setOnClickListener {
            startActivity(Intent(this, InfoAgroActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnComite).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
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