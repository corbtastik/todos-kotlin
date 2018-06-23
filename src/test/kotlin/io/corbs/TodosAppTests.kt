package io.corbs

import org.junit.Test
import org.junit.runner.RunWith
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux

@RunWith(SpringRunner::class)
@SpringBootTest
class TodosAppTests {

    /**
     * Demo scattering (Publishing) commands that can run
     * independent by a (Subscriber) and collected and combined for a unified result
     */
	@Test
	fun scatterGather() {

        val commands = Flux.just(Work(), Work(), Work())
	}

}


class Work {

}

class WorkSubscriber: Subscriber<Work> {
    override fun onComplete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSubscribe(s: Subscription?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNext(t: Work?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(t: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}