package teknoeducativa.mx.agrovc.adapters

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.models.Producto
import com.google.firebase.firestore.FirebaseFirestore

class ProductoAdapterAdmin(
    private val lista: MutableList<Producto>
) : RecyclerView.Adapter<ProductoAdapterAdmin.ViewHolder>() {

    private val db = FirebaseFirestore.getInstance()

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val imgProducto:
                ImageView =
            view.findViewById(R.id.imgProductoItem)

        val txtNombre:
                TextView =
            view.findViewById(R.id.txtNombreProductoItem)

        val txtDescripcion:
                TextView =
            view.findViewById(R.id.txtDescripcionProductoItem)

        val btnEditar:
                Button =
            view.findViewById(R.id.btnEditar)

        val btnEliminar:
                Button =
            view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(
            parent.context
        ).inflate(
            R.layout.item_producto_admin,
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

        val producto = lista[position]

        // DATOS

        holder.txtNombre.text =
            producto.nombre

        holder.txtDescripcion.text =
            producto.descripcion

        // IMAGEN

        if (
            producto.imagen.isNotEmpty()
        ) {

            Glide.with(holder.itemView.context)
                .load(producto.imagen)
                .into(holder.imgProducto)
        }

        // ELIMINAR

        holder.btnEliminar.setOnClickListener {

            db.collection("PRODUCTOS")
                .document(producto.id_producto)
                .delete()

                .addOnSuccessListener {

                    Toast.makeText(
                        holder.itemView.context,
                        "Producto eliminado",
                        Toast.LENGTH_SHORT
                    ).show()

                    lista.removeAt(position)

                    notifyItemRemoved(position)
                }
        }

        // EDITAR

        holder.btnEditar.setOnClickListener {

            val dialog =
                Dialog(holder.itemView.context)

            val view = LayoutInflater.from(
                holder.itemView.context
            ).inflate(
                R.layout.dialog_producto,
                null
            )

            dialog.setContentView(view)

            val imgProducto =
                view.findViewById<ImageView>(
                    R.id.imgProducto
                )

            val etNombre =
                view.findViewById<EditText>(
                    R.id.etNombreProducto
                )

            val etDescripcion =
                view.findViewById<EditText>(
                    R.id.etDescripcion
                )

            val spinner =
                view.findViewById<Spinner>(
                    R.id.spTipoProducto
                )

            val btnGuardar =
                view.findViewById<Button>(
                    R.id.btnGuardarProducto
                )

            // TIPOS

            val tipos = arrayOf(
                "Frutas",
                "Verduras",
                "Artesanías",
                "Piel",
                "Otros"
            )

            spinner.adapter = ArrayAdapter(

                holder.itemView.context,

                android.R.layout.simple_spinner_dropdown_item,

                tipos
            )

            // SELECCIONAR TIPO ACTUAL

            val posicion =
                tipos.indexOf(producto.tipo)

            if (posicion >= 0) {

                spinner.setSelection(posicion)
            }

            // DATOS ACTUALES

            etNombre.setText(
                producto.nombre
            )

            etDescripcion.setText(
                producto.descripcion
            )

            if (
                producto.imagen.isNotEmpty()
            ) {

                Glide.with(holder.itemView.context)
                    .load(producto.imagen)
                    .into(imgProducto)
            }

            btnGuardar.text =
                "Actualizar"

            // ACTUALIZAR

            btnGuardar.setOnClickListener {

                val nuevoNombre =
                    etNombre.text.toString()

                val nuevaDescripcion =
                    etDescripcion.text.toString()

                val nuevoTipo =
                    spinner.selectedItem.toString()

                db.collection("PRODUCTOS")
                    .document(producto.id_producto)

                    .update(
                        mapOf(

                            "nombre" to nuevoNombre,

                            "descripcion" to nuevaDescripcion,

                            "tipo" to nuevoTipo
                        )
                    )

                    .addOnSuccessListener {

                        Toast.makeText(
                            holder.itemView.context,
                            "Producto actualizado",
                            Toast.LENGTH_SHORT
                        ).show()

                        producto.nombre =
                            nuevoNombre

                        producto.descripcion =
                            nuevaDescripcion

                        producto.tipo =
                            nuevoTipo

                        notifyItemChanged(
                            holder.adapterPosition
                        )

                        dialog.dismiss()
                    }
            }

            dialog.show()
        }
    }
}