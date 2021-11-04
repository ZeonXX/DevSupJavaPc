package com.sup.dev.java_pc.visual_engine

import com.sup.dev.java.libs.visual_engine.graphics.VeGraphics
import com.sup.dev.java.libs.visual_engine.root.VeGui
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.RenderingHints

class VeGraphicsPc(
) : VeGraphics() {

    private var graphics: Graphics2D? = null

    init {
    }

    fun setGraphics(graphics: Graphics2D) {
        this.graphics = graphics
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY)
        graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)

        clearOffset()
    }

    override fun drawString(string: String, x: Float, y: Float) {
        graphics!!.color = Color(getColor())
        graphics!!.font = VeGuiPc.getFont(getFont())
        graphics!!.drawString(string, getOffsetX() + x, getOffsetY() + y + VeGui.getFontSize(getFont()))
    }

    override fun drawLine(x1: Float, y1: Float, x2: Float, y2: Float) {
        graphics!!.color = Color(getColor())
        graphics!!.stroke = BasicStroke(getStrokeSize())
        graphics!!.drawLine((getOffsetX() + x1).toInt(), (getOffsetY() + y1).toInt(), (getOffsetX() + x2).toInt(), (getOffsetY() + y2).toInt())
    }

    override fun fillRect(x: Float, y: Float, w: Float, h: Float) {
        graphics!!.color = Color(getColor())
        graphics!!.stroke = BasicStroke(getStrokeSize())
        graphics!!.fillRect((getOffsetX() + x).toInt(), (getOffsetY() + y).toInt(), w.toInt(), h.toInt())
    }

    override fun drawRect(x: Float, y: Float, w: Float, h: Float) {
        graphics!!.color = Color(getColor())
        graphics!!.stroke = BasicStroke(getStrokeSize())
        graphics!!.drawRect((getOffsetX() + x).toInt(), (getOffsetY() + y).toInt(), w.toInt(), h.toInt())
    }

    override fun fillCircle(x: Float, y: Float, w: Float, h: Float) {
        graphics!!.color = Color(getColor())
        graphics!!.stroke = BasicStroke(getStrokeSize())
        graphics!!.fillArc((getOffsetX() + x).toInt(), (getOffsetY() + y).toInt(), (w).toInt(), (h).toInt(), 0, 360)
    }

    override fun drawCircle(x: Float, y: Float, w: Float, h: Float) {
        graphics!!.color = Color(getColor())
        graphics!!.stroke = BasicStroke(getStrokeSize())
        graphics!!.drawArc((getOffsetX() + x).toInt(), (getOffsetY() + y).toInt(), (w).toInt(), (h).toInt(), 0, 360)
    }

    override fun fillCircle(cx: Float, cy: Float, r: Float) {
        fillCircle(cx - r, cy - r, r * 2, r * 2)
    }

    override fun drawCircle(cx: Float, cy: Float, r: Float) {
        drawCircle(cx - r, cy - r, r * 2, r * 2)
    }


}