package hw4.imageoperations;

import hw4.model.IImage;
import hw4.model.IPixel;
import hw4.model.RGBPixel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MosaicOperation implements IImageOperation {

  private final int seeds;

  public MosaicOperation(int seeds) {
    this.seeds = seeds;
  }

  @Override
  public List<List<IPixel>> apply(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("The given image cannot be null.");
    }

    return mosaicOperation(image);
  }

  /**
   * MosaicOperation function.
   *
   * @param origImage original image.
   * @return list of list of pixels.
   */
  private List<List<IPixel>> mosaicOperation(IImage origImage) {
    Map<List<Integer>, List<List<Integer>>> map = this.generateSeeds(origImage);

    List<List<Integer>> keys = new ArrayList<>(map.keySet());

    for (int row = 0; row < origImage.getHeight(); row++) {
      for (int col = 0; col < origImage.getWidth(); col++) {
        List<Integer> point = new ArrayList<>();
        point.add(row);
        point.add(col);

        double min = Double.MAX_VALUE;
        List<Integer> closestSeed = new ArrayList<>();
        for (List<Integer> key : keys) {
          min = Double.min(min, this.dist(point, key));

          if (min == this.dist(point, key)) { //is minimum
            closestSeed = key;
          }
        }

        map.get(closestSeed).add(point);

      }
    }

    List<IPixel> l;

    List<List<IPixel>> returnPixels = new ArrayList<>(origImage.getWidth());
    for (int row = 0; row < origImage.getHeight(); row++) {
      l = new ArrayList<>(origImage.getWidth());
      for (int col = 0; col < origImage.getWidth(); col++) {
        l.add(new RGBPixel(0, 0, 0));
      }
      returnPixels.add(l);
    }

    for (List<Integer> key : keys) {
      IPixel clusteredPixel = averageInCluster(map.get(key), origImage);
      for (List<Integer> point : map.get(key)) {
        int x = point.get(0);
        int y = point.get(1);

        returnPixels.get(x).set(y, clusteredPixel); //IF FLIPPED THEN WRONG
      }
    }

    return returnPixels;
  }

  private IPixel averageInCluster(List<List<Integer>> points, IImage origImage) {
    int red = 0;
    int green = 0;
    int blue = 0;

    for (List<Integer> point : points) {
      red += origImage.getPixelAt(point.get(0), point.get(1)).getRed();
      green += origImage.getPixelAt(point.get(0), point.get(1)).getGreen();
      blue += origImage.getPixelAt(point.get(0), point.get(1)).getBlue();
    }
    IPixel pixel = new RGBPixel(red / points.size(), green / points.size(), blue / points.size());

    return pixel;
  }

  private Map<List<Integer>, List<List<Integer>>> generateSeeds(IImage image) {
    Map<List<Integer>, List<List<Integer>>> map = new HashMap<>();

    while (map.size() < this.seeds) {
      int seedx = (int) (Math.random() * (image.getHeight() + 1));
      int seedy = (int) (Math.random() * (image.getWidth() + 1));

      List<Integer> key = new ArrayList<>();
      key.add(seedx);
      key.add(seedy);

      map.put(key, new ArrayList<>());
    }

    return map;
  }

  private double dist(List<Integer> point, List<Integer> seed) {
    return Math.abs(Math.sqrt(
        Math.pow(seed.get(0) - point.get(0), 2) + Math.pow(seed.get(1) - point.get(1), 2)));
  }
}


