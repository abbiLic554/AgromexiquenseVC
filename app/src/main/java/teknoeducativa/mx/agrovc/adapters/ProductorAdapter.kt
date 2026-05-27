package teknoeducativa.mx.agrovc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.models.Productor

class ProductorAdapter(
    private val lista: List<Productor>,
    private val onClick: (Productor) -> Unit
) : RecyclerView.Adapter<ProductorAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nombre = view.findViewById<TextView>(R.id.txtNombre)
        val ubicacion = view.findViewById<TextView>(R.id.txtUbicacion)
        val telefono = view.findViewById<TextView>(R.id.txtTelefono)
        val img = view.findViewById<ImageView>(R.id.imgProductor)

        val btnIr = view.findViewById<Button>(R.id.btnIrProductor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_productor, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = lista[position]

        holder.nombre.text = item.nombre
        holder.ubicacion.text = item.ubicacion
        holder.telefono.text = item.telefono

        if (!item.imagen.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(item.imagen)
                .into(holder.img)
        }

        // 👇 CLICK EN BOTÓN "IR"
        holder.btnIr.setOnClickListener {
            onClick(item)
        }

        // opcional: click en toda la card
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }
}