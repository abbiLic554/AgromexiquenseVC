package teknoeducativa.mx.agrovc.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.adapters.ProductoAdapterAdmin
import teknoeducativa.mx.agrovc.models.Producto

class PerfilProductorFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var recycler: RecyclerView
    private var idProductor: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_perfil_productor,
            container,
            false
        )

        val txtNombre = view.findViewById<TextView>(R.id.txtNombre)
        val txtTelefono = view.findViewById<TextView>(R.id.txtTelefono)
        val txtUbicacion = view.findViewById<TextView>(R.id.txtUbicacion)
        val txtDescripcion = view.findViewById<TextView>(R.id.txtDescripcion)

        recycler = view.findViewById(R.id.recyclerProductos)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)

        val fab = view.findViewById<FloatingActionButton>(R.id.fabAgregar)

        // ID
        idProductor = arguments?.getString("ID_PRODUCTOR")

        if (idProductor.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "ID no recibido", Toast.LENGTH_SHORT).show()
            return view
        }

        // PRODUCTOR
        db.collection("PRODUCTORES")
            .document(idProductor!!)
            .get()
            .addOnSuccessListener { doc ->
                txtNombre.text = doc.getString("nombre") ?: ""
                txtTelefono.text = doc.getString("telefono") ?: ""
                txtUbicacion.text = doc.getString("ubicacion") ?: ""
                txtDescripcion.text = doc.getString("descripcion") ?: ""
            }

        // PRODUCTOS
        cargarProductos()

        // FAB
        fab.setOnClickListener {

            val fragment = AgregarProductoFragment()

            val bundle = Bundle()
            bundle.putString("ID_PRODUCTOR", idProductor)
            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.containerPerfil, fragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun cargarProductos() {

        db.collection("PRODUCTOS")
            .whereEqualTo("id_productor", idProductor)
            .get()
            .addOnSuccessListener { result ->

                val lista = mutableListOf<Producto>()

                for (doc in result) {

                    lista.add(
                        Producto(
                            id_producto = doc.id,
                            nombre = doc.getString("nombre") ?: "",
                            descripcion = doc.getString("descripcion") ?: "",
                            imagen = doc.getString("imagen") ?: "",
                            tipo = doc.getString("tipo") ?: "",
                            id_productor = idProductor ?: "",
                            calificacion_promedio = 0.0
                        )
                    )
                }

                recycler.adapter = ProductoAdapterAdmin(lista)
            }
    }
}