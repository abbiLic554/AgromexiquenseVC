package teknoeducativa.mx.agrovc.activities.cliente

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.activities.admin.LoginActivity
import teknoeducativa.mx.agrovc.adapters.ProductoAdapter
import teknoeducativa.mx.agrovc.models.Producto
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var recycler: RecyclerView

    private lateinit var buscar: EditText

    private lateinit var btnMenu: ImageView

    private lateinit var btnFiltro: ImageView

    private val db = FirebaseFirestore.getInstance()

    private val lista = mutableListOf<Producto>()

    private val filtrada = mutableListOf<Producto>()

    private lateinit var adapter: ProductoAdapter

    private lateinit var txtTituloInicio: TextView

    private lateinit var txtHistoria: TextView

    // FILTROS

    private lateinit var btnFrutas: LinearLayout

    private lateinit var btnVerduras: LinearLayout

    private lateinit var btnArtesanias: LinearLayout

    private lateinit var btnPiel: LinearLayout

    private lateinit var btnOtros: LinearLayout

    private lateinit var btnQuitar: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        drawerLayout =
            findViewById(R.id.drawerLayout)

        recycler =
            findViewById(R.id.recyclerProductos)

        buscar =
            findViewById(R.id.etBuscar)

        btnMenu =
            findViewById(R.id.btnMenu)

        btnFiltro =
            findViewById(R.id.btnFiltro)

        txtTituloInicio =
            findViewById(R.id.txtTituloHistoria)

        txtHistoria =
            findViewById(R.id.txtHistoria)

        // FILTROS

        btnFrutas =
            findViewById(R.id.btnFrutas)

        btnVerduras =
            findViewById(R.id.btnVerduras)

        btnArtesanias =
            findViewById(R.id.btnArtesanias)

        btnPiel =
            findViewById(R.id.btnPiel)

        btnOtros =
            findViewById(R.id.btnOtros)

        btnQuitar =
            findViewById(R.id.btnQuitarFiltros)

        recycler.layoutManager =
            GridLayoutManager(this, 2)

        adapter = ProductoAdapter(
            filtrada
        ) { producto ->

            val intent =
                Intent(
                    this,
                    DetalleProductoActivity::class.java
                )

            intent.putExtra(
                "nombre",
                producto.nombre
            )

            intent.putExtra(
                "descripcion",
                producto.descripcion
            )

            intent.putExtra(
                "imagen",
                producto.imagen
            )

            intent.putExtra(
                "tipo",
                producto.tipo
            )

            intent.putExtra(
                "id_productor",
                producto.id_productor
            )

            intent.putExtra(
                "calificacion_promedio",
                producto.calificacion_promedio
            )

            startActivity(intent)
        }

        recycler.adapter =
            adapter

        // MENU LATERAL

        btnMenu.setOnClickListener {

            drawerLayout.openDrawer(
                findViewById(R.id.menuLateral)
            )
        }

        // MENU FILTROS

        btnFiltro.setOnClickListener {

            drawerLayout.openDrawer(
                findViewById(R.id.menuFiltro)
            )
        }

        setupMenu()

        // BUSCADOR

        buscar.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    filtrarProductos(
                        s.toString()
                    )
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {}
            }
        )

        // FILTROS

        btnFrutas.setOnClickListener {

            filtrarTipo("Frutas")
        }

        btnVerduras.setOnClickListener {

            filtrarTipo("Verduras")
        }

        btnArtesanias.setOnClickListener {

            filtrarTipo("Artesanal")
        }

        btnPiel.setOnClickListener {

            filtrarTipo("Piel")
        }

        btnOtros.setOnClickListener {

            filtrada.clear()

            filtrada.addAll(

                lista.filter {

                    it.tipo != "Frutas" &&
                            it.tipo != "Verduras" &&
                            it.tipo != "Artesanal" &&
                            it.tipo != "Piel"
                }
            )

            adapter.notifyDataSetChanged()

            drawerLayout.closeDrawers()
        }

        btnQuitar.setOnClickListener {

            filtrada.clear()

            filtrada.addAll(lista)

            adapter.notifyDataSetChanged()

            drawerLayout.closeDrawers()
        }

        cargarInicio()

        cargarProductos()
    }

    private fun setupMenu() {

        findViewById<LinearLayout>(
            R.id.btnInicio
        ).setOnClickListener {

            drawerLayout.closeDrawers()
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
    }

    private fun cargarInicio() {

        db.collection("INICIO")
            .document("data")
            .get()

            .addOnSuccessListener { doc ->

                txtTituloInicio.text =
                    doc.getString("titulo") ?: ""

                txtHistoria.text =
                    doc.getString("descripcion") ?: ""
            }
    }

    private fun cargarProductos() {

        db.collection("PRODUCTOS")
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

                filtrada.clear()

                filtrada.addAll(lista)

                adapter.notifyDataSetChanged()
            }
    }

    private fun filtrarProductos(
        texto: String
    ) {

        val q =
            texto.lowercase()

        filtrada.clear()

        filtrada.addAll(

            lista.filter {

                it.nombre.lowercase().contains(q) ||

                        it.tipo.lowercase().contains(q)
            }
        )

        adapter.notifyDataSetChanged()
    }

    private fun filtrarTipo(
        tipo: String
    ) {

        filtrada.clear()

        filtrada.addAll(

            lista.filter {

                it.tipo.equals(
                    tipo,
                    true
                )
            }
        )
        adapter.notifyDataSetChanged()
        drawerLayout.closeDrawers()
    }
}