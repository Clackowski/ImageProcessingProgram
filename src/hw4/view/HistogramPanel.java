package hw4.view;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import hw4.histogram.IImageHistogram;

/**
 * A JPanel which displays the contents of an image histogram.
 */
public class HistogramPanel extends JPanel {

  private final IImageHistogram histogram;
  private final Color histColor;
  private final String label;

  /**
   * Make a histogram display with the given histogram.
   * @param histogram the histogram to display
   * @throws IllegalArgumentException if the histogram is null
   */
  public HistogramPanel(IImageHistogram histogram)
          throws IllegalArgumentException {
    if (histogram == null) {
      throw new IllegalArgumentException("Requires a non-null histogram!");
    }
    this.histogram = histogram;
    this.histColor = histogram.getColor();
    this.label = histogram.getLabel();
  }


  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);

    Graphics2D g = (Graphics2D) graphics;
    int dx = Math.max((int) (getWidth() * 0.8) / 256, 1);
    int max = this.histogram.getMaxValue();
    double heightScale = (getHeight() * 0.8) / max;

    int y = (int) (getHeight() * 0.1);
    int x = (int) (getWidth() * 0.1);
    int value;
    g.setColor(Color.BLACK);
    g.drawString(String.format(label + " Range (y): 0 to %d", this.histogram.getMaxValue()),
            x, (int) (getHeight() * 0.95));
    g.setColor(this.histColor);
    g.translate(0, getHeight());
    g.transform(new AffineTransform(new double[] {1, 0, 0, -1}));
    for (int i = 0; i < 256; i += 1) {
      value = this.histogram.getValueAt(i);
      //g.fillRect(x, y, 50, 50);
      int height = (int) (value * heightScale);
      g.fillRect(x, y, dx, height);
      x += dx;
    }


  }
}
