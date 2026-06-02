package teknoeducativa.mx.agrovc.models

data class Producto(
    var id_producto: String = "",
    var nombre: String = "",
    var descripcion: String = "",
    var imagen: String = "",
    var tipo: String = "",
    var id_productor: String = "",
    var calificacion_promedio: Double = 0.0,
    var total_calificaciones: Long = 0
)
