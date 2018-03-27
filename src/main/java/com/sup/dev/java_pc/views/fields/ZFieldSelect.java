package com.sup.dev.java_pc.views.fields;

import com.sup.dev.java.classes.callbacks.simple.Callback;
import com.sup.dev.java.classes.callbacks.simple.CallbackSource;
import com.sup.dev.java.classes.items.Pair;
import com.sup.dev.java_pc.views.GUI;
import com.sup.dev.java_pc.tools.ToolsGui;
import com.sup.dev.java_pc.views.widgets.ZMenuItem;
import com.sup.dev.java_pc.views.widgets.ZPopup;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class ZFieldSelect<K> extends ZField implements KeyListener, FocusListener {

    private final List<Pair<String, K>> values = new ArrayList<>();
    private final List<Pair<String, K>> selected = new ArrayList<>();

    private Callback onShow;
    private CallbackSource<K> onSelect;
    private K lastSelect;
    private boolean canShowError;

    private ZPopup zPopup = new ZPopup();

    public ZFieldSelect() {
        this(GUI.S_256);
    }

    public ZFieldSelect(int w) {
        this(w, "", null);
    }

    public ZFieldSelect(int w, String hint) {
        this(w, hint, null);
    }

    public ZFieldSelect(int w, String hint, List<K> values) {
        super(w);
        addKeyListener(this);
        addFocusListener(this);
        setHint(hint);
        setValues(values);
        logic.setLocalOnTextChanged(this::onTextChanged);
    }

    public void onTextChanged() {
        canShowError = canShowError || !getText().isEmpty();
        updateSelect();
        suggest();
    }

    @Override
    public void showIfError() {
        canShowError = true;
        super.showIfError();
    }

    @Override
    public void setErrorIfEmpty() {
        setOnChangedErrorChecker(source -> getSelected() == null && canShowError);
    }


    public K getSelected() {
        for (Pair<String, K> v : values)
            if (v.left.equals(getText()))
                return v.right;
        return null;
    }

    public void clearValues() {
        this.values.clear();
    }

    public void setValues(List<K> values) {
        clearValues();
        if (values != null)
            for (K v : values)
                addValue(v);

        updateSelect();
        logic.onTextChanged();
    }

    public void addValue(K v) {
            addValue(v.toString(), v);
    }

    public void addValue(String mask, K v) {
        values.add(new Pair<>(mask, v));
    }

    public ArrayList<K> getValues() {
        ArrayList<K> l = new ArrayList<>();
        for (Pair<String, K> v : values)
            l.add(v.right);
        return l;
    }

    public void suggest() {

        hidePopup();

        if (!hasFocus())
            return;

        if (onShow != null)
            onShow.callback();

        zPopup = new ZPopup();
        zPopup.setBackground(Color.WHITE);
        zPopup.setFocusable(false);

        selected.clear();

        for (Pair<String, K> v : values)
            if (v.left.toLowerCase().startsWith(getText().toLowerCase()))
                selected.add(v);

        int added = selected.size();
        for (Pair<String, K> v : selected)
            zPopup.add(new ZMenuItem(v.left, t -> {
                setText(t);
                updateSelect();
                transferFocus();
                repaint();
            }));

        for (Pair<String, K> v : values)
            if (v.left.toLowerCase().contains(getText().toLowerCase())
                    && v.left.length() >= getText().length()
                    && !selected.contains(v))
                selected.add(v);

        for (int i = added; i < selected.size(); i++)
            zPopup.add(new ZMenuItem(selected.get(i).left, t -> {
                setText(t);
                updateSelect();
                transferFocus();
                repaint();
            }));

        zPopup.show(this, 0, getHeight() + 4);
        zPopup.setFocusable(true);
    }

    private void hidePopup() {
        if (zPopup != null && zPopup.isOpaque())
            zPopup.setVisible(false);
    }

    public void setOnShow(Callback onShow) {
        this.onShow = onShow;
    }

    public void setOnSelect(CallbackSource<K> onSelect) {
        this.onSelect = onSelect;
    }

    public void updateSelect() {
        K selected = getSelected();
        if (selected != lastSelect) {
            lastSelect = selected;
            if (onSelect != null)
                onSelect.callback(lastSelect);
        }
    }

    //
    //  Gui methods
    //


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (zPopup == null || !zPopup.isOpaque() || selected.isEmpty())
            return;

        if (e.getKeyCode() == 10) {
            setText(selected.get(0).left);
            updateSelect();
            transferFocus();
            repaint();
        }

        if (e.getKeyCode() == 40) {
            zPopup.setVisible(false);
            zPopup.show(this, 0, getHeight() + 4);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void focusGained(FocusEvent e) {
        if (zPopup == null || !zPopup.isVisible())
            suggest();
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (zPopup == null
                || e.getOppositeComponent() == null
                || ToolsGui.childOf(zPopup, e.getOppositeComponent()))
            return;

        hidePopup();
    }
}
