package teknoeducativa.mx.agrovc.activities.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.fragments.PerfilProductorFragment

class PerfilProductorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_perfil_productor)

        val id = intent.getStringExtra("ID_PRODUCTOR")

        Toast.makeText(
            this,
            "ID recibido: $id",
            Toast.LENGTH_SHORT
        ).show()

        val fragment = PerfilProductorFragment()

        val bundle = Bundle()

        bundle.putString(
            "ID_PRODUCTOR",
            id
        )

        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.containerPerfil, fragment)
            .commit()
    }
}