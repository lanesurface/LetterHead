package net.lanesurface.letterhead;

import com.sun.prism.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ClientInterface {
  private JFrame window;
  private int width,
    height,
    iw,
    ih;

  private ClientInterface(
    int width,
    int height)
  {
    window = new JFrame("LetterHead Image Converter");
    window.setResizable(false);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel();
    panel.setBorder(new TitledBorder("Controls"));
    panel.setLayout(new BoxLayout(
      panel,
      BoxLayout.Y_AXIS));
//    panel.setPreferredSize(new Dimension(
//      width,
//      height));
    window.add(panel);

    JPanel inputPanel;
    JLabel fileLabel;
    JTextField fileField;
    JButton fileButton;

    inputPanel = new JPanel();
    fileLabel = new JLabel("File:");
    fileField = new JTextField(25);

    inputPanel.add(
      fileLabel,
      BorderLayout.PAGE_START);
    inputPanel.add(
      fileField,
      BorderLayout.CENTER);

    fileButton = new JButton("Open");
    fileButton.addActionListener(e -> {
      JFileChooser chooser;
      int r;
      chooser = new JFileChooser();
      r = chooser.showOpenDialog(window);

      if (r == JFileChooser.APPROVE_OPTION)
        createImage(chooser.getSelectedFile());
    });
    inputPanel.add(
      fileButton,
      BorderLayout.PAGE_END);
    panel.add(inputPanel);

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

  private void createImage(File file) {
    ImageConverter converter;
    ImageConverter.AsciiFrame frame;
    RenderedImage img;
    File dest;

    converter = new ImageConverter();
    dest = null; // TODO: Get destination from client.

    try {
      frame = converter.createFrame(
        ImageIO.read(file),
        iw,
        ih);
      img = frame.createImage(
        0,
        0);

      ImageIO.write(
        img,
        "png",
        dest);
    } catch (IOException ie) { return; }
  }

  public static void main(String[] args) {
    ClientInterface ci = new ClientInterface(
      500,
      500);
  }
}
