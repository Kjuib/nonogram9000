package com.fnod.nonogram.buttons;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: hbillings
 * Date: 2/9/13
 * Time: 3:05 PM
 */
public class ButtonCreate extends JLabel {
    private static final Color CLR_UNKNOWN = Color.WHITE;
    private static final Color CLR_HIT = Color.BLACK;

    private boolean mouseOver;

    public ButtonCreate() {
        mouseOver = false;
        setBackground(CLR_UNKNOWN);
        setBorder(new LineBorder(Color.GRAY));
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (mouseOver) {
                    updateSelection();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getModifiers() != 0) {
                    updateSelection();
                }
                mouseOver = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver = false;
            }
        });
    }

    private void updateSelection() {
        if (getBackground().equals(CLR_UNKNOWN)) {
            setBackground(CLR_HIT);
        } else {
            setBackground(CLR_UNKNOWN);
        }
    }

    public boolean isSelected() {
        return getBackground().equals(CLR_HIT);
    }

    public void setSelected(boolean selected) {
        if (selected) {
            setBackground(CLR_HIT);
        } else {
            setBackground(CLR_UNKNOWN);
        }
    }

    public static void main(String[] args) {
        JFrame dlg = new JFrame();
        dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dlg.setLayout(new GridLayout(1, 3, 1, 1));
        dlg.setMinimumSize(new Dimension(200, 100));

        ButtonCreate btn1 = new ButtonCreate();
        dlg.add(btn1);

        ButtonCreate btn2 = new ButtonCreate();
        dlg.add(btn2);

        ButtonCreate btn3 = new ButtonCreate();
        dlg.add(btn3);

        dlg.setVisible(true);
    }

}
