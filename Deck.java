import java.util.*;
import java.awt.*;

public class Deck
{
   /**
     *contains all the cards in the deck, regardless of whether they are face up or down
     */
   private ArrayList<Card> fullDeck;
   
   /**
     *contains all the cards that have not been dealt out of the deck yet. all face down.
     */
   private Stack<Card> undealt;
   
   /**
     *contains all the cards that have been dealt. only the topmost (highest index number) can be
     *moved by the user. all face up, at most three will be visible on the screen.
     */
   private ArrayList<Card> dealt;
   
   /**
     *the top left corner x value for both the dealt pile and the undealt pile
     */
   private final int x;
   
   /**
     *the top left corner y value for the pile with the undealt cards
     */
   private final int undealtY;
   
   /**
     *the top left corner y value for the pile with the dealt cards
     */
   private final int dealtY;
   
   /**
     *the distance, in pixels, between the y coordinates of the top left corners of the undealt pile
     *and the dealt pile.
     */
   private static final int SPACING = Card.CARD_HEIGHT + 20;
   
   /**
     *the maximum number of cards to be dealt out from the undealt pile to the dealt pile at a time
     */
   private final int MAX_CARDS_DEALT = 3;
   
   /**
     *the constructor for a Deck. Sets all the necessary variables, including instance variables for
     *each Card that is added to the Deck.
     *
     *@param deck - all the cards that will be put into the deck
     *@param x - the x coordinate of the top left corners of both the undealt cards and the dealt cards
     *@param undealtY - the y coordinate of the top left corner of the undealt cards
     */
   public Deck(ArrayList<Card> deck, int x, int undealtY)
   {
      fullDeck = deck;
      undealt = new Stack<Card>();
      for (int ii = fullDeck.size() - 1; ii >= 0; ii--)
      {
         Card c = fullDeck.get(ii);
         c.setX(x);
         c.setY(undealtY);
         c.setLocation(Card.Location.DECK);
         undealt.push(c);  
      }
      dealt = new ArrayList<Card>();
      this.x = x;
      this.undealtY = undealtY;
      dealtY = undealtY + SPACING;
   }
   
   /**
     *deals out cards from the undealt pile to the dealt pile as the rules of solitaire dictate.
     *if there are no more cards left in the undealt pile to deal out, all the cards from the dealt
     *pile are moved back to the undealt pile.
     */
   public void deal()
   {
      if (fullDeck.size() != 0)
      {
         if (undealt.size() != 0)
         {
            for (int ii = 0; ii < dealt.size(); ii++)
            {
               Card movingCard = dealt.get(ii);
               movingCard.setY(dealtY);
               dealt.set(ii, movingCard);
            }
            if (undealt.size() >= MAX_CARDS_DEALT)
            {
               for (int jj = 1; jj <= MAX_CARDS_DEALT; jj++)
               {
                  Card movingCard = undealt.pop();
                  movingCard.setY(dealtY + (jj - 1) * Card.SMALL_SUIT_HEIGHT);
                  movingCard.flip();
                  dealt.add(movingCard);
               }
            }
            else
            {
               int undealtCards = undealt.size();
               for (int kk = 1; kk <= undealtCards; kk++)
               {
                  Card movingCard = undealt.pop();
                  movingCard.setY(dealtY + (kk - 1) * Card.SMALL_SUIT_HEIGHT);
                  movingCard.flip();
                  dealt.add(movingCard);
               }
            }
         }
         else
         {
            int nCardsLeft = dealt.size() - 1; //to avoid diminishing size
            for (int a = nCardsLeft; a >= 0; a--)
            {
               Card movingCard = dealt.remove(a);
               movingCard.setY(undealtY);
               movingCard.flip();
               undealt.push(movingCard);
            }
         }
         Solitaire.drawScreen();
      }
   }
   
   /**
     *draws the deck by first drawing a black rectangle where the undealt pile is, then drawing each
     *card in the deck. Because the cards' x and y coordinates and faceup status have been updated as
     *necessary when being added to the deck or to either the dealt or undealt pile, the cards already
     *"know" how to properly draw themselves in their deck locations.
     *
     *@param g - the graphics context for the drawingpanel onto which the deck will be drawn
     */
   public void draw(Graphics g)
   {
      g.setColor(Color.BLACK);
      g.fillRect(x, undealtY, Card.CARD_WIDTH, Card.CARD_HEIGHT);
      for (Card c : fullDeck)
      {
         c.draw(g);
      }
   }
   
   /**
     *gives a string representation of this deck. this representation has all the cards left in the
     *deck in their proper order. this method is only used for debugging purposes.
     *
     *@return string - all the cards in the deck in their string form
     */
   public String toString()
   {
      String string = "";
      for (int ii = 0; ii < fullDeck.size(); ii++)
      {
         string += fullDeck.get(ii).toString() + "\n";
      }
      
      return string;
   }
   
   /**
     *accessor method for the arraylist of dealt cards
     *
     *@return dealt - the arraylist of dealt cards
     */
   public ArrayList<Card> getDealt()
   {
      return dealt;
   }
   
   /**
     *if it is legal to do so, this method removes the topmost (highest index) card of the dealt pile,
     *and also removes that same card from the arraylist containing all the cards in the deck.
     */
   public void removeTop()
   {
      if (!dealt.isEmpty())
      {
         Card c = dealt.remove(dealt.size() - 1);
         fullDeck.remove(c);
      }
   }
}