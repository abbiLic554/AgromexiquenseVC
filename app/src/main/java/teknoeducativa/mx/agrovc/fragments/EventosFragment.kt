package teknoeducativa.mx.agrovc.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import teknoeducativa.mx.agrovc.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast

class EventosFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    // STORAGE
    private val storage = FirebaseStorage.getInstance()

    private var imageUri: Uri? = null

    private var imgPreview: ImageView? = null

    private val PICK_IMAGE = 300

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_eventos,
            container,
            false
        )

        val etNombre =
            view.findViewById<EditText>(
                R.id.etNombreEvento
            )

        val etDescripcion =
            view.findViewById<EditText>(
                R.id.etDescripcionEvento
            )

        val etFecha =
            view.findViewById<EditText>(
                R.id.etFechaEvento
            )

        val etUbicacion =
            view.findViewById<EditText>(
                R.id.etUbicacionEvento
            )

        val btnImagen =
            view.findViewById<Button>(
                R.id.btnSeleccionarImagen
            )

        val btnGuardar =
            view.findViewById<Button>(
                R.id.btnGuardarEvento
            )

        imgPreview =
            view.findViewById(R.id.imgEvento)

        val ref =
            db.collection("EVENTOS")
                .document("data")

        // CARGAR DATOS

        ref.get().addOnSuccessListener {

            etNombre.setText(
                it.getString("nombre") ?: ""
            )

            etDescripcion.setText(
                it.getString("descripcion") ?: ""
            )

            etFecha.setText(
                it.getString("fecha") ?: ""
            )

            etUbicacion.setText(
                it.getString("ubicacion") ?: ""
            )
        }

        // SELECCIONAR IMAGEN

        btnImagen.setOnClickListener {

            val intent =
                Intent(Intent.ACTION_PICK)

            intent.type = "image/*"

            startActivityForResult(
                intent,
                PICK_IMAGE
            )
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

            val storageRef =
                storage.reference.child(
                    "eventos/evento.jpg"
                )

            // SUBIR IMAGEN

            storageRef.putFile(imageUri!!)

                .addOnSuccessListener {

                    // OBTENER URL

                    storageRef.downloadUrl
                        .addOnSuccessListener { uri ->

                            ref.set(

                                mapOf(

                                    "nombre" to
                                            etNombre.text.toString(),

                                    "descripcion" to
                                            etDescripcion.text.toString(),

                                    "fecha" to
                                            etFecha.text.toString(),

                                    "ubicacion" to
                                            etUbicacion.text.toString(),

                                    "imagen" to
                                            uri.toString()
                                )
                            )

                                .addOnSuccessListener {

                                    Toast.makeText(
                                        requireContext(),
                                        "Evento guardado",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                .addOnFailureListener {

                                    Toast.makeText(
                                        requireContext(),
                                        "Error Firestore",
                                        Toast.LENGTH_SHORT
                                    ).show()
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

        return view
    }

    // RESULTADO IMAGEN

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
            resultCode == Activity.RESULT_OK
        ) {

            imageUri = data?.data

            imgPreview?.setImageURI(imageUri)
        }
    }
}