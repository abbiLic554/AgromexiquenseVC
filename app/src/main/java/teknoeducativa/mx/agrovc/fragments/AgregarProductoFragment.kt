package teknoeducativa.mx.agrovc.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import teknoeducativa.mx.agrovc.R

class AgregarProductoFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    private var idProductor: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.dialog_producto,
            container,
            false
        )

        // RECIBIR ID
        idProductor = arguments?.getString("ID_PRODUCTOR")

        val imgProducto = view.findViewById<ImageView>(R.id.imgProducto)
        val btnImagen = view.findViewById<Button>(R.id.btnSeleccionarImagen)

        val etNombre = view.findViewById<EditText>(R.id.etNombreProducto)
        val etDescripcion = view.findViewById<EditText>(R.id.etDescripcion)

        val spinner = view.findViewById<Spinner>(R.id.spTipoProducto)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarProducto)

        // SPINNER SIMPLE
        val tipos = arrayOf("Fruta", "Verdura", "Grano", "Otro")
        spinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            tipos
        )

        // BOTÓN IMAGEN (solo placeholder por ahora)
        btnImagen.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Aquí abrirías galería o cámara",
                Toast.LENGTH_SHORT
            ).show()
        }

        // GUARDAR PRODUCTO
        btnGuardar.setOnClickListener {

            val nombre = etNombre.text.toString()
            val descripcion = etDescripcion.text.toString()
            val tipo = spinner.selectedItem.toString()

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val producto = hashMapOf(
                "nombre" to nombre,
                "descripcion" to descripcion,
                "tipo" to tipo,
                "imagen" to "",
                "id_productor" to idProductor
            )

            db.collection("PRODUCTOS")
                .add(producto)
                .addOnSuccessListener {

                    Toast.makeText(
                        requireContext(),
                        "Producto guardado",
                        Toast.LENGTH_SHORT
                    ).show()

                    parentFragmentManager.popBackStack()
                }
                .addOnFailureListener {

                    Toast.makeText(
                        requireContext(),
                        "Error al guardar",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        return view
    }
}