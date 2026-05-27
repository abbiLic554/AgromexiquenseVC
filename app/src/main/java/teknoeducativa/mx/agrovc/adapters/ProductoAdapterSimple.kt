package teknoeducativa.mx.agrovc.activities.cliente

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.models.Producto

class ProductoAdapterSimple(
    private val lista: List<Producto>
) : RecyclerView.Adapter<ProductoAdapterSimple.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val nombre: TextView = v.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.nombre.text = lista[position].nombre
    }

    override fun getItemCount() = lista.size
}