package com.fnod.nonogram;

import com.fnod.nonogram.panels.PnlCreate;
import com.fnod.nonogram.panels.PnlPlay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: hbillings
 * Date: 2/8/13
 * Time: 7:10 PM
 */
public class Main extends JFrame {
    private JTabbedPane tabMain;

    public Main() throws HeadlessException {
        initComponents();
    }

    private void initComponents() {
        setTitle("Nonogram 9000");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 500));
        setupLookAndFeel();

        JToolBar barMain = new JToolBar();
        barMain.setOrientation(SwingConstants.HORIZONTAL);
        barMain.setFloatable(false);
        barMain.setBorderPainted(false);
        {
            JButton btnPlay = new JButton();
            btnPlay.setText("Play");
            btnPlay.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnPlayActionPerformed();
                }
            });
            barMain.add(btnPlay);

            JButton btnEdit = new JButton();
            btnEdit.setText("Edit");
            btnEdit.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnEditActionPerformed();
                }
            });
            barMain.add(btnEdit);

            JButton btnCreate = new JButton();
            btnCreate.setText("Create");
            btnCreate.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnCreateActionPerformed();
                }
            });
            barMain.add(btnCreate);
        }
        add(barMain, BorderLayout.NORTH);

        tabMain = new JTabbedPane();
        tabMain.setVisible(false);
        getContentPane().add(tabMain, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private void setupLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void btnPlayActionPerformed() {
        JFileChooser fcPuzzle = Common.findPuzzle();
        if (fcPuzzle.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File filePuzzle = fcPuzzle.getSelectedFile();

            PnlPlay pnlPlay = new PnlPlay();
            try {
                pnlPlay.loadPuzzle(filePuzzle);
                tabMain.addTab("play (" + filePuzzle.getName() + ")", pnlPlay);
                tabMain.setSelectedIndex(tabMain.getTabCount() - 1);

                if (!tabMain.isVisible()) {
                    tabMain.setVisible(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnEditActionPerformed() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void btnCreateActionPerformed() {
        PnlCreate pnlCreate = new PnlCreate();
        tabMain.addTab("Create", pnlCreate);
        tabMain.setSelectedIndex(tabMain.getTabCount() - 1);

        if (!tabMain.isVisible()) {
            tabMain.setVisible(true);
        }
    }


    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
    }
}
