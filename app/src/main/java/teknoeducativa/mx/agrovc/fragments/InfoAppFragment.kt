package teknoeducativa.mx.agrovc.fragments

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import teknoeducativa.mx.agrovc.R
import com.google.firebase.firestore.FirebaseFirestore

class InfoAppFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_info_app, container, false)

        view.findViewById<Button>(R.id.btnInicio).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, InicioFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<Button>(R.id.btnHistoria).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HistoriaFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<Button>(R.id.btnEventos).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, EventosFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}