import UI.MenuLogin

fun main() {
    var menuLogin = MenuLogin()
    do {
        menuLogin.imprimirOpciones()
    } while (!menuLogin.pedirAccion())
}