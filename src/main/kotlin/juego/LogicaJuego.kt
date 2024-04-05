package juego

class LogicaJuego {
    fun crearNumeroAleatorio(): Int {
        return (1..10).random()
    }

    fun hasAcaertado(historial: Historial, numeroJugador: Int) {
        if (numeroJugador == (1..10).random()) {
            println("!Has acertado!")
            historial.partidasGanadas++
            historial.partidasJugadas++
        } else {
            println("No has hacertado :(")
            historial.partidasJugadas++
        }
    }

    fun calculoPorcentajeVictorias(historial: Historial): Double {
        return historial.partidasGanadas / historial.partidasJugadas.toDouble()
    }
}