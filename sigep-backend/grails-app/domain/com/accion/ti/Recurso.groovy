package com.accion.ti

import org.springframework.http.HttpMethod

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes=['configAttribute', 'httpMethod', 'url'])
@ToString(includes=['configAttribute', 'httpMethod', 'url'], cache=true, includeNames=true, includePackage=false)
class Recurso implements Serializable {

	private static final long serialVersionUID = 1

	String configAttribute
	HttpMethod httpMethod
	String url

	Recurso(String url, String configAttribute, HttpMethod httpMethod = null) {
		this()
		this.configAttribute = configAttribute
		this.httpMethod = httpMethod
		this.url = url
	}

	static constraints = {
		configAttribute blank: false
		httpMethod nullable: true
		url blank: false, unique: 'httpMethod'
	}

	static mapping = {
		version false
		cache true
	}

	Recurso() {

	}
}
