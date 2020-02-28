package co.lucaspinazzola.example.data.db.architecture

import com.squareup.sqldelight.internal.AtomicBoolean

abstract class BasePub<T> : Pub<T> {

    private val hadError = AtomicBoolean(false)

    internal abstract fun subs():MutableCollection<Sub<T>>
    fun applyNextValue(next:T){
        subs().forEach { it.onNext(next) }
    }

    fun applyError(throwable: Throwable){
        val subIter = subs().iterator()
        subs().clear()
        hadError.set(true)
        subIter.forEach { it.onError(throwable) }
    }

    fun applyNext(block:()->T){
        try {
            applyNextValue(block())
        }catch (t:Throwable){
            applyError(t)
        }
    }

    override fun addSub(sub: Sub<T>) {
        checkError()
        subs().add(sub)
    }

    override fun removeSub(sub: Sub<T>) {
        subs().remove(sub)
    }

    override fun removeAllSubs() {
        subs().clear()
    }

    private fun checkError(){
        if(hadError.get())
            throw IllegalStateException("Had error")
    }
}