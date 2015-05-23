import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;

public class CompleteButtonListener extends MouseInputAdapter
{
   private Button b;
   private ArrayList<Card> cards;
   private Foundation[] foundations;
   
   public CompleteButtonListener(Button b, ArrayList<Card> allCards, Foundation[] f)
   {
      this.b = b;
      cards = allCards;
      foundations = f;
   }
   
   public void mouseClicked(MouseEvent event)
   {
      if (b.isHit(event.getX(), event.getY()) && !CardListener.isWon())
      {
         int cardsAdded;
         do
         {
            cardsAdded = 0;
            for (Card c : cards)
            {
               if (c.isTopAt(c.getX() + Card.CARD_WIDTH, c.getY() + Card.CARD_HEIGHT) && (c.getLocation().equals(Card.Location.DECK) || c.getLocation().equals(Card.Location.PILE)))
               {
                  //System.out.println(c.toString() + " is eligible");
                  for (Foundation f : foundations)
                  {
                     if (f.canAddCard(c))
                     {
                        if (c.getLocation().equals(Card.Location.DECK))
                        {
                           Deck deck = Solitaire.getDeck();
                           deck.removeTop();
                        }
                        else
                        {
                           Pile p = Solitaire.whichPileIsHit(c.getX());
                           p.removeLastCard();
                           p.flipLastCard();
                        }
                        f.addCard(c);
                        cardsAdded++;
                        break;
                     }
                  }
               }
            }
            Solitaire.drawScreen();
            CardListener.checkForWin();
         } while (cardsAdded != 0);
      }
   }
}