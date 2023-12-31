grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.war.file = "target/sigep.war"
grails.server.port.http = 90
grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        mavenRepo "http://repository.codehaus.org"
        mavenRepo "http://download.java.net/maven/2/"
        mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {

// https://mvnrepository.com/artifact/xml-apis/-apis
        
        // compile group: 'xml-apis', name: 'xml-apis', version: '2.0.2'
        
        compile 'xml-apis:xml-apis:1.4.01'

        runtime 'mysql:mysql-connector-java:8.0.28'
        compile 'org.modelmapper:modelmapper:0.7.3'

        compile 'org.apache.poi:poi:5.2.3'
        compile 'org.apache.poi:poi-ooxml:5.2.3'
        test "org.grails:grails-datastore-test-support:1.0.2-grails-2.4"

        compile 'joda-time:joda-time:2.9.4'
        compile 'com.jameskleeh:excel-builder:0.4.4'
        // compile 'org.apache.poi:poi-ooxml-schemas:4.1.2'
        compile 'org.apache.poi:poi-ooxml-full:5.2.3'
        // compile 'org.apache.xmlbeans:xmlbeans:5.1.1'
        compile group: 'org.apache.commons', name: 'commons-collections4', version: '4.1'


    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.70" // or ":tomcat:8.0.22"

        // plugins for the compile step
        compile ":scaffolding:2.1.2"
        compile ':cache:1.1.8'
        // asset-pipeline 2.0+ requires Java 7, use version 1.9.x with Java 6
        compile ":asset-pipeline:2.5.7"

        // plugins needed at runtime but not for compilation
        runtime ":hibernate4:4.3.10" // or ":hibernate:3.6.10.18"
        runtime ":database-migration:1.4.0"
        runtime ":jquery:1.11.1"

        // Uncomment these to enable additional asset-pipeline capabilities
        //compile ":sass-asset-pipeline:1.9.0"
        //compile ":less-asset-pipeline:1.10.0"
        //compile ":coffee-asset-pipeline:1.8.0"
        //compile ":handlebars-asset-pipeline:1.3.0.3"

        //Plugins adicionales
        compile("org.grails.plugins:db-reverse-engineer:4.0.0") {
            excludes "servlet-api"
        }
        compile "org.grails.plugins:spring-security-core:2.0.0"
        compile "org.grails.plugins:spring-security-rest:1.5.4"
        runtime "org.grails.plugins:cors:1.3.0"
        compile "org.grails.plugins:mail:1.0.7"
    }
}
