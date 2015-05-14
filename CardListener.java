import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class CardListener extends MouseInputAdapter
{
   public static boolean somethingSelected;
   private ArrayList<Card> cards;
   private Graphics g;
   
   public CardListener(ArrayList<Card> allCards, DrawingPanel p)
   {
      somethingSelected = false;
      cards = allCards;
      g = p.getGraphics();
   }
   
   public void mouseClicked(MouseEvent event)
   {
      //System.out.println("you clicked");
      for (Card c : cards)
      {
         if (c.isFaceUp() && c.isHit(event.getX(), event.getY()))
         {
            //System.out.println("you clicked " + c.toString());
            if (!c.isSelected())
            {
               if (somethingSelected)
               {
                  //check to see if cards can go on top of each other
               }
               else
               {
                  c.select();
                  somethingSelected = true;
               }
            }
            else
            {
               c.unselect();
               somethingSelected = false;
            }
            c.draw(g);
            break;
         }
         //if (c.isHit(event.getX(), event.getY()))
         //   System.out.println(c.fullToString());
      }
   }
}