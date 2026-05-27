package teknoeducativa.mx.agrovc

import android.app.Application
import com.google.firebase.FirebaseApp

class AgroApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}