package com.fnod.nonogram.buttons;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: hbillings
 * Date: 2/9/13
 * Time: 3:05 PM
 */
public class ButtonPlay extends JLabel {
    private static final Color CLR_UNKNOWN = Color.WHITE;
    private static final Color CLR_HIT = Color.BLACK;
    private static final Color CLR_MISS = Color.LIGHT_GRAY;
    private static final Color CLR_GUESS1 = Color.CYAN;
    private static final Color CLR_GUESS2 = Color.YELLOW;

    private boolean selected;
    private int posX;
    private int posY;

    public ButtonPlay() {
        setBackground(CLR_UNKNOWN);
        setBorder(new LineBorder(Color.GRAY));
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                updateSelection(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getModifiers() != 0) {
                    updateSelection(e);
                }
            }
        });
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    private void updateSelection(MouseEvent e) {
        if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK && (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK) {
            if (getBackground().equals(CLR_GUESS1)) {
                setBackground(CLR_UNKNOWN);
            } else {
                setBackground(CLR_GUESS1);
            }
        } else if (((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK && (e.getModifiersEx() & InputEvent.ALT_DOWN_MASK) == InputEvent.ALT_DOWN_MASK) || e.getModifiersEx() == InputEvent.BUTTON2_DOWN_MASK) {
            if (getBackground().equals(CLR_GUESS2)) {
                setBackground(CLR_UNKNOWN);
            } else {
                setBackground(CLR_GUESS2);
            }
        } else if (((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK && (e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == InputEvent.SHIFT_DOWN_MASK) || e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK) {
            if (getBackground().equals(CLR_MISS)) {
                setBackground(CLR_UNKNOWN);
            } else {
                setBackground(CLR_MISS);
            }
        } else if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK) {
            if (getBackground().equals(CLR_HIT)) {
                setBackground(CLR_UNKNOWN);
            } else {
                setBackground(CLR_HIT);
            }
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean test() {
        if (isSelected() && getBackground() == CLR_HIT) {
            return true;
        } else if (!isSelected() && getBackground() != CLR_HIT) {
            return true;
        }

        return false;
    }
}
