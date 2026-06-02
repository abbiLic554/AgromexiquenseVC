package teknoeducativa.mx.agrovc.activities.cliente

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.activities.admin.LoginActivity

class UbicacionActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicacion)

        // MAPA
        val imgMapa = findViewById<ImageView>(R.id.imgMapa)

        imgMapa.setOnClickListener {
            val uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=mercado+municipal")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        // DRAWER
        drawerLayout = findViewById(R.id.drawerLayout)

        val btnMenu = findViewById<ImageView>(R.id.btnMenu)

        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        setupMenu()
    }

    private fun setupMenu() {

        findViewById<LinearLayout>(R.id.btnInicio).setOnClickListener {
            drawerLayout.closeDrawers()
            finish()
        }

        findViewById<LinearLayout>(R.id.btnProductores).setOnClickListener {
            startActivity(Intent(this, ProductoresActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnUbicacion).setOnClickListener {
            drawerLayout.closeDrawers()
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

        findViewById<LinearLayout>(R.id.btnCreditos).setOnClickListener {
            startActivity(Intent(this, CreditosActivity::class.java))
            drawerLayout.closeDrawers()
        }
    }
}