package com.kec.apicalling

object ColorPicker {
    val colors = arrayOf("aqua", "black", "blue", "fuchsia","gray", "green",
            "lime", "maroon", "navy", "olive", "purple", "red",
            "silver", "teal", "white", "yellow")
    var colorIndex  = 1
    fun getColor() : String {
        return colors[colorIndex++ % colors.size]
    }
}