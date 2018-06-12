package io.corbs

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class TodosApp

fun main(args: Array<String>) {
    println("one=${System.getProperty("one")}")
    SpringApplication.run(TodosApp::class.java, *args)
}
