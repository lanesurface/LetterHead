package net.lanesurface.letterhead;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

public class ImageConverter {
  private static class ColoredChar {
    private char chr;
    private Color color;

    ColoredChar(
      char chr,
      Color color)
    {
      this.chr = chr;
      this.color = color;
    }

    public char getChar() {
      return chr;
    }

    public Color getColor() {
      return color;
    }
  }

  private class AsciiFrame {
    private int width,
      height,
      fw,
      fh;
    private ColoredChar frm[][];
    private boolean ready;

    AsciiFrame(
      int width,
      int height)
    {
      this.width = width;
      this.height = height;
      frm = new ColoredChar[width][height];
    }

    void renderFrame(Graphics graphics) {
      
    }

    public RenderedImage createImage() {
      if (!ready) return null;

      BufferedImage image;
      Graphics igr;

      fw = fh = 0; // TODO: Initialize these values.
      image = new BufferedImage(
        width*fw,
        height*fh,
        BufferedImage.TYPE_INT_RGB);
      igr = image.createGraphics();
      renderFrame(igr);
      igr.dispose();

      return image;
    }

    void pushChar(
      char chr,
      Color color,
      int x,
      int y)
    {
      frm[x][y] = new ColoredChar(
        chr,
        color);
    }

    /*
     * All characters have been pushed onto the frame; we are ready to render.
     */
    void setReady() {
      ready = true;
    }
  }

  private Font font;
  private Charset charset;

  void setFont(Font font) {
    this.font = font;
  }

  void setCharset(Charset charset) {
    this.charset = charset;
  }

  public AsciiFrame createFrame(
    Image image,
    int width,
    int height)
  {
    AsciiFrame frame = new AsciiFrame(
      width,
      height);
    getChars(
      image,
      frame);

    return frame;
  }

  private void getChars(
    Image image,
    AsciiFrame frame)
  {
    BufferedImage bi;
    Graphics graphics;
    double range;
    int width,
      height,
      rgb,
      lum,
      index;
    char out;
    char chars[];

    width = frame.width;
    height = frame.height;
    image = image.getScaledInstance(
      width,
      height,
      Image.SCALE_SMOOTH);
    bi = new BufferedImage(
      width,
      height,
      BufferedImage.TYPE_INT_RGB);
    graphics = bi.createGraphics();
    graphics.drawImage(
      image,
      0,
      0,
      null);
    graphics.dispose();

    chars = Charsets.getCharset(charset);
    range = 255.d / chars.length;
    for (int y = 0; y < frame.height; y++) {
      for (int x = 0; x < frame.width; x++) {
        rgb = bi.getRGB(
          x,
          y);
        lum = ((rgb >> 16 & 0xFF)
             + (rgb >> 8 & 0xFF)
             + (rgb & 0xFF)) / 3;
        index = (int)Math.min(
          Math.round(lum / range),
          chars.length-1);
        out = chars[chars.length-index-1];

        frame.pushChar(
          out,
          new Color(rgb),
          x,
          y);
      }
    }

    frame.setReady();
  }
}
