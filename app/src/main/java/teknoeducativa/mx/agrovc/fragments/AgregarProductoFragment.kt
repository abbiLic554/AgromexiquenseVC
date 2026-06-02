package teknoeducativa.mx.agrovc.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import teknoeducativa.mx.agrovc.R

class AgregarProductoFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference

    private var idProductor: String? = null
    private var imageUri: Uri? = null

    private lateinit var imgProducto: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.dialog_producto, container, false)

        idProductor = arguments?.getString("ID_PRODUCTOR")

        imgProducto = view.findViewById(R.id.imgProducto)
        val btnImagen = view.findViewById<Button>(R.id.btnSeleccionarImagen)

        val etNombre = view.findViewById<EditText>(R.id.etNombreProducto)
        val etDescripcion = view.findViewById<EditText>(R.id.etDescripcion)

        val spinner = view.findViewById<Spinner>(R.id.spTipoProducto)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarProducto)

        // TIPOS (CORREGIDOS A TU SISTEMA)
        val tipos = arrayOf(
            "Frutas",
            "Verduras",
            "Artesanías",
            "Piel",
            "Otros"
        )

        spinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            tipos
        )

        // ABRIR GALERÍA
        val pickImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
                imgProducto.setImageURI(uri)
            }
        }

        btnImagen.setOnClickListener {
            pickImage.launch("image/*")
        }

        // GUARDAR
        btnGuardar.setOnClickListener {

            val nombre = etNombre.text.toString()
            val descripcion = etDescripcion.text.toString()
            val tipo = spinner.selectedItem.toString()

            if (nombre.isEmpty() || descripcion.isEmpty() || imageUri == null) {
                Toast.makeText(requireContext(), "Completa todo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            subirImagenYGuardar(nombre, descripcion, tipo)
        }

        return view
    }

    private fun subirImagenYGuardar(
        nombre: String,
        descripcion: String,
        tipo: String
    ) {

        val ref = storage.child("productos/${System.currentTimeMillis()}.jpg")

        ref.putFile(imageUri!!)
            .addOnSuccessListener {

                ref.downloadUrl.addOnSuccessListener { url ->

                    val producto = hashMapOf(
                        "nombre" to nombre,
                        "descripcion" to descripcion,
                        "tipo" to tipo,
                        "imagen" to url.toString(),
                        "id_productor" to idProductor,

                        // CALIFICACIONES
                        "calificacion_promedio" to 0.0,
                        "suma_calificaciones" to 0.0,
                        "total_calificaciones" to 0
                    )

                    db.collection("PRODUCTOS")
                        .add(producto)
                        .addOnSuccessListener {

                            Toast.makeText(requireContext(), "Guardado", Toast.LENGTH_SHORT).show()
                            parentFragmentManager.popBackStack()
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error al subir imagen", Toast.LENGTH_SHORT).show()
            }
    }
}