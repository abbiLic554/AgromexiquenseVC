package teknoeducativa.mx.agrovc.adapters

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.models.Admin

class AdminAdapter(
    private val lista: List<Admin>,
    private val onEditar: (Admin) -> Unit,
    private val onEliminar: (Admin) -> Unit
) : RecyclerView.Adapter<AdminAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val nombre = v.findViewById<TextView>(R.id.txtNombre)
        val telefono = v.findViewById<TextView>(R.id.txtTelefono)
        val ubicacion = v.findViewById<TextView>(R.id.txtUbicacion)

        val btnEditar = v.findViewById<Button>(R.id.btnEditar)
        val btnEliminar = v.findViewById<Button>(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_productor_admin, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(h: ViewHolder, position: Int) {

        val item = lista[position]

        h.nombre.text = item.nombre
        h.telefono.text = item.usuario
        h.ubicacion.text = item.usuario

        h.btnEditar.setOnClickListener { onEditar(item) }
        h.btnEliminar.setOnClickListener { onEliminar(item) }
    }

    override fun getItemCount() = lista.size
}