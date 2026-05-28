package teknoeducativa.mx.agrovc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.models.Evento
import java.text.SimpleDateFormat
import java.util.*

class EventoAdapter(
    private val lista: List<Evento>
) : RecyclerView.Adapter<EventoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre = view.findViewById<TextView>(R.id.tvNombre)
        val fecha = view.findViewById<TextView>(R.id.tvFecha)
        val ubicacion = view.findViewById<TextView>(R.id.tvUbicacion)
        val descripcion = view.findViewById<TextView>(R.id.tvDescripcion)
        val imagen = view.findViewById<ImageView>(R.id.imgEvento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_evento, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = lista[position]

        holder.nombre.text = item.nombre
        holder.fecha.text = item.fecha.toString()
        holder.ubicacion.text = item.ubicacion
        holder.descripcion.text = item.descripcion

        Glide.with(holder.itemView.context)
            .load(item.imagen)
            .into(holder.imagen)
    }
}