import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class CardListener extends MouseInputAdapter
{
   private static boolean somethingSelected;
   private ArrayList<Card> cards;
   private Graphics g;
   private static Card selectedCard;
   private Foundation[] foundations;
   private Pile[] piles;
   private static boolean multipleSelected;
   
   public CardListener(ArrayList<Card> allCards, DrawingPanel p, Foundation[] foundationArray, Pile[] pileArray)
   {
      somethingSelected = false;
      cards = allCards;
      g = p.getGraphics();
      foundations = foundationArray;
      piles = pileArray;
      multipleSelected = false;
   }
   
   public void mouseClicked(MouseEvent event)
   {
      //System.out.println("you clicked");
      //Collections.sort(cards); 
      for (Card c : cards)
      {
         if (c.isFaceUp() && c.isTopAt(event.getX(), event.getY()))
         {
            //System.out.println("you clicked " + c.toString());
            if (!c.isSelected())
            {
               if (somethingSelected)
               {
                  if (c.getLocation().equals(Card.Location.PILE) && selectedCard.getLocation().equals(Card.Location.PILE) && !multipleSelected)
                  {
                     //System.out.println(selectedCard.toString() + " has been selected first");
                     Pile pileToLoseCard = Solitaire.whichPileIsHit(selectedCard.getX());
                     //System.out.println("The pile losing a card has x = " + pileToLoseCard.getX());
                     //System.out.println(selectedCard.fullToString());
                     Pile pileToGainCard = Solitaire.whichPileIsHit(event.getX());
                     //System.out.println("The pile gaining a card has x = " + pileToGainCard.getX());
                     //System.out.println(selectedCard.fullToString());
                     if (pileToGainCard.canAddCard(selectedCard) && pileToLoseCard != null)
                     {
                        pileToLoseCard.removeLastCard();
                        //System.out.println("The pile losing a card has x = " + pileToLoseCard.getX());
                        //System.out.println(selectedCard.fullToString());
                        pileToGainCard.addCard(selectedCard);
                        //System.out.println("The pile losing a card has x = " + pileToLoseCard.getX());
                        //System.out.println("before flipping last card: " + selectedCard.fullToString());
                        pileToLoseCard.flipLastCard();
                        //System.out.println("The pile losing a card has x = " + pileToLoseCard.getX());
                        //System.out.println("after flipping last card: " + selectedCard.fullToString());
                        somethingSelected = false;
                        selectedCard.unselect();
                        selectedCard = null;
                        Solitaire.drawScreen();
                        //selectedCard.draw(g);
                        //c.draw(g);
                        //System.out.println("I recognize that the right things have happened. i tried.");
                     }
                  }
                  else if (c.getLocation().equals(Card.Location.PILE) && selectedCard.getLocation().equals(Card.Location.FOUNDATION) && !multipleSelected)
                  {
                     Foundation foundationToLoseCard = Solitaire.whichFoundationIsHit(selectedCard.getY());
                     Pile pileToGainCard = Solitaire.whichPileIsHit(event.getX());
                     if (pileToGainCard.canAddCard(selectedCard) && foundationToLoseCard != null)
                     {
                        foundationToLoseCard.removeCard();
                        pileToGainCard.addCard(selectedCard);
                        somethingSelected = false;
                        selectedCard.unselect();
                        selectedCard = null;
                        Solitaire.drawScreen();
                     }
                  }
                  else if (c.getLocation().equals(Card.Location.DECK))
                  {
                     //???????
                  }
                  else if (multipleSelected && c.getLocation().equals(Card.Location.PILE))
                  {
                     Pile pileToLoseCards = Solitaire.whichPileIsHit(selectedCard.getX());
                     Pile pileToGainCards = Solitaire.whichPileIsHit(event.getX());
                     if (pileToGainCards.canAddCard(selectedCard) && pileToLoseCards != null)
                     {
                        ArrayList<Card> losingPileSeeable = pileToLoseCards.getSeeable();
                        int index = losingPileSeeable.indexOf(selectedCard);
                        ArrayList<Card> losingPileSeeableImmutable = (ArrayList<Card>) losingPileSeeable.clone();
                        for (int cc = index; cc < losingPileSeeableImmutable.size(); cc++)
                        {
                           Card movingCard = losingPileSeeableImmutable.get(cc);
                           movingCard.unselect();
                           pileToLoseCards.removeLastCard();
                           pileToGainCards.addCard(movingCard);
                        }
                        somethingSelected = false;
                        multipleSelected = false;
                        pileToLoseCards.flipLastCard(); //only will do something if necessary (hopefully)
                        selectedCard = null;
                        Solitaire.drawScreen();
                     }
                  }
               }
               else
               {
                  if (c.isTopAt(c.getX(), c.getY() + Card.CARD_HEIGHT)) //top of pile or on foundation
                  {
                     c.select();
                     somethingSelected = true;
                     selectedCard = c;
                     c.draw(g);
                     //System.out.println(c.fullToString() + "asdf");
                  }
                  else //midstack of pile
                  {
                     somethingSelected = true;
                     multipleSelected = true;
                     selectedCard = c;
                     Pile p = Solitaire.whichPileIsHit(c.getX());
                     ArrayList<Card> allSeeable = p.getSeeable();
                     int index = allSeeable.indexOf(c);
                     for (int aa = index; aa < allSeeable.size(); aa++)
                     {
                        Card cardInStack = allSeeable.get(aa);
                        cardInStack.select();
                        cardInStack.draw(g);
                     }
                  }
               }
            }
            else
            {
               if (!multipleSelected)
               {
                  c.unselect();
                  somethingSelected = false;
                  selectedCard = null;
                  c.draw(g);
                  //System.out.println(c.fullToString() + "fdsa");
               }
               else //multiple are selected
               {
                  somethingSelected = false;
                  multipleSelected = false;
                  Pile p = Solitaire.whichPileIsHit(selectedCard.getX());
                  ArrayList<Card> allSeeable = p.getSeeable();
                  int index = allSeeable.indexOf(selectedCard);
                  for (int bb = index; bb < allSeeable.size(); bb++)
                  {
                     Card cardInStack = allSeeable.get(bb);
                     cardInStack.unselect();
                     cardInStack.draw(g);
                  }
                  selectedCard = null;
               }
            }
            break;
         }
         //if (c.isHit(event.getX(), event.getY()))
         //   System.out.println(c.fullToString());
      }
      for (Foundation f : foundations)
      {
         if (f.isHit(event.getX(), event.getY()) && somethingSelected && f.canAddCard(selectedCard) && !multipleSelected)
         {
            if (selectedCard.getLocation().equals(Card.Location.PILE))
            {
               Pile pileToLoseCard = Solitaire.whichPileIsHit(selectedCard.getX());
               pileToLoseCard.removeLastCard();
               f.addCard(selectedCard);
               pileToLoseCard.flipLastCard();
            }
            else if (selectedCard.getLocation().equals(Card.Location.FOUNDATION))
            {
               Foundation foundationToLoseCard = Solitaire.whichFoundationIsHit(selectedCard.getY());
               foundationToLoseCard.removeCard();
               f.addCard(selectedCard);
            }
            //if you're adding an else case for the deck make sure this stuff still applies
            somethingSelected = false;
            selectedCard.unselect();
            selectedCard = null;
            Solitaire.drawScreen();
         }
      }
      for (Pile p : piles) //clicking on empty pile, adding a king
      {
         if (p.isEmpty() && p.isHit(event.getX(), event.getY()) && somethingSelected && p.canAddCard(selectedCard))
         {
            if (multipleSelected)
            {
               Pile pileToLoseCards = Solitaire.whichPileIsHit(selectedCard.getX());
               ArrayList<Card> losingPileSeeable = pileToLoseCards.getSeeable();
               int index = losingPileSeeable.indexOf(selectedCard);
               ArrayList<Card> losingPileSeeableImmutable = (ArrayList<Card>) losingPileSeeable.clone();
               for (int cc = index; cc < losingPileSeeableImmutable.size(); cc++)
               {
                  Card movingCard = losingPileSeeableImmutable.get(cc);
                  movingCard.unselect();
                  pileToLoseCards.removeLastCard();
                  p.addCard(movingCard);
               }
               multipleSelected = false;
               pileToLoseCards.flipLastCard();
            }
            else
            {
               Pile pileToLoseCard = Solitaire.whichPileIsHit(selectedCard.getX());
               pileToLoseCard.removeLastCard();
               p.addCard(selectedCard);
               selectedCard.unselect();
               pileToLoseCard.flipLastCard();
            }
            somethingSelected = false;
            selectedCard = null;
            Solitaire.drawScreen();
         }
      }  
   }
}