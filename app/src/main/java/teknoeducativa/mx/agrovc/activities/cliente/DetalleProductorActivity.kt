package teknoeducativa.mx.agrovc.activities.cliente

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.activities.admin.LoginActivity
import teknoeducativa.mx.agrovc.adapters.ProductoAdapter
import teknoeducativa.mx.agrovc.models.Producto

class DetalleProductorActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var recycler: RecyclerView

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var btnMenu: ImageView

    private val lista = mutableListOf<Producto>()

    private lateinit var adapter: ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detalle_productor)

        // MENU
        drawerLayout =
            findViewById(R.id.drawerLayout)

        btnMenu =
            findViewById(R.id.btnMenu)

        btnMenu.setOnClickListener {

            drawerLayout.openDrawer(
                findViewById(R.id.menuLateral)
            )
        }

        setupMenu()

        // VISTAS
        val img =
            findViewById<ImageView>(R.id.imgPerfil)

        val nombre =
            findViewById<TextView>(R.id.txtNombre)

        val ubicacion =
            findViewById<TextView>(R.id.txtUbicacion)

        val telefono =
            findViewById<TextView>(R.id.txtTelefono)

        val descripcion =
            findViewById<TextView>(R.id.txtDescripcion)

        recycler =
            findViewById(R.id.recyclerProductosProductor)

        recycler.layoutManager =
            GridLayoutManager(this, 2)

        adapter =
            ProductoAdapter(lista) { }

        recycler.adapter =
            adapter

        val id =
            intent.getStringExtra("id") ?: ""

        if (id.isEmpty()) {

            Toast.makeText(
                this,
                "Error productor vacío",
                Toast.LENGTH_SHORT
            ).show()

            finish()

            return
        }

        // PRODUCTOR
        db.collection("PRODUCTORES")
            .document(id)
            .get()

            .addOnSuccessListener { doc ->

                nombre.text =
                    doc.getString("nombre") ?: ""

                ubicacion.text =
                    doc.getString("ubicacion") ?: ""

                telefono.text =
                    doc.getString("telefono") ?: ""

                descripcion.text =
                    doc.getString("descripcion") ?: ""

                Glide.with(this)
                    .load(doc.getString("imagen"))
                    .into(img)
            }

        // PRODUCTOS DEL PRODUCTOR
        db.collection("PRODUCTOS")
            .whereEqualTo("id_productor", id)
            .get()

            .addOnSuccessListener { result ->

                lista.clear()

                for (doc in result) {

                    lista.add(
                        doc.toObject(
                            Producto::class.java
                        )
                    )
                }

                adapter.notifyDataSetChanged()
            }
    }

    private fun setupMenu() {

        findViewById<LinearLayout>(
            R.id.btnInicio
        ).setOnClickListener {

            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        }

        findViewById<LinearLayout>(
            R.id.btnProductores
        ).setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ProductoresActivity::class.java
                )
            )
        }

        findViewById<LinearLayout>(
            R.id.btnUbicacion
        ).setOnClickListener {

            startActivity(
                Intent(
                    this,
                    UbicacionActivity::class.java
                )
            )
        }

        findViewById<LinearLayout>(
            R.id.btnEventos
        ).setOnClickListener {

            startActivity(
                Intent(
                    this,
                    EventosActivity::class.java
                )
            )
        }

        findViewById<LinearLayout>(
            R.id.btnInfo
        ).setOnClickListener {

            startActivity(
                Intent(
                    this,
                    InfoAgroActivity::class.java
                )
            )
        }

        findViewById<LinearLayout>(
            R.id.btnComite
        ).setOnClickListener {

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )
        }

        findViewById<LinearLayout>(
            R.id.btnCreditos
        ).setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CreditosActivity::class.java
                )
            )
        }
    }
}