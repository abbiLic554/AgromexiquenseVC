package teknoeducativa.mx.agrovc.activities.cliente

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import teknoeducativa.mx.agrovc.R

class DetalleProductoActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        val img = findViewById<ImageView>(R.id.ivProduct)
        val title = findViewById<TextView>(R.id.tvProductTitle)
        val category = findViewById<TextView>(R.id.tvCategory)
        val shortDesc = findViewById<TextView>(R.id.tvShortDescription)
        val longDesc = findViewById<TextView>(R.id.tvLongDescription)
        val rating = findViewById<TextView>(R.id.tvRating)

        val imgProd = findViewById<ImageView>(R.id.imgProductor)
        val nameProd = findViewById<TextView>(R.id.txtNombreProductor)
        val descProd = findViewById<TextView>(R.id.txtDescripcionProductor)
        val btnProd = findViewById<Button>(R.id.btnIrProductor)

        val nombre = intent.getStringExtra("nombre") ?: ""
        val descripcion = intent.getStringExtra("descripcion") ?: ""
        val imagen = intent.getStringExtra("imagen") ?: ""
        val tipo = intent.getStringExtra("tipo") ?: ""
        val idProductor = intent.getStringExtra("id_productor") ?: ""
        val calificacion = intent.getDoubleExtra("calificacion_promedio", 0.0)

        title.text = nombre
        category.text = tipo
        shortDesc.text = descripcion
        rating.text = "Calificación: $calificacion / 5"

        Glide.with(this).load(imagen).into(img)

        // PRODUCTOR
        if (idProductor.isNotEmpty()) {

            db.collection("PRODUCTORES")
                .document(idProductor)
                .get()
                .addOnSuccessListener { doc ->

                    val nombreProd = doc.getString("nombre") ?: ""
                    val descProdTxt = doc.getString("descripcion") ?: ""
                    val imagenProd = doc.getString("imagen") ?: ""

                    nameProd.text = nombreProd
                    descProd.text = descProdTxt

                    Glide.with(this)
                        .load(imagenProd)
                        .into(imgProd)

                    // BOTÓN IR PRODUCTOR
                    btnProd.setOnClickListener {
                        val i = Intent(this, DetalleProductorActivity::class.java)
                        i.putExtra("id", idProductor)
                        startActivity(i)
                    }
                }
        }
    }
}