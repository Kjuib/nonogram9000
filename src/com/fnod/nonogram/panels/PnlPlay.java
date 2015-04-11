package com.fnod.nonogram.panels;

import com.fnod.nonogram.Common;
import com.fnod.nonogram.buttons.ButtonPlay;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hbillings
 * Date: 2/14/13
 * Time: 6:33 PM
 */
public class PnlPlay extends JPanel {
    private final static Color CLR_CLUE_HIGHLIGHT = Color.LIGHT_GRAY;

    private JPanel pnlImage;
    private JPanel pnlLeft;
    private JPanel pnlTop;

    public PnlPlay() {
        initComponents();

    }

    public void loadPuzzle(File filePuzzle) throws Exception {
        if (!filePuzzle.getName().endsWith(Common.FILE_EXT)) {
            throw new Exception("Unknown File Type.");
        }

        String puzzle = readFile(filePuzzle);
        String[] pieces = puzzle.split(Common.DELIMITER);
        if (pieces.length < 3) {
            throw new Exception("Malformed Nonogram File.");
        }

        try {
            int width = Integer.parseInt(pieces[0]);
            int height = Integer.parseInt(pieces[1]);

            pnlImage.setLayout(new GridLayout(height, width, 1, 1));
            int count = 0;
            boolean selected = true;
            for (int i = 2; i < pieces.length; i++) {
                int piece = Integer.parseInt(pieces[i]);
                for (int j = 0; j < piece; j++) {
                    count++;
                    ButtonPlay btn = new ButtonPlay();
                    btn.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            highlightClues(e, true);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            highlightClues(e, false);
                        }
                    });
                    btn.setSelected(selected);
                    if (count % width == 0) {
                        btn.setPosX(width);
                        btn.setPosY((count / width));
                    } else {
                        btn.setPosX(count % width);
                        btn.setPosY((count / width) + 1);
                    }
                    pnlImage.add(btn);
                }
                selected = !selected;
            }

            if (width * height != count) {
                throw new Exception("Malformed Nonogram File.");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Unable to read Nonogram file.");
        }

        loadClues();
    }

    private void loadClues() throws Exception {
        Map<Integer, List<Integer>> cluesLeft = new HashMap<>();
        Map<Integer, List<Integer>> cluesTop = new HashMap<>();

        for (Component c : pnlImage.getComponents()) {
            if (!(c instanceof ButtonPlay)) {
                throw new Exception("Unknown Component");
            }

            ButtonPlay btn = (ButtonPlay) c;
            List<Integer> listLeft = cluesLeft.get(btn.getPosY());
            if (listLeft == null) {
                listLeft = new ArrayList<>();
                cluesLeft.put(btn.getPosY(), listLeft);
            }
            List<Integer> listTop = cluesTop.get(btn.getPosX());
            if (listTop == null) {
                listTop = new ArrayList<>();
                cluesTop.put(btn.getPosX(), listTop);
            }

            int indexLeft = listLeft.size() - 1;
            if (indexLeft < 0) {
                indexLeft = 0;
                listLeft.add(0);
            }
            int clueLeft = listLeft.get(indexLeft);
            listLeft.remove(indexLeft);

            int indexTop = listTop.size() - 1;
            if (indexTop < 0) {
                indexTop = 0;
                listTop.add(0);
            }
            int clueTop = listTop.get(indexTop);
            listTop.remove(indexTop);

            if (btn.isSelected()) {
                clueLeft++;
                listLeft.add(clueLeft);
                clueTop++;
                listTop.add(clueTop);
            } else {
                if (clueLeft > 0) {
                    listLeft.add(clueLeft);
                }
                listLeft.add(0);

                if (clueTop > 0) {
                    listTop.add(clueTop);
                }
                listTop.add(0);
            }
        }

        printCluesLeft(cluesLeft);
        printCluesTop(cluesTop);
    }

    private void printCluesLeft(Map<Integer, List<Integer>> clues) {
        pnlLeft.setLayout(new GridLayout(clues.size(), 1));

        for (int i = 1; i <= clues.size(); i++) {
            List<Integer> list = clues.get(i);
            JPanel pnlClues = new JPanel();
            pnlClues.setLayout(new GridBagLayout());
            pnlClues.setOpaque(true);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            pnlClues.add(new JLabel(), gbc);

            for (int j = 0; j < list.size(); j++) {
                int clue = list.get(j);
                if (j == list.size() - 1 && clue == 0 && pnlClues.getComponentCount() > 1) {
                    continue;
                }
                JLabel lblClue = new JLabel();
                lblClue.setVerticalAlignment(SwingConstants.CENTER);
                lblClue.setHorizontalAlignment(SwingConstants.RIGHT);
                lblClue.setText(clue + "");
                gbc = new GridBagConstraints();
                gbc.gridx = pnlClues.getComponentCount();
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 2, 0, 1);
                pnlClues.add(lblClue, gbc);
            }
            pnlLeft.add(pnlClues);
        }
    }

    private void printCluesTop(Map<Integer, List<Integer>> clues) {
        pnlTop.setLayout(new GridLayout(1, clues.size()));

        for (int i = 1; i <= clues.size(); i++) {
            List<Integer> list = clues.get(i);
            JPanel pnlClues = new JPanel();
            pnlClues.setLayout(new GridBagLayout());
            pnlClues.setOpaque(true);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weighty = 1;
            pnlClues.add(new JLabel(), gbc);

            for (int j = 0; j < list.size(); j++) {
                int clue = list.get(j);
                if (j == list.size() - 1 && clue == 0 && pnlClues.getComponentCount() > 1) {
                    continue;
                }
                JLabel lblClue = new JLabel();
                lblClue.setVerticalAlignment(SwingConstants.BOTTOM);
                lblClue.setHorizontalAlignment(SwingConstants.CENTER);
                lblClue.setText(clue + "");
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = pnlClues.getComponentCount();
                gbc.insets = new Insets(0, 0, 1, 0);
                pnlClues.add(lblClue, gbc);
            }
            pnlTop.add(pnlClues);
        }
    }

    private static String readFile(File file) throws IOException {
        try (FileInputStream stream = new FileInputStream(file)) {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            // Instead of using default, pass in a decoder.
            return Charset.defaultCharset().decode(bb).toString();
        }
    }

    private void btnCheckAction() {
        for (Component c : pnlImage.getComponents()) {
            if (c instanceof ButtonPlay) {
                ButtonPlay btn = (ButtonPlay) c;
                if (!btn.test()) {
                    JOptionPane.showMessageDialog(this, "Some tiles are not properly set.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(this, "You Win!", "Winner", JOptionPane.INFORMATION_MESSAGE);
    }

    private void highlightClues(MouseEvent e, boolean show) {
        if (e.getSource() instanceof ButtonPlay) {
            ButtonPlay btn = (ButtonPlay) e.getSource();

            Component clueTop = pnlTop.getComponent(btn.getPosX() - 1);
            if (clueTop != null && clueTop instanceof JPanel) {
                if (show) {
                    clueTop.setBackground(CLR_CLUE_HIGHLIGHT);
                } else {
                    clueTop.setBackground(null);
                }
            }

            Component clueLeft = pnlLeft.getComponent(btn.getPosY() - 1);
            if (clueLeft != null && clueLeft instanceof JPanel) {
                if (show) {
                    clueLeft.setBackground(CLR_CLUE_HIGHLIGHT);
                } else {
                    clueLeft.setBackground(null);
                }
            }
        }
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc;

        JButton btnCheck = new JButton();
        btnCheck.setText("C");
        btnCheck.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCheckAction();
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(btnCheck, gbc);

        pnlLeft = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(1, 1, 1, 1);
        add(pnlLeft, gbc);

        pnlTop = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(1, 1, 1, 1);
        add(pnlTop, gbc);

        pnlImage = new JPanel();
        pnlImage.setBorder(new LineBorder(Color.BLACK));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(pnlImage, gbc);
    }
}
