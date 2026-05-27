package teknoeducativa.mx.agrovc.activities.cliente

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import teknoeducativa.mx.agrovc.R

class InfoAgroActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var txtQueEs: TextView
    private lateinit var txtBeneficios: TextView
    private lateinit var imgQueEs: ImageView
    private lateinit var imgBeneficios: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_agro)

        txtQueEs = findViewById(R.id.txtQueEs)
        txtBeneficios = findViewById(R.id.txtBeneficios)
        imgQueEs = findViewById(R.id.imgQueEs)
        imgBeneficios = findViewById(R.id.imgBeneficios)

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