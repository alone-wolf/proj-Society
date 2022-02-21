package com.wh.society.api.data



class ReturnObjectData<T> {
    var code: Int = 200
    var message: String = ""
    var data: T? = null

    companion object{
        fun <T>blank(): ReturnObjectData<T> {
            return ReturnObjectData()
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