package co.lucaspinazzola.example.data.db.architecture

interface Sub<T>{
    fun onNext(next:T)
    fun onError(t:Throwable)
}

interface Pub<T>{
    fun addSub(sub: Sub<T>)
    fun removeSub(sub: Sub<T>)
    fun removeAllSubs()
}