package alepando.dev.dialogapi.util

class InputValueList {
    val list: MutableList<InputValue> = mutableListOf()

    fun add(inputValue: InputValue){
        list.add(inputValue)
    }

    fun get(key:String): Any?{
        return list.firstOrNull { it.key == key }
    }
}