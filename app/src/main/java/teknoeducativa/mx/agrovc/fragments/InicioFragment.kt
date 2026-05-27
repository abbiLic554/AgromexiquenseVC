package teknoeducativa.mx.agrovc.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import teknoeducativa.mx.agrovc.R
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.TextView
import android.widget.EditText
import android.widget.Button

class InicioFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        val etTitulo = view.findViewById<EditText>(R.id.etTituloInicio)
        val etDescripcion = view.findViewById<EditText>(R.id.etDescripcionInicio)
        val btn = view.findViewById<Button>(R.id.btnGuardarInicio)

        val ref = db.collection("INICIO").document("data")

        ref.get().addOnSuccessListener {
            etTitulo.setText(it.getString("titulo") ?: "")
            etDescripcion.setText(it.getString("descripcion") ?: "")
        }

        btn.setOnClickListener {
            ref.set(mapOf(
                "titulo" to etTitulo.text.toString(),
                "descripcion" to etDescripcion.text.toString()
            ))
        }

        return view
    }
}