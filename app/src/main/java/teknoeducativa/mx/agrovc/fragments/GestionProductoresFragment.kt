package teknoeducativa.mx.agrovc.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.models.Productor
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import teknoeducativa.mx.agrovc.adapters.ProductorAdapterAdmin

class GestionProductoresFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var btnAgregar: View
    private val lista = mutableListOf<Productor>()
    private val db = FirebaseFirestore.getInstance()
    // STORAGE
    private val storage = FirebaseStorage.getInstance()
    private var imageUri: Uri? = null

    private var imgPreview: ImageView? = null

    private val PICK_IMAGE = 200

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_gestion_productores,
            container,
            false
        )

        recycler = view.findViewById(R.id.recyclerProductores)

        btnAgregar = view.findViewById(R.id.btnAgregar)

        recycler.layoutManager =
            LinearLayoutManager(requireContext())

        cargar()

        btnAgregar.setOnClickListener {

            dialogoProductor(null)
        }

        return view
    }

    // RESULTADO GALERÍA

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (
            requestCode == PICK_IMAGE &&
            resultCode == Activity.RESULT_OK &&
            data != null
        ) {

            imageUri = data.data

            imgPreview?.setImageURI(imageUri)
        }
    }

    // FORMULARIO

    private fun dialogoProductor(
        productor: Productor?
    ) {

        val view = layoutInflater.inflate(
            R.layout.dialog_agregar_productor,
            null
        )

        // IMAGEN
        val imgProductor =
            view.findViewById<ImageView>(
                R.id.imgProductor
            )

        imgPreview = imgProductor

        val btnImagen =
            view.findViewById<Button>(
                R.id.btnSeleccionarImagen
            )

        // CAMPOS
        val etNombre =
            view.findViewById<EditText>(
                R.id.etNombre
            )

        val etTelefono =
            view.findViewById<EditText>(
                R.id.etTelefono
            )

        val etUbicacion =
            view.findViewById<EditText>(
                R.id.etUbicacion
            )

        val etDescripcion =
            view.findViewById<EditText>(
                R.id.etDescripcion
            )

        val etUsuario =
            view.findViewById<EditText>(
                R.id.etUsuario
            )

        val etPassword =
            view.findViewById<EditText>(
                R.id.etPassword
            )

        val btnGuardar =
            view.findViewById<Button>(
                R.id.btnGuardar
            )

        val btnCancelar =
            view.findViewById<Button>(
                R.id.btnCancelar
            )

        // ABRIR GALERÍA

        btnImagen.setOnClickListener {

            val intent =
                Intent(Intent.ACTION_PICK)

            intent.type = "image/*"

            startActivityForResult(
                intent,
                PICK_IMAGE
            )
        }

        // EDICIÓN

        if (productor != null) {

            etNombre.setText(productor.nombre)

            etTelefono.setText(productor.telefono)

            etUbicacion.setText(productor.ubicacion)

            etDescripcion.setText(productor.descripcion)

            etUsuario.setText(productor.usuario)

            etPassword.setText(productor.contraseña)
        }

        val dialog =
            AlertDialog.Builder(
                requireContext()
            )
                .setView(view)
                .setCancelable(false)
                .create()

        dialog.show()

        // CANCELAR

        btnCancelar.setOnClickListener {

            dialog.dismiss()
        }

        // GUARDAR

        btnGuardar.setOnClickListener {

            if (imageUri == null) {

                Toast.makeText(
                    requireContext(),
                    "Selecciona una imagen",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val nombreImagen =
                System.currentTimeMillis().toString()

            val storageRef =
                storage.reference.child(
                    "productores/$nombreImagen.jpg"
                )

            // SUBIR IMAGEN

            storageRef.putFile(imageUri!!)

                .addOnSuccessListener {

                    // OBTENER URL

                    storageRef.downloadUrl
                        .addOnSuccessListener { uri ->

                            val data = mapOf(

                                "nombre" to
                                        etNombre.text.toString(),

                                "telefono" to
                                        etTelefono.text.toString(),

                                "ubicacion" to
                                        etUbicacion.text.toString(),

                                "descripcion" to
                                        etDescripcion.text.toString(),

                                "usuario" to
                                        etUsuario.text.toString(),

                                "contraseña" to
                                        etPassword.text.toString(),

                                // URL STORAGE
                                "imagen" to
                                        uri.toString()
                            )

                            // CREAR

                            if (productor == null) {

                                db.collection("PRODUCTORES")
                                    .add(data)

                                    .addOnSuccessListener {
                                        dialog.dismiss()

                                        cargar()
                                    }
                            }

                            // EDITAR

                            else {

                                db.collection("PRODUCTORES")
                                    .document(productor.id)

                                    .update(data)

                                    .addOnSuccessListener {
                                        dialog.dismiss()

                                        cargar()
                                    }
                            }
                        }
                }

                .addOnFailureListener {

                    Toast.makeText(
                        requireContext(),
                        "Error al subir imagen",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    // CARGAR LISTA

    private fun cargar() {

        db.collection("PRODUCTORES")
            .get()

            .addOnSuccessListener { result ->

                lista.clear()

                for (doc in result) {

                    val p =
                        doc.toObject(
                            Productor::class.java
                        )

                    p.id = doc.id

                    lista.add(p)
                }

                recycler.adapter =
                    ProductorAdapterAdmin(

                        lista,

                        onEditar = {

                            dialogoProductor(it)
                        },

                        onEliminar = { p ->

                            db.collection("PRODUCTORES")
                                .document(p.id)
                                .delete()

                                .addOnSuccessListener {
                                    cargar()
                                }
                        }
                    )
            }
    }
}