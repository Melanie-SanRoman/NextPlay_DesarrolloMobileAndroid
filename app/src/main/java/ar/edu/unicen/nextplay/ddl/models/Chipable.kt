package ar.edu.unicen.nextplay.ddl.models

/**
 * Interfaz para objetos que pueden ser representados como un Chip en la UI.
 * Requiere que la clase tenga una propiedad 'id' y 'name'.
 */
interface Chipable {
    val id: Int
    val name: String
}
