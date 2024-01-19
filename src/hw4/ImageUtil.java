package hw4;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import hw4.model.IImage;
import hw4.model.IPixel;
import hw4.model.ImageImpl;
import hw4.model.RGBPixel;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {
  private static FileWriter writer;

  /**
   * Read an image file in the PPM format and use it to instantiate an IImage.
   *
   * @param filepath the path leading to the file
   * @return the new IImage image as specified by the file
   * @throws IllegalStateException if the file cannot be found
   * @throws IllegalArgumentException if the file format of the filepath is invalid or if the
   *                                  filepath is null
   */
  public static IImage readImage(String filepath)
          throws IllegalStateException, IllegalArgumentException {
    if (filepath == null) {
      throw new IllegalArgumentException("The given filepath cannot be null!");
    }

    switch (getFileExtension(filepath)) {
      case "ppm":
        return readPPM(filepath);
      case "png":
      case "jpg":
      case "bmp":
        return readIOImage(filepath);
      default:
        throw new IllegalArgumentException("Invalid file format!");
    }
  }

  /**
   * Reads an image from the filesystem requiring the IOImage class as an IImage.
   *
   * @param filepath the path to the image
   * @return the loaded IImage
   * @throws IllegalStateException if there are any file reading errors
   * @throws IllegalArgumentException if the filepath is null
   */
  private static IImage readIOImage(String filepath) throws IllegalStateException,
          IllegalArgumentException {
    if (filepath == null) {
      throw new IllegalArgumentException("The given filepath cannot be null!");
    }

    BufferedImage img;
    try {
      img = ImageIO.read(new File(filepath));
    } catch (IOException e) {
      throw new IllegalStateException("There was an error reading from the file.");
    }

    int width = img.getWidth();
    int height = img.getHeight();
    List<List<IPixel>> pixels = new ArrayList<>();
    for (int row = 0; row < height; row += 1) {
      pixels.add(row, new ArrayList<>());
      for (int col = 0; col < width; col += 1) {
        Color rgb = new Color(img.getRGB(col, row), false);
        pixels.get(row).add(new RGBPixel(rgb.getRed(), rgb.getGreen(), rgb.getBlue()));
      }
    }

    return new ImageImpl(pixels);
  }

  /**
   * Loads a PPM file as a new IImage.
   *
   * @param filepath the path leading to the file
   * @return the new loaded PPM image
   * @throws IllegalStateException if there are any errors reading the file
   * @throws IllegalArgumentException if the filepath is null
   */
  private static IImage readPPM(String filepath)
          throws IllegalStateException, IllegalArgumentException {
    if (filepath == null) {
      throw new IllegalArgumentException("The given filepath cannot be null!");
    }

    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filepath));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File " + filepath + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = readInt(sc);
    int height = readInt(sc);
    int maxValue = readInt(sc);

    List<List<IPixel>> pixels = new ArrayList<>(0);
    for (int row = 0; row < height; row += 1) {
      pixels.add(row, new ArrayList<>(0));
      for (int col = 0; col < width; col += 1) {
        pixels.get(row).add(new RGBPixel(readInt(sc), readInt(sc), readInt(sc)));
      }
    }
    if (sc.hasNextInt()) {
      throw new IllegalStateException("Invalid PPM file! More pixels than dimensions specify!");
    }
    return new ImageImpl(maxValue, pixels);
  }

  /**
   * Helper method which scans for the next integer, if there is any.
   *
   * @param sc the scanner to use
   * @return the integer to scan
   * @throws IllegalStateException if the next token is not an int
   * @throws IllegalArgumentException if sc is null
   */
  private static int readInt(Scanner sc) throws IllegalStateException , IllegalArgumentException {
    if (sc == null) {
      throw new IllegalArgumentException("The given Scanner cannot be null!");
    }

    if (sc.hasNextInt()) {
      return sc.nextInt();
    } else if (sc.hasNext()) {
      throw new IllegalStateException("Integer expected!");
    } else {
      throw new IllegalStateException("Invalid PPM file! "
              + "Not enough pixels as specified by dimensions of image!");
    }
  }

  /**
   * Gets the file extension from the passed filename without the leading period.
   * Converts it to lowercase so that it is standardized, since the extension works
   * either way and can thus be inputted either way.
   *
   * @param filename the filename to pull the extension from
   * @return the file extension
   * @throws IllegalArgumentException if no file extension can be found or if filename is null
   */
  private static String getFileExtension(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("The given filename cannot be null!");
    }

    if (!filename.contains(".")) {
      throw new IllegalArgumentException("No extension found!");
    } else {
      return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }
  }

  /**
   * Saves the given image as a file at the given filepath.
   *
   * @param image the image to save
   * @param filepath the path where the file should be saved to
   * @throws IllegalStateException if there are any errors writing to the file
   * @throws IllegalArgumentException if the filetype of the given path is not supported or if
   *                                  image or filepath are null
   */
  public static void saveImage(IImage image, String filepath) throws IllegalStateException,
          IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The given IImage cannot be null!");
    }

    if (filepath == null) {
      throw new IllegalArgumentException("The given filepath cannot be null!");
    }

    switch (getFileExtension(filepath)) {
      case "ppm":
        savePPM(image, filepath);
        break;
      case "png":
      case "jpg":
      case "bmp":
        saveIOImage(image, filepath);
        break;
      default:
        throw new IllegalArgumentException("Invalid file format!");
    }

  }

  /**
   * Saves the given image as a file at the given filepath, if it is an image type supported by the
   * IOImage class.
   *
   * @param image the image to save
   * @param filepath the path where the file should be saved to
   * @throws IllegalStateException if there are any errors writing to the file
   * @throws IllegalArgumentException if the image or filepath are null
   */
  private static void saveIOImage(IImage image, String filepath) throws IllegalStateException,
          IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The given IImage cannot be null!");
    }

    if (filepath == null) {
      throw new IllegalArgumentException("The given filepath cannot be null!");
    }

    BufferedImage img = toJavaImage(image);

    File file = new File(filepath);
    try {
      ImageIO.write(img, getFileExtension(filepath), file);
    } catch (IOException e) {
      throw new IllegalStateException("There was an error writing to the file.");
    }
  }

  /**
   * Returns a BufferedImage that is converted from/based on the given IImage.
   *
   * @param image an IImage used to base the returned BufferedImage upon
   * @return a BufferedImage based on image
   * @throws IllegalArgumentException if image is null
   */
  public static BufferedImage toJavaImage(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The given IImage cannot be null!");
    }

    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    ColorModel model = img.getColorModel();
    for (int row = 0; row < height; row += 1) {
      for (int col = 0; col < width; col += 1) {
        IPixel pixel = image.getPixelAt(row, col);
        int[] colors = {pixel.getRed(), pixel.getGreen(), pixel.getBlue()};
        int rgb = model.getDataElement(colors, 0);
        img.setRGB(col, row, rgb);
      }
    }

    return img;
  }


  /**
   * Saves the given image as a PPM file at the given filepath.
   *
   * @param image the image to save
   * @param filepath the path where the file should be saved to
   * @throws IllegalStateException if there are any errors in writing to the file.
   * @throws IllegalArgumentException if the image or filepath are null
   */
  private static void savePPM(IImage image, String filepath)
          throws IllegalStateException, IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The given IImage cannot be null!");
    }

    if (filepath == null) {
      throw new IllegalArgumentException("The given filepath cannot be null!");
    }

    File file = new File(filepath);
    String sep = System.lineSeparator();
    try {
      writer = new FileWriter(filepath);
    } catch (IOException e) {
      throw new IllegalStateException("An error occurred in opening the file writer.");
    }
    writeLine("P3");
    writeLine(String.format("%d %d", image.getWidth(), image.getHeight()));
    writeLine(Integer.toString(image.getMaxChannelDepth()));
    for (int row = 0; row < image.getHeight(); row += 1) {
      for (int col = 0; col < image.getWidth(); col += 1) {
        IPixel p = image.getPixelAt(row, col);
        writeLine(Integer.toString(p.getRed()));
        writeLine(Integer.toString(p.getGreen()));
        writeLine(Integer.toString(p.getBlue()));
      }
    }
    try {
      writer.close();
    } catch (IOException e) {
      throw new IllegalStateException("An error occurred in closing the file writer.");
    }

  }

  /**
   * Helper method which writes a single line of content to the file specified by the FileWriter.
   *
   * @param input the input desired to be written to the file
   * @throws IllegalStateException if there are any errors writing to the file
   * @throws IllegalArgumentException if input is null
   */
  private static void writeLine(String input) throws IllegalStateException,
          IllegalArgumentException {
    if (input == null) {
      throw new IllegalArgumentException("The given input cannot be null!");
    }

    try {
      writer.write(input + System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("An error occurred writing to the file.");
    }
  }


}

