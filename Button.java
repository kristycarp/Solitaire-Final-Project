//Kristy Carpenter, Computer Science III, String 2015, Section B (5th period)
//Final project--Solitaire
//
//This class contains all the information for a button. There are two different buttons implemented
//in the game (with one other being developed but not error-free): a deal button and a complete button.
//This class does not have any of their functionality; it only has the information for their apperance.

import java.awt.*;

public class Button extends Clickable
{
   /**
     *the width of the button
     */
   public static final int BUTTON_WIDTH = Card.CARD_WIDTH;
   
   /**
     *the height of the button
     */
   public static final int BUTTON_HEIGHT = 40;
   
   /**
     *the color of the button
     */
   private static final Color BUTTON_COLOR = Color.WHITE;
   
   /**
     *the text on top of the button
     */
   private String message;
   
   /**
     *the constructor for a new Button. sets all instance variables as necessary.
     *
     *@param x - the x coordinate of the top left corner
     *@param y - the y coordinate of the top left corner
     *@param text - the text on top of the button
     */
   public Button(int x, int y, String text)
   {
      super(x, y);
      message = text;  
   }
   
   /**
     *the default constructor. makes a button with no text that is in the top left corner of the panel
     */
   public Button()
   {
      this(0, 0, "");
   }
   
   /**
     *another constructor for the button. Makes a button in the top left corner with the given text
     *on top.
     *
     *@param text - the text to be put on top of the button
     */
   public Button(String text)
   {
      this(0, 0, text);
   }
   
   /**
     *given the graphics context for a panel, this method draws the button on that panel
     *
     *@param g - the graphics context for the panel onto which this button will be drawn
     */
   public void draw(Graphics g)
   {
      g.setColor(BUTTON_COLOR);
      g.fillRect(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
      g.setColor(Color.BLACK);
      g.drawRect(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
      g.drawString(message, x + BUTTON_HEIGHT / 2 - 10, y + BUTTON_HEIGHT / 2 + 5);
   }
   
   /**
     *a modified isHit method. uses the width and height of the button instead of the card. checks
     *to see if the given point is inside the button
     *
     *@param x - the x coordinate to be chekced
     *@param y - the y coordinate to be checked
     */
   public boolean isHit(int x, int y)
   {
      return (x >= this.x && x <= this.x + BUTTON_WIDTH && y >= this.y && y <= this.y + BUTTON_HEIGHT);
   }
}