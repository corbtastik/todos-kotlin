package io.corbs

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.cloud.client.ServiceInstance
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.discovery.DiscoveryClient


var seq:Int=0

data class Todo(var id:Int=0, var title:String="", var completed:Boolean=false){

    // Needed because of jackson
    // Can also add jackson-kotlin, but need to update mapper. 1 line vs 15 ...
    @Suppress("unused")
    constructor():this(0)
}


@RestController
@CrossOrigin
class TodosAPI {

    val todos = HashMap<Int, Todo>()

    @Autowired
    private val discoveryClient: DiscoveryClient? = null

    @RequestMapping("/service-instances/{applicationName}")
    fun serviceInstancesByApplicationName(
            @PathVariable applicationName: String): List<ServiceInstance> {
        return this.discoveryClient!!.getInstances(applicationName)
    }

    @GetMapping("/")
    fun listTodo(): Flux<Todo> = Flux.fromIterable(todos.values)

    @PostMapping("/")
    fun createTodo(@RequestBody todo: Todo): Mono<Todo> {
        todo.id= seq++
        todos[todo.id]=todo
        return Mono.just(todo)
    }

    @DeleteMapping("/")
    fun clean() { todos.clear() }

    @GetMapping("/howdy")
    fun hi(): String {
        return "howdy"
    }

    @GetMapping("/{id}")
    fun getTodo(@PathVariable("id") id: Int): Mono<Todo> = Mono.just(todos[id])

    @DeleteMapping("/{id}")
    fun remove(@PathVariable("id") id: Int){
        todos.remove(id)
    }

    @PatchMapping("/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody todo: Todo): Mono<Todo> {
        if (!todos.contains(id)) return Mono.empty()

        val old=todos[id]!!
        if (!todo.title.isEmpty()) old.title=todo.title
        if (todo.completed) old.completed=true

        return Mono.just(old)
    }
}
