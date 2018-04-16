package io.corbs

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
class WebConfig : WebFluxConfigurer {
    @Value("\${todos.cors.allowed.origin}")
    var origin: String? = null

    override fun addCorsMappings(registry: CorsRegistry?) {
        registry!!.addMapping("/**")
                .allowedOrigins(origin)
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)

    }
}

@SpringBootApplication
class TodosApp {

}

object Config{
    var root: String = "http://localhost:8080"
}

fun main(args: Array<String>) {
    if (args.isNotEmpty()) Config.root =args[0]

    SpringApplication.run(TodosApp::class.java, *args)
}
