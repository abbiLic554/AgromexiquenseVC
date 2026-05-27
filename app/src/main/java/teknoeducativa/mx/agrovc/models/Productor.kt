package teknoeducativa.mx.agrovc.models

data class Productor(
    var id: String = "",
    var nombre: String = "",
    var telefono: String = "",
    var ubicacion: String = "",
    var descripcion: String = "",
    var usuario: String = "",
    var contraseña: String = "",
    var imagen: String? = null
)