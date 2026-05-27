package teknoeducativa.mx.agrovc.activities.cliente

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import teknoeducativa.mx.agrovc.R

class UbicacionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicacion)

        val imgMapa = findViewById<ImageView>(R.id.imgMapa)

        // CLICK EN MAPA → ABRE GOOGLE MAPS
        imgMapa.setOnClickListener {

            val uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=mercado+municipal")

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}