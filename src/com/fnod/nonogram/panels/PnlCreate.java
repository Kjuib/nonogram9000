package com.fnod.nonogram.panels;

import com.fnod.nonogram.Common;
import com.fnod.nonogram.buttons.ButtonCreate;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: hbillings
 * Date: 2/8/13
 * Time: 7:40 PM
 */
public class PnlCreate extends JPanel {
    private JPanel pnlImage;
    private JTextField txtWidth;
    private JTextField txtHeight;
    private BufferedImage image;

    public PnlCreate() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));

        txtWidth = new JTextField();
        txtWidth.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtWidth.setSelectionStart(0);
                txtWidth.setSelectionEnd(txtWidth.getText().length());
            }
        });
        txtWidth.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                if (str == null) {
                    return;
                }

                try {
                    Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    return;
                }

                if ((getLength() + str.length()) <= 2) {
                    super.insertString(offset, str, a);
                }
            }
        });
        txtWidth.setText(10 + "");
        Dimension sizeWidth = new Dimension(22, 10);
        txtWidth.setPreferredSize(sizeWidth);
        txtWidth.setSize(sizeWidth);
        txtWidth.setMinimumSize(sizeWidth);
        txtWidth.setMaximumSize(sizeWidth);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 0, 0);
        add(txtWidth, gbc);

        JLabel lblX = new JLabel();
        lblX.setText("x");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 0, 0);
        add(lblX, gbc);

        txtHeight = new JTextField();
        txtHeight.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtHeight.setSelectionStart(0);
                txtHeight.setSelectionEnd(txtHeight.getText().length());
            }
        });
        txtHeight.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                if (str == null) {
                    return;
                }

                try {
                    Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    return;
                }

                if ((getLength() + str.length()) <= 2) {
                    super.insertString(offset, str, a);
                }
            }
        });
        txtHeight.setText(10 + "");
        Dimension sizeHeight = new Dimension(22, 10);
        txtHeight.setPreferredSize(sizeHeight);
        txtHeight.setSize(sizeHeight);
        txtHeight.setMinimumSize(sizeHeight);
        txtHeight.setMaximumSize(sizeHeight);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 0, 0);
        add(txtHeight, gbc);

        JButton btnLoad = new JButton();
        btnLoad.setText("Load Image");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 0, 0);
        btnLoad.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLoadActionPerformed();
            }
        });
        add(btnLoad, gbc);

        JButton btnSave = new JButton();
        btnSave.setText("Save Puzzle");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 0, 0);
        btnSave.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSaveActionPerformed();
            }
        });
        add(btnSave, gbc);

        pnlImage = new JPanel();
        pnlImage.setBorder(new LineBorder(Color.BLACK));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 10;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 0, 0, 0);
        add(pnlImage, gbc);
    }

    private void btnSaveActionPerformed() {
        // TODO validate the puzzle
        if (pnlImage.getComponentCount() < 1) {
            return;
        }

        JFileChooser fcSave = Common.findPuzzle();
        if (fcSave.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        boolean selected = true;
        StringBuilder s = new StringBuilder();

        GridLayout layout = (GridLayout) pnlImage.getLayout();
        s.append(layout.getColumns());
        s.append(Common.DELIMITER);
        s.append(layout.getRows());
        s.append(Common.DELIMITER);

        int count = 0;
        for (Component c : pnlImage.getComponents()) {
            if (c instanceof ButtonCreate) {
                ButtonCreate btn = (ButtonCreate) c;
                if (selected == btn.isSelected()) {
                    count++;
                } else {
                    s.append(count);
                    s.append(Common.DELIMITER);
                    count = 1;
                    selected = btn.isSelected();
                }
            }
        }
        s.append(count);

        try {
            File fileSave = fcSave.getSelectedFile();
            String name = fileSave.getName();
            if (!name.endsWith(Common.FILE_EXT)) {
                fileSave = new File(fileSave.getAbsolutePath() + Common.FILE_EXT);
            }
            FileWriter fstream = new FileWriter(fileSave);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(s.toString());
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void btnLoadActionPerformed() {
        JFileChooser fcOpen = Common.findImage();
        if (fcOpen.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fcOpen.getSelectedFile();

            int width = 10;
            try {
                width = Integer.parseInt(txtWidth.getText());
            } catch (NumberFormatException e) {
                // Ignore
            }

            int height = 10;
            try {
                height = Integer.parseInt(txtHeight.getText());
            } catch (NumberFormatException e) {
                // Ignore
            }

            image = null;

            try {
                BufferedImage img = ImageIO.read(file);
                image = getScaledImage(img, width, height);
            } catch (IOException e) {
                // Ignore
            }
            updateImage();
        }
    }

    private void updateImage() {
        pnlImage.removeAll();
        if (image == null) {
            return;
        }

        int width = image.getWidth();
        int height = image.getHeight();
        pnlImage.setLayout(new GridLayout(height, width, 1, 1));

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                ButtonCreate btn = new ButtonCreate();
                Color color = new Color(image.getRGB(j, i));
                btn.setSelected(getBrightness(color) < 130);
                pnlImage.add(btn);
            }
        }

        pnlImage.revalidate();
    }

    protected static boolean isWidthBigger(int imageWidth, int imageHeight, int labelWidth, int labelHeight) {
        double imageRatio = imageWidth * 1.0 / imageHeight;
        double labelRatio = labelWidth * 1.0 / labelHeight;
        if (imageRatio >= 1 && labelRatio >= 1) {
            return imageRatio >= labelRatio;
        } else if (imageRatio <= 1 && labelRatio <= 1) {
            return imageRatio >= labelRatio;
        } else {
            return imageRatio >= labelRatio;
        }
    }

    private static int getBrightness(Color c) {
        return (int) Math.sqrt(
                c.getRed() * c.getRed() * .241 +
                        c.getGreen() * c.getGreen() * .691 +
                        c.getBlue() * c.getBlue() * .068
        );
    }

    private BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
        if (image == null) {
            return null;
        }

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double) width / imageWidth;
        double scaleY = (double) height / imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

        return bilinearScaleOp.filter(image, new BufferedImage(width, height, image.getType()));
    }
}
