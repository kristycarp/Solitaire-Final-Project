//Kristy Carpenter, Computer Science III, String 2015, Section B (5th period)
//Final project--Solitaire
//
//This class contains all the information for a listener for the deal button. Whenever it senses that
//a point inside the deal button has been clicked, it calls the deal method in the Deck class.

import javax.swing.event.*;
import java.awt.event.*;

public class DealButtonListener extends MouseInputAdapter
{
   /**
     *the button that is being listened to
     */
   private Button b;
   
   /**
     *the deck to deal
     */
   private Deck d;
   
   /**
     *the constructor for a new DealButtonListener
     *
     *@param b - the button to listen to
     *@param d - the deck to deal out
     */
   public DealButtonListener(Button b, Deck d)
   {
      this.b = b;
      this.d = d;
   }
   
   /**
     *called whenever the screen is clicked. If the game has not been won, nothing is selected, and
     *the deal button has been clicked, the deal method of the deck is called
     *
     *@param event - the mouseevent
     */
   public void mouseClicked(MouseEvent event)
   {
      if (!CardListener.isWon() && !CardListener.somethingSelected() && b.isHit(event.getX(), event.getY()))
      {
         d.deal();
      }
   }
}