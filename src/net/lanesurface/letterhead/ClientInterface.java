package net.lanesurface.letterhead;

import com.sun.prism.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ClientInterface {
  private static class NumericVerifier extends InputVerifier {
    /*
     * Ensure that the text entered into a JTextField contains only numeric
     * values, so that we can set the width and height of the output image
     * appropriately.
     */
    @Override
    public boolean verify(JComponent component) {
      JTextField field;
      String text;

      field = (JTextField)component;
      text = field.getText();

      return text.matches("[0-9]+");
    }
  }

  private class OpenHandler implements ActionListener {
    JFileChooser fc;
    int r;

    {
      fc = new JFileChooser();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      r = fc.showOpenDialog(window);
      inf = fc.getSelectedFile();
      ff.setText(inf.getName());
    }
  }

  private class SaveHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (inf == null) { // The file has not yet been specified.
        JOptionPane.showMessageDialog(
          window,
          "You must specify a file to convert!",
          "Invalid file name",
          JOptionPane.ERROR_MESSAGE);

        return;
      }
      createImage(inf);
    }
  }

  private JFrame window;
  private JTextField ff,
    wf,
    hf;
  private InputVerifier sv;
  private ActionListener oh, sh;
  private File inf;
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
    JButton fileButton;

    inputPanel = new JPanel();
    fileLabel = new JLabel("File:");
    ff = new JTextField(25);

    inputPanel.add(
      fileLabel,
      BorderLayout.PAGE_START);
    inputPanel.add(
      ff,
      BorderLayout.CENTER);

    fileButton = new JButton("Open");
    oh = new OpenHandler();
    fileButton.addActionListener(oh);
    inputPanel.add(
      fileButton,
      BorderLayout.PAGE_END);
    panel.add(inputPanel);

    String[] fonts = getSystemFonts();
    JComboBox box = new JComboBox<String>(fonts);
    panel.add(box);

    JPanel sp;
    JLabel xl;

    sp = new JPanel(new FlowLayout());
    xl = new JLabel("X");
    wf = new JTextField(
      "64",
      3);
    hf = new JTextField(
      "64",
      3);
    sv = new NumericVerifier();

    wf.setInputVerifier(sv);
    hf.setInputVerifier(sv);
    sp.add(wf);
    sp.add(xl);
    sp.add(hf);
    panel.add(sp);

    JPanel sbp;
    JButton sb;

    sbp = new JPanel();
    sb = new JButton("Save");
    sh = new SaveHandler();

    sb.addActionListener(sh);
    sbp.add(
      sb,
      BorderLayout.PAGE_END);
    panel.add(sbp);

    window.pack();
    window.setVisible(true);
  }

  private boolean isMonospace(Font font) {
    // TODO: Only display monospace font options.
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

  private int getValue(JTextField field) {
    return Integer.parseInt(field.getText());
  }

  private void createImage(File file) {
    ImageConverter converter;
    ImageConverter.AsciiFrame frame;
    RenderedImage img;
    File dest;

    converter = new ImageConverter();
    dest = new File("out.png"); // TODO: Get destination from client.
    iw = getValue(wf);
    ih = getValue(hf);

    Graphics g;
    FontMetrics fm;
    int fw, fh;
    g = window.getGraphics();
    g.setFont(new Font(
      "Consolas",
      Font.BOLD,
      12));
    fm = g.getFontMetrics();
    fw = fm.getMaxAdvance();
    fh = fm.getAscent()
      + fm.getDescent()
      + fm.getLeading();

    try {
      frame = converter.createFrame(
        ImageIO.read(file),
        iw,
        ih);
      img = frame.createImage(
        fw,
        fh);

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
