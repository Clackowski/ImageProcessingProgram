package hw4.controller;

import hw4.view.IGUIView;

/**
 * The interface for the asynchronous controller, used for things like GUIs (where the controller
 * needs to be reactive instead of the engine).
 */
public interface IAsynchController {

  /**
   * Sets the implementing classes' view to the given IGUIView (if not null) and sets up the view's
   * reference to the respective Features.
   *
   * @param view the given IGUIView
   * @throws IllegalArgumentException if view is null
   */
  void setView(IGUIView view) throws IllegalArgumentException;
}
