package teknoeducativa.mx.agrovc.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.adapters.AdminAdapter
import teknoeducativa.mx.agrovc.models.Admin
import com.google.firebase.firestore.FirebaseFirestore

class GestionAdministradoresFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var btnAgregar: View

    private val lista = mutableListOf<Admin>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_gestion_administradores, container, false)

        recycler = view.findViewById(R.id.recyclerAdmins)
        btnAgregar = view.findViewById(R.id.btnAgregar)

        recycler.layoutManager = LinearLayoutManager(requireContext())

        cargar()

        btnAgregar.setOnClickListener {
            dialogoAdmin(null)
        }

        return view
    }

    // FORMULARIO AGREGAR / EDITAR
    private fun dialogoAdmin(admin: Admin?) {

        val view = layoutInflater.inflate(R.layout.dialog_agregar_admin, null)

        val etNombre = view.findViewById<EditText>(R.id.etNombre)
        val etUsuario = view.findViewById<EditText>(R.id.etUsuario)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)

        val btnGuardar = view.findViewById<Button>(R.id.btnGuardar)
        val btnCancelar = view.findViewById<Button>(R.id.btnCancelar)

        // llenar si edición
        if (admin != null) {
            etNombre.setText(admin.nombre)
            etUsuario.setText(admin.usuario)
            etPassword.setText(admin.contraseña)
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(false)
            .create()

        dialog.show()

        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        btnGuardar.setOnClickListener {

            val id = admin?.id_admin ?: db.collection("ADMINISTRADORES").document().id

            val data = hashMapOf(
                "id_admin" to id,
                "nombre" to etNombre.text.toString(),
                "usuario" to etUsuario.text.toString(),
                "contraseña" to etPassword.text.toString()
            )

            db.collection("ADMINISTRADORES")
                .document(id)
                .set(data)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Guardado", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    cargar()
                }
        }
    }

    // CARGAR LISTA
    private fun cargar() {

        db.collection("ADMINISTRADORES")
            .get()
            .addOnSuccessListener { result ->

                lista.clear()

                for (doc in result) {

                    val admin = Admin(
                        id_admin = doc.getString("id_admin") ?: doc.id,
                        nombre = doc.getString("nombre") ?: "",
                        usuario = doc.getString("usuario") ?: "",
                        contraseña = doc.getString("contraseña") ?: ""
                    )

                    lista.add(admin)
                }

                recycler.adapter = AdminAdapter(
                    lista,
                    onEditar = { dialogoAdmin(it) },
                    onEliminar = { a ->

                        db.collection("ADMINISTRADORES")
                            .document(a.id_admin)
                            .delete()
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(), "Eliminado", Toast.LENGTH_SHORT).show()
                                cargar()
                            }
                    }
                )
            }
    }
}