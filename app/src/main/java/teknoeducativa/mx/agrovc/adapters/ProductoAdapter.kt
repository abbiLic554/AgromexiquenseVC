package teknoeducativa.mx.agrovc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.models.Producto

class ProductoAdapter(

    private val lista: MutableList<Producto>,

    private val onClick: (Producto) -> Unit

) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val img =
            view.findViewById<ImageView>(
                R.id.imgProducto
            )

        val nombre =
            view.findViewById<TextView>(
                R.id.txtNombre
            )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_producto,
                    parent,
                    false
                )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return lista.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = lista[position]

        holder.nombre.text =
            item.nombre

        if (
            item.imagen.isNotEmpty()
        ) {

            Glide.with(holder.itemView.context)
                .load(item.imagen)
                .into(holder.img)
        }

        holder.itemView.setOnClickListener {

            onClick(item)
        }
    }

    // FILTROS

    fun actualizarLista(
        nuevaLista: List<Producto>
    ) {

        lista.clear()

        lista.addAll(nuevaLista)

        notifyDataSetChanged()
    }
}