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

class ProductorAdapterAdmin(

    private val lista: List<Productor>,
    private val onEditar: (Productor) -> Unit,
    private val onEliminar: (Productor) -> Unit

) : RecyclerView.Adapter<ProductorAdapterAdmin.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val img = v.findViewById<ImageView>(R.id.imgProductorItem)

        val nombre = v.findViewById<TextView>(R.id.txtNombre)

        val telefono = v.findViewById<TextView>(R.id.txtTelefono)

        val ubicacion = v.findViewById<TextView>(R.id.txtUbicacion)

        val btnEditar = v.findViewById<Button>(R.id.btnEditar)

        val btnEliminar = v.findViewById<Button>(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_productor_admin, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = lista[position]

        holder.nombre.text = item.nombre

        holder.telefono.text = item.telefono

        holder.ubicacion.text = item.ubicacion

        if (!item.imagen.isNullOrEmpty()) {

            Glide.with(holder.itemView.context)
                .load(item.imagen)
                .into(holder.img)
        }

        holder.btnEditar.setOnClickListener {

            onEditar(item)
        }

        holder.btnEliminar.setOnClickListener {

            onEliminar(item)
        }
    }

    override fun getItemCount(): Int {

        return lista.size
    }
}