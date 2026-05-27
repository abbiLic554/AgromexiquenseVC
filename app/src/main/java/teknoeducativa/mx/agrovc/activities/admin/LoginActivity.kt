package teknoeducativa.mx.agrovc.activities.admin

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import teknoeducativa.mx.agrovc.R

class LoginActivity : AppCompatActivity() {

    private val db =
        FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val etUsuario =
            findViewById<EditText>(R.id.etUsuario)

        val etPassword =
            findViewById<EditText>(R.id.etPassword)

        val btnAdmin =
            findViewById<Button>(R.id.btnLoginAdmin)

        val btnProductor =
            findViewById<Button>(R.id.btnLoginProductor)

        // LOGIN ADMIN

        btnAdmin.setOnClickListener {

            val usuario =
                etUsuario.text.toString().trim()

            val password =
                etPassword.text.toString().trim()

            if (
                usuario.isEmpty() ||
                password.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Llena todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            db.collection("ADMINISTRADORES")
                .whereEqualTo(
                    "usuario",
                    usuario
                )
                .get()

                .addOnSuccessListener { result ->

                    if (result.isEmpty) {

                        Toast.makeText(
                            this,
                            "No existe admin",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@addOnSuccessListener
                    }

                    val doc =
                        result.documents[0]

                    val pass =
                        doc.getString("contraseña") ?: ""

                    if (pass == password) {

                        val intent =
                            Intent(
                                this,
                                MainActivityAdmin::class.java
                            )

                        // ENVIAR ROL
                        intent.putExtra(
                            "ROL",
                            "COMITE"
                        )

                        startActivity(intent)

                        finish()

                    } else {

                        Toast.makeText(
                            this,
                            "Contraseña incorrecta",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Error de conexión",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        // LOGIN PRODUCTOR

        btnProductor.setOnClickListener {

            val usuario =
                etUsuario.text.toString().trim()

            val password =
                etPassword.text.toString().trim()

            if (
                usuario.isEmpty() ||
                password.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Llena todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            db.collection("PRODUCTORES")
                .whereEqualTo(
                    "usuario",
                    usuario
                )
                .get()

                .addOnSuccessListener { result ->

                    if (result.isEmpty) {

                        Toast.makeText(
                            this,
                            "No existe productor",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@addOnSuccessListener
                    }

                    val doc =
                        result.documents[0]

                    val pass =
                        doc.getString("contraseña") ?: ""

                    if (pass == password) {

                        val idProductor =
                            doc.getString("id_productor")
                                ?: doc.id

                        val intent =
                            Intent(
                                this,
                                PerfilProductorActivity::class.java
                            )

                        intent.putExtra(
                            "ID_PRODUCTOR",
                            idProductor
                        )

                        startActivity(intent)

                        finish()

                    } else {

                        Toast.makeText(
                            this,
                            "Contraseña incorrecta",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Error de conexión",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}