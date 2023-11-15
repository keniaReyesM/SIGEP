package com.accion.ti

class ClienteFoto {

	byte[] foto
	Cliente cliente

	static belongsTo = [Cliente]

	static mapping = {
		version false
	}
}
