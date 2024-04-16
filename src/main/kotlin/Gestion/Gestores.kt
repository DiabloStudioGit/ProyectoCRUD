package Gestion

import Gestion.BaseDeDatos.ConexionBD
import Gestion.BaseDeDatos.GestionarHistorialesBD
import Gestion.BaseDeDatos.GestionarLogsBD
import Gestion.BaseDeDatos.GestionarUsuariosBD
import Gestion.Fichero.GestionarHistoriales
import Gestion.Fichero.GestionarLogs
import Gestion.Fichero.GestionarUsuarios
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Gestores {

    companion object {
        lateinit var gestorUsuarios : IGestorUsuarios
        lateinit var gestorHistoriales : IGestorHistoriales
        lateinit var gestorLogs : IGestorLogs

        fun establecerFicheros() {
            this.gestorUsuarios = GestionarUsuarios()
            this.gestorHistoriales = GestionarHistoriales()
            this.gestorLogs = GestionarLogs()
        }

        fun establecerBaseDeDatos() {
            val conexion = ConexionBD()
            this.gestorUsuarios = GestionarUsuariosBD()
            this.gestorHistoriales = GestionarHistorialesBD()
            this.gestorLogs = GestionarLogsBD()
        }

        //Fecha actual formateada
        fun fechaActual(): String {
            return (LocalDateTime.now()).format(DateTimeFormatter.ofPattern("HH:mm dd:MM:yyyy"))
        }
    }
}