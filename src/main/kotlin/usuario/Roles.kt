package usuario

enum class Roles {
    ESTANDAR,
    ADMINISTRADOR;

    override fun toString(): String {
        return name.lowercase()
    }
}