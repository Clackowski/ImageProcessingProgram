package hw4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import hw4.imageoperations.BlueComponentOperation;
import hw4.imageoperations.BrightenOperation;
import hw4.imageoperations.GreenComponentOperation;
import hw4.imageoperations.HorizontalFlipOperation;
import hw4.imageoperations.IImageOperation;
import hw4.imageoperations.IntensityComponentOperation;
import hw4.imageoperations.LumaComponentOperation;
import hw4.imageoperations.RedComponentOperation;
import hw4.imageoperations.ValueComponentOperation;
import hw4.imageoperations.VerticalFlipOperation;
import hw4.ImageUtil;
import hw4.model.IImage;
import hw4.model.IModel;
import hw4.view.IView;

/**
 * Controller for the image manipulation program.
 */
public class ControllerImpl implements IController {

  private final IModel model;

  private final Readable readable;

  private final IView view;

  protected final Map<String, Function<Scanner, IImageOperation>> operationMap;

  private enum RunState {
    run,
    quit
  }

  ;

  private RunState state;

  /**
   * Instantiates the controller with the given model, input source, and view.
   *
   * @param model    the model to communicate to
   * @param readable the input source
   * @param view     the view to send messages to
   */
  public ControllerImpl(IModel model, Readable readable, IView view) {
    if (model == null) {
      throw new IllegalArgumentException("The given model cannot be null.");
    }

    if (readable == null) {
      throw new IllegalArgumentException("The given readable cannot be null.");
    }

    if (view == null) {
      throw new IllegalArgumentException("The given view cannot be null.");
    }

    this.model = model;
    this.readable = readable;
    this.view = view;
    this.operationMap = new HashMap<>();
    this.initMap();
    this.state = RunState.run;

  }

  /**
   * Helper which fills the command map with the right commands.
   */
  protected void initMap() {
    this.operationMap.put("brighten", scanner -> {
          if (scanner.hasNextInt()) {
            return new BrightenOperation(scanner.nextInt());
          } else {
            scanner.next();
            scanner.next();
            scanner.next();
            throw new IllegalArgumentException("Need to pass an integer with brighten!");
          }
        }
    );
    this.operationMap.put("red-component", scanner -> new RedComponentOperation());
    this.operationMap.put("green-component", scanner -> new GreenComponentOperation());
    this.operationMap.put("blue-component", scanner -> new BlueComponentOperation());
    this.operationMap.put("value-component", scanner -> new ValueComponentOperation());
    this.operationMap.put("intensity-component", scanner -> new IntensityComponentOperation());
    this.operationMap.put("luma-component", scanner -> new LumaComponentOperation());
    this.operationMap.put("vertical-flip", scanner -> new VerticalFlipOperation());
    this.operationMap.put("horizontal-flip", scanner -> new HorizontalFlipOperation());
  }


  /**
   * Runs the image manipulation program with the given inputs.
   */
  @Override
  public void run() {
    Scanner sc = new Scanner(readable);
    while (this.state.equals(RunState.run) && sc.hasNext()) {
      parseInput(sc, sc.next().toLowerCase());
    }
    //resetting in the event that run is called twice
    this.state = RunState.run;
  }

  /**
   * Parses the current token of input and performs the desired operations with the model and view,
   * using extra input from the scanner as needed.
   *
   * @param sc    the scanner over the input source
   * @param input the input token to parse
   */
  private void parseInput(Scanner sc, String input) {
    IImageOperation op;
    IImage img;
    switch (input) {
      case "q":
      case "quit":
        this.state = RunState.quit;
        return;
      case "load":
        String filename;
        String filepath;
        filepath = nextToken(sc);
        filename = nextToken(sc);
        try {
          img = ImageUtil.readImage(filepath);
          this.model.loadImage(img, filename);
        } catch (IllegalArgumentException | IllegalStateException e) {
          this.transmitMessage(e.getMessage());
        }
        break;
      case "save":
        filepath = nextToken(sc);
        filename = nextToken(sc);
        try {
          img = this.model.getImage(filename);
          ImageUtil.saveImage(img, filepath);
        } catch (IllegalArgumentException | IllegalStateException e) {
          this.transmitMessage(e.getMessage());
        }
        break;
      default:
        if (this.operationMap.containsKey(input)) {
          Function<Scanner, IImageOperation> operation = this.operationMap.get(input);
          try {
            op = operation.apply(sc);
          } catch (IllegalArgumentException e) {
            this.transmitMessage(e.getMessage());
            break;
          }
          try {
            img = this.model.getImage(this.nextToken(sc));
            IImage newImage = img.accept(op);
            this.model.loadImage(newImage, this.nextToken(sc));
          } catch (IllegalArgumentException e) {
            this.transmitMessage(e.getMessage());
          }

        } else {
          this.transmitMessage("Invalid input! Please enter a valid command.");
        }


    }
  }

  /**
   * Helper method for sending messages to the view which handles the IOException.
   *
   * @param message the message to send
   * @throws IllegalStateException if there is an error in transmitting
   */
  private void transmitMessage(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message + System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("An error occurred in transmitting to the view!");
    }
  }

  /**
   * Helper method for scanning in the next token from the scanner.
   *
   * @param sc the scanner to use
   * @return the next token
   * @throws IllegalStateException if there are no more tokens
   */
  private String nextToken(Scanner sc) throws IllegalStateException {
    if (sc.hasNext()) {
      return sc.next();
    } else {
      throw new IllegalStateException("No remaining input!");
    }
  }
}
