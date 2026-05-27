package teknoeducativa.mx.agrovc.activities.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import teknoeducativa.mx.agrovc.R
import teknoeducativa.mx.agrovc.fragments.AdminFragment

class MainActivityAdmin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)

        val rol = intent.getStringExtra("ROL")

        if (savedInstanceState == null && rol == "COMITE") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AdminFragment())
                .commit()
        }
    }
}