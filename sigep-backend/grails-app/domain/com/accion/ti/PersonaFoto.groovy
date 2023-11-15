package com.accion.ti

class PersonaFoto {

	byte[] foto
	Persona persona

	static belongsTo = [Persona]

	static mapping = {
		version false
	}
}
