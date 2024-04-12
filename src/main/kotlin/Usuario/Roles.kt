package Usuario

enum class Roles {
    ESTANDAR,
    ADMINISTRADOR,
    ADMIN_NoJuego;
    override fun toString(): String {
        return name.lowercase()
    }
}