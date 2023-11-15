class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{}
        "/api/$controller?/$action?/$id?(.$format)?"{}
        "/"(view:"/index")
        "500"(controller:"error", method:"index")
        // "500"(view:'/error')
//        "/login/auth"( view: "/index")
    }
}
