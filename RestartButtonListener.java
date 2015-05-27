import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;

public class RestartButtonListener extends MouseInputAdapter
{
   private Button b;
   private ArrayList<Card> allCards;
   
   public RestartButtonListener(Button b, ArrayList<Card> allCards)
   {
      this.b = b;
      this.allCards = allCards;
   }
   
   public void mouseClicked(MouseEvent event)
   {
      if (b.isHit(event.getX(), event.getY()))
      {
         for (Card c : allCards)
         {
            c.setLocation(Card.Location.PILE);
         }
         Solitaire.setup();
      }
   }
}