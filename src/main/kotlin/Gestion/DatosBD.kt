package Gestion

import Juego.Historial
import Usuario.Usuario
import java.sql.Connection

interface DatosBD {
    fun conectarBD(): Connection?
    fun obtenerUsuario(email: String): Usuario?
    fun obtenerHistorial(email: String): Historial?
}