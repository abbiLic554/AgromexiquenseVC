package teknoeducativa.mx.agrovc.adapters

import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.models.Producto

class ProductoAdapter(
    private val lista: MutableList<Producto>,
    private val onClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img = view.findViewById<ImageView>(R.id.imgProducto)
        val nombre = view.findViewById<TextView>(R.id.txtNombre)
        val rating = view.findViewById<RatingBar>(R.id.ratingProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = lista[position]

        holder.nombre.text = item.nombre

        holder.rating.setOnRatingBarChangeListener(null)
        holder.rating.rating = item.calificacion_promedio.toFloat()

        holder.rating.setOnRatingBarChangeListener { _, valor, fromUser ->

            if (!fromUser) return@setOnRatingBarChangeListener

            val context = holder.itemView.context

            val deviceId = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )

            val db = FirebaseFirestore.getInstance()

            val productoRef = db.collection("PRODUCTOS")
                .document(item.id_producto)

            val califRef = productoRef
                .collection("CALIFICACIONES")
                .document(deviceId)

            // 1. Guardar calificación del usuario
            califRef.set(mapOf("valor" to valor.toDouble()))

            // 2. Actualización rápida del promedio (SIN leer toda la colección)
            productoRef.get().addOnSuccessListener { doc ->

                val promedioActual = doc.getDouble("calificacion_promedio") ?: 0.0
                val totalActual = doc.getLong("total_calificaciones") ?: 0L

                val nuevoTotal = totalActual + 1
                val nuevoPromedio =
                    ((promedioActual * totalActual) + valor) / nuevoTotal

                productoRef.update(
                    mapOf(
                        "calificacion_promedio" to nuevoPromedio,
                        "total_calificaciones" to nuevoTotal
                    )
                )
            }
        }

        if (item.imagen.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(item.imagen)
                .into(holder.img)
        }

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    fun actualizarLista(nuevaLista: List<Producto>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}