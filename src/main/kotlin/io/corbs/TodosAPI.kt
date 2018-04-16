package io.corbs

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

var seq:Int=0

data class Todo(var id:Int=0, var title:String="", var completed:Boolean=false, var order:Int=-1){

    // Needed because of jackson
    // Can also add jackson-kotlin, but need to update mapper. 1 line vs 15 ...
    @Suppress("unused")
    constructor():this(0)

    @Suppress("unused")
    val url:String
        get()="${Config.root}/$id"
}

@RestController
@CrossOrigin
class TodosAPI {

    val todos = HashMap<Int, Todo>()

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

    @GetMapping("/hi")
    fun hi(): String {
        return "hi"
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
        if (todo.order>-1) old.order=todo.order

        return Mono.just(old)
    }
}
