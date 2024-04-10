package Juego

import java.io.Serializable
data class Historial(var emailJugador : String, var partidasJugadas : Int, var partidasGanadas : Int, var puntos : Int): Serializable {

    fun copy() : Historial {
        return Historial(this.emailJugador, this.partidasJugadas, this.partidasGanadas, this.puntos)
    }

    override fun toString(): String {
        return "partida:   \n  Email: $emailJugador \n  Partidas Jugadas: $partidasJugadas \n  Partidas Ganadas: $partidasGanadas \n  Puntos: $puntos"
    }
}
