package teknoeducativa.mx.agrovc.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import teknoeducativa.mx.agrovc.R

class AdminFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_admin, container, false)

        val btnUsuarios = view.findViewById<Button>(R.id.btnGestionComite)
        val btnProductores = view.findViewById<Button>(R.id.btnGestionProductores)
        val btnInfo = view.findViewById<Button>(R.id.btnGestionContenido)

        btnProductores.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, GestionProductoresFragment())
                .addToBackStack(null)
                .commit()
        }

        btnUsuarios.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, GestionAdministradoresFragment())
                .addToBackStack(null)
                .commit()
        }

        btnInfo.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, InfoAppFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}