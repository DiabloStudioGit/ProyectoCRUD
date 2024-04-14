package Usuario

enum class Roles {
    ESTANDAR,
    ADMINISTRADOR,
    STAFF;
    override fun toString(): String {
        return name.lowercase()
    }
}