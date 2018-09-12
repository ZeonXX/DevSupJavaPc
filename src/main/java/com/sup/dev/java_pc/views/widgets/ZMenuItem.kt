package com.sup.dev.java_pc.views.widgets

import com.sup.dev.java_pc.views.GUI
import java.awt.Color
import javax.swing.JMenuItem


class ZMenuItem(text: String?, callback: (String?)->Unit) : JMenuItem() {


    init {
        background = Color.WHITE
        font = GUI.BODY_2
        setText(text)

        addActionListener { e -> callback.invoke(text) }

    }

}
