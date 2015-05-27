//Kristy Carpenter, Computer Science III, String 2015, Section B (5th period)
//Final project--Solitaire
//
//This abstract class is the superclass to any object that can be clicked and drawn. It contains an
//isHit method, which checks to see if a Card-shaped object contains a given point. It also contains
//an abstract draw method, which is implemented by all classes that extend Clickable. This draw method
//allows the Solitaire class to more efficiently draw everything on the screen.

import java.awt.*;

public abstract class Clickable
{
   /**
     *the x coordinate of the top left corner of the object
     */
   protected int x;
   
   /**
     *the y coordinate of the top left corner of the object
     */
   protected int y;
   
   /**
     *constructor for a new Clickable
     *
     *@param x - the top left x coordinate of the Clickable
     *@param y - the top left y coordinate of the Clickable
     */
   public Clickable(int x, int y)
   {
      this.x = x;
      this.y = y;
   }
   
   /**
     *default constructor for a new Clickable. Puts the object in the top left corner of the panel
     */
   public Clickable()
   {
      this(0, 0);
   }
   
   /**
     *takes a given point and checks to see whether it is inside the bounds of this object. Since
     *all objects that extend Clickable have the same dimensions of a card whether or not they are a
     *Card, this particular method can be used for all of them
     *
     *@param x - the x coordinate of the point to be checked
     *@param y - the y coordinate of the point to be checked
     *@return true if the point is inside the bounds of the object, false if not
     */
   public boolean isHit(int x, int y)
   {
      return (x >= this.x && x <= this.x + Card.CARD_WIDTH && y >= this.y && y <= this.y + Card.CARD_HEIGHT);
   }
   
   /**
     *draws the clickable in whatever specific way the object dictates
     *
     *@param g - the graphics context for the drawingpanel onto which the object will be drawn
     */
   public abstract void draw(Graphics g);
}