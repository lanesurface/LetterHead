package net.lanesurface.letterhead;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;

public class ClientInterface {
  private JFrame window;
  private int width, height;

  private ClientInterface(
    int width,
    int height)
  {
    window = new JFrame("LetterHead Image Converter");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel();
    panel.setBorder(new TitledBorder("Controls"));
    panel.setPreferredSize(new Dimension(
      width,
      height));
    window.add(panel);

    String[] fonts = getSystemFonts();
    JComboBox box = new JComboBox<String>(fonts);
    panel.add(box);

    window.pack();
    window.setVisible(true);
  }

  private boolean isMonospace(Font font) {
    return true;
  }

  private String[] getSystemFonts() {
    Object[] fonts;

    fonts = Arrays.stream(
      GraphicsEnvironment
        .getLocalGraphicsEnvironment()
        .getAllFonts())
        .filter(this::isMonospace)
        .map(Font::getFamily)
        .toArray();

    return Arrays.copyOf(
      fonts,
      fonts.length,
      String[].class);
  }

  public static void main(String[] args) {
    ClientInterface ci = new ClientInterface(
      500,
      500);
  }
}
