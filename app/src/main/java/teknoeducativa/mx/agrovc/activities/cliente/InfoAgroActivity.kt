package teknoeducativa.mx.agrovc.activities.cliente

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.activities.admin.LoginActivity

class InfoAgroActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var txtQueEs: TextView
    private lateinit var txtBeneficios: TextView
    private lateinit var imgQueEs: ImageView
    private lateinit var imgBeneficios: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_agro)

        // TEXTOS
        txtQueEs = findViewById(R.id.txtQueEs)
        txtBeneficios = findViewById(R.id.txtBeneficios)
        imgQueEs = findViewById(R.id.imgQueEs)
        imgBeneficios = findViewById(R.id.imgBeneficios)

        // DRAWER
        drawerLayout = findViewById(R.id.drawerLayout)

        val btnMenu = findViewById<ImageView>(R.id.btnMenu)

        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // MENÚ LATERAL (TODO DENTRO DE onCreate)
        findViewById<LinearLayout>(R.id.btnInicio).setOnClickListener {
            drawerLayout.closeDrawers()
            finish()
        }

        findViewById<LinearLayout>(R.id.btnProductores).setOnClickListener {
            startActivity(Intent(this, ProductoresActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnUbicacion).setOnClickListener {
            startActivity(Intent(this, UbicacionActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnEventos).setOnClickListener {
            startActivity(Intent(this, EventosActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnInfo).setOnClickListener {
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.btnComite).setOnClickListener {
            startActivity(Intent(this, LoginActivity ::class.java))
        }

        // CARGA FIRESTORE
        cargarHistoria()
    }

    private fun cargarHistoria() {

        db.collection("HISTORIA")
            .document("data")
            .get()
            .addOnSuccessListener { doc ->

                if (doc.exists()) {

                    txtQueEs.text = doc.getString("descripcion_1") ?: ""
                    txtBeneficios.text = doc.getString("descripcion_2") ?: ""

                    Glide.with(this)
                        .load(doc.getString("imagen_1"))
                        .into(imgQueEs)

                    Glide.with(this)
                        .load(doc.getString("imagen_2"))
                        .into(imgBeneficios)

                } else {
                    Toast.makeText(this, "No existe HISTORIA/data", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }
}