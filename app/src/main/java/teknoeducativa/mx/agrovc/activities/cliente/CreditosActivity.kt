package teknoeducativa.mx.agrovc.activities.cliente

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.activities.admin.LoginActivity

class CreditosActivity : AppCompatActivity() {

    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creditos)

        drawer = findViewById(R.id.drawerLayout)

        findViewById<View>(R.id.btnMenu).setOnClickListener {
            drawer.openDrawer(findViewById(R.id.menuLateral))
        }

        setupMenu()
        setupLinks()
    }

    private fun setupMenu() {

        findViewById<View>(R.id.btnInicio).setOnClickListener {
            drawer.closeDrawers()
            startActivity(Intent(this, MainActivity::class.java))
        }

        findViewById<View>(R.id.btnProductores).setOnClickListener {
            drawer.closeDrawers()
            startActivity(Intent(this, ProductoresActivity::class.java))
        }

        findViewById<View>(R.id.btnUbicacion).setOnClickListener {
            drawer.closeDrawers()
            startActivity(Intent(this, UbicacionActivity::class.java))
        }

        findViewById<View>(R.id.btnEventos).setOnClickListener {
            drawer.closeDrawers()
            startActivity(Intent(this, EventosActivity::class.java))
        }

        findViewById<View>(R.id.btnInfo).setOnClickListener {
            drawer.closeDrawers()
            startActivity(Intent(this, InfoAgroActivity::class.java))
        }

        findViewById<View>(R.id.btnComite).setOnClickListener {
            drawer.closeDrawers()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        findViewById<View>(R.id.btnCreditos).setOnClickListener {
            drawer.closeDrawers()
            startActivity(Intent(this, CreditosActivity::class.java))
        }
    }

    private fun setupLinks() {

        findViewById<View>(R.id.txtAbigail).setOnClickListener {
            openLink("https://instagram.com/abbi_lic")
        }

        findViewById<View>(R.id.txtPaulina).setOnClickListener {
            openLink("https://www.instagram.com/santos___pau?igsh=d2J4MjFnNnpoZ2h1")
        }

        findViewById<View>(R.id.txtBrayan).setOnClickListener {
            openLink("https://www.facebook.com/brayan.portillo.7982?mibextid=ZbWKwL")
        }

        findViewById<View>(R.id.txtJonatan).setOnClickListener {
            openLink("https://www.facebook.com/jonatan.gonzalez.418655?mibextid=ZbWKwL")
        }

        findViewById<View>(R.id.txtMarcos).setOnClickListener {
            openLink("https://instagram.com/monroy.marc")
        }

        findViewById<View>(R.id.txtDavid).setOnClickListener {
            openLink("https://www.instagram.com/david0_7._?igsh=dzNwY3dhemxoanJr")
        }

        findViewById<View>(R.id.txtXimena1).setOnClickListener {
            openLink("https://instagram.com/i_ww_xiime")
        }

        findViewById<View>(R.id.txtXimena2).setOnClickListener {
            openLink("https://www.instagram.com/ximenatapia485?igsh=cmJiZDJxZXRwcTFm")
        }

        findViewById<View>(R.id.txtAldo).setOnClickListener {
            openLink("https://www.instagram.com/aldo_portillo_?igsh=MXJ5d285b21jamZ5ag==")
        }

        findViewById<View>(R.id.txtFatima).setOnClickListener {
            openLink("https://instagram.com/fati_do_sa19")
        }
    }

    private fun openLink(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}