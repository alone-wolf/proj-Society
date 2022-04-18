package com.wh.admin.data



class ReturnObjectData<T> {
    var code: Int = 200
    var message: String = ""
    var data: T? = null

    constructor(t:T?){
        data = t
    }

    companion object{
        fun <T>blank(t:T?=null): ReturnObjectData<T> {
            return ReturnObjectData(t)
        }
    }

    fun notNullOrBlank(t:T): T {
        return data ?: t
    }
}

class ReturnListData<T> {
    var code: Int = 200
    var message: String = ""
    var data: List<T> = emptyList()

   companion object{
       fun <T>blank(): ReturnListData<T> {
           return ReturnListData()
       }
   }

    val newestIndex:Int
    get() = if (data.isEmpty()) 0 else data.size - 1
}