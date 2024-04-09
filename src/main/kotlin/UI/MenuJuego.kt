package UI

import Gestion.GestionarHistoriales
import Inputs.InputsJuego
import Inputs.InputsMenus
import Juego.Historial
import Juego.LogicaJuego
import Usuario.Usuario

class MenuJuego {
    val gestor = GestionarHistoriales()
    val usuario : Usuario
    val historial : Historial?
    constructor(usuario : Usuario) {
        this.usuario = usuario
        this.historial = gestor.obtenerHistorial(usuario.email)
    }
    fun juego() {
        var eleccion = true
        val juego = LogicaJuego()

        while (eleccion) {
            if (historial == null) {
                println("[ERROR] Historial no encontrado")
                eleccion = false
            } else {
                when (menuJuego()) {
                    1 -> gestor.modificarHistorial(historial, juego.hasAcertado(historial, InputsJuego.introducirNumero()), false)
                    2 -> {
                        println(historial)
                        println("Porcentaje de Victorias: ${juego.calculoPorcentajeVictorias(historial)}%")
                    }
                    3 -> eleccion = false
                }
            }
        }
    }

    fun menuJuego(): Int {
        println("Hola! [${usuario.nombre}]")
        println("@======¿Que Desea Hacer?======@")
        println("|                             |")
        println("|        [1]  Jugar           |")
        println("|      [2]  Puntuacion        |")
        println("|    [3]  Cerrar Sesion       |")
        println("|                             |")
        println("@=============================@")

        return InputsMenus.seleccionarOpcionMenu(3)
    }
}