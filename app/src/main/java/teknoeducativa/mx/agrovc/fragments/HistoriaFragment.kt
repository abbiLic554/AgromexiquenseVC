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

class HistoriaFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    // STORAGE
    private val storage = FirebaseStorage.getInstance()

    private val PICK_IMG_1 = 101
    private val PICK_IMG_2 = 102

    private var uri1: Uri? = null
    private var uri2: Uri? = null

    private var img1: ImageView? = null
    private var img2: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_historia, container, false)

        val btnImg1 = view.findViewById<Button>(R.id.btnImagen1)
        val btnImg2 = view.findViewById<Button>(R.id.btnImagen2)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarHistoria)

        val etDesc1 = view.findViewById<EditText>(R.id.etDescripcion1)
        val etDesc2 = view.findViewById<EditText>(R.id.etDescripcion2)

        img1 = view.findViewById(R.id.imgHistoria1)
        img2 = view.findViewById(R.id.imgHistoria2)

        val ref = db.collection("HISTORIA").document("data")

        // CARGAR DATOS
        ref.get().addOnSuccessListener {

            etDesc1.setText(it.getString("descripcion_1") ?: "")
            etDesc2.setText(it.getString("descripcion_2") ?: "")

            val imgUri1 = it.getString("imagen_1")
            val imgUri2 = it.getString("imagen_2")

            // SOLO GUARDAMOS URI
            if (!imgUri1.isNullOrEmpty()) {
                uri1 = Uri.parse(imgUri1)
            }

            if (!imgUri2.isNullOrEmpty()) {
                uri2 = Uri.parse(imgUri2)
            }
        }

        // IMAGEN 1
        btnImg1.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)

            intent.type = "image/*"

            startActivityForResult(intent, PICK_IMG_1)
        }

        // IMAGEN 2
        btnImg2.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)

            intent.type = "image/*"

            startActivityForResult(intent, PICK_IMG_2)
        }

        // GUARDAR TODO
        btnGuardar.setOnClickListener {

            if (uri1 == null || uri2 == null) {

                Toast.makeText(
                    requireContext(),
                    "Selecciona ambas imágenes",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // STORAGE 1

            val storageRef1 =
                storage.reference.child("historia/img1.jpg")

            storageRef1.putFile(uri1!!)

                .addOnSuccessListener {

                    storageRef1.downloadUrl
                        .addOnSuccessListener { url1 ->

                            // STORAGE 2

                            val storageRef2 =
                                storage.reference.child("historia/img2.jpg")

                            storageRef2.putFile(uri2!!)

                                .addOnSuccessListener {

                                    storageRef2.downloadUrl
                                        .addOnSuccessListener { url2 ->

                                            // GUARDAR FIRESTORE

                                            ref.set(
                                                mapOf(

                                                    "imagen_1" to url1.toString(),

                                                    "descripcion_1" to etDesc1.text.toString(),

                                                    "imagen_2" to url2.toString(),

                                                    "descripcion_2" to etDesc2.text.toString()
                                                )
                                            )

                                                .addOnSuccessListener {
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
                                        "Error imagen 2",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                }

                .addOnFailureListener {

                    Toast.makeText(
                        requireContext(),
                        "Error imagen 1",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        return view
    }

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

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == PICK_IMG_1) {

                uri1 = data?.data

                img1?.setImageURI(uri1)
            }

            if (requestCode == PICK_IMG_2) {

                uri2 = data?.data

                img2?.setImageURI(uri2)
            }
        }
    }
}