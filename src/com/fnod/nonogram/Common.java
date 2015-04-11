package com.fnod.nonogram;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: hbillings
 * Date: 2/12/13
 * Time: 7:11 PM
 */
public class Common {
    public final static String FILE_EXT = ".non";

    private static JFileChooser fcImage;
    private static JFileChooser fcPuzzle;

    public static final String DELIMITER = ",";

    public static JFileChooser findImage() {
        if (fcImage == null) {
            fcImage = new JFileChooser();
            fcImage.setAcceptAllFileFilterUsed(false);
            fcImage.setMultiSelectionEnabled(false);
            fcImage.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory() || file.getAbsolutePath().toLowerCase().endsWith(".jpeg") || file.getAbsolutePath().toLowerCase().endsWith(".jpg") || file.getAbsolutePath().toLowerCase().endsWith(".gif") || file.getAbsolutePath().toLowerCase().endsWith("png");
                }

                @Override
                public String getDescription() {
                    return "Images (.jpg, .gif, .png)";
                }
            });
        }
        return fcImage;
    }

    public static JFileChooser findPuzzle() {
        if (fcPuzzle == null) {
            fcPuzzle = new JFileChooser();
            fcPuzzle.setAcceptAllFileFilterUsed(false);
            fcPuzzle.setMultiSelectionEnabled(false);
            fcPuzzle.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory() || file.getAbsolutePath().toLowerCase().endsWith(".non");
                }

                @Override
                public String getDescription() {
                    return "Nonogram (" + FILE_EXT + ")";
                }
            });
        }
        return fcPuzzle;
    }


}
