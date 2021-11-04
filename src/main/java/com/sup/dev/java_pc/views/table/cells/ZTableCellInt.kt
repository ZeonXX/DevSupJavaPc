package com.sup.dev.java_pc.views.table.cells


class ZTableCellInt(type: Int, size: Int, label: String, canBeEmpty: Boolean) : ZTableCellField(type, size, label, canBeEmpty) {

    constructor(size: Int, label: String, canBeEmpty: Boolean) : this(0, size, label, canBeEmpty) {}

    init {

        field.setOnlyInt()
        setValue(null!!, true)
    }

    override fun getCellValue(): Any {
        return field.int
    }

}
