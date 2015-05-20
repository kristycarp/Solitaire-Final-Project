import javax.swing.*; //for gui components
import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;


public class Solitaire
{
   public static final int PANEL_WIDTH = 800;
   public static final int PANEL_HEIGHT = 600;
   public static final int PILES_SPACE = 20;
   //private static enum Piles {pile1, pile2, pile3, pile4, pile5, pile6, pile7}
   private static Pile[] pileArray;
   private static BufferedImage background;
   private static Graphics g;
   private static Deck deck;
   private static Foundation[] foundationArray;
   private static Button dealButton;
   
   public static void main(String[] args)
   {
      
      DrawingPanel panel = new DrawingPanel(PANEL_WIDTH, PANEL_HEIGHT);
      g = panel.getGraphics();
      try
      {
         background = ImageIO.read(new File("background1.png"));
      }
      catch (IOException e)
      {
         System.out.println("Problem loading background image");
      }
      //g.drawImage(background, 0, 0, null);
      //panel.setBackground(Color.BLUE);

      pileArray = new Pile[7];
      for (int ii = 1; ii <= 7; ii++)
      {
         pileArray[ii - 1] = makePile(ii);
         //pileArray[ii - 1].draw(g);
      }
      
      foundationArray = new Foundation[4];
      for (int jj = 1; jj <= 4; jj++)
      {
         foundationArray[jj - 1] = makeFoundation(jj);
         //foundationArray[jj - 1].draw(g);
      }
      //FoundationListener fListener = new FoundationListener(); 
      //panel.addMouseListener(fListener);
      
      //frame.setVisible(true);
      
      ArrayList<Card> deck52 = tempGenerateAndShuffle();
      ArrayList<Card> permaDeck = (ArrayList<Card>) deck52.clone();
      deck = new Deck(dealToPiles(deck52, pileArray), PILES_SPACE, Card.CARD_HEIGHT + PILES_SPACE + 10);
      CardListener cListener = new CardListener(permaDeck, panel, foundationArray, pileArray, deck);
      panel.addMouseListener(cListener);
      //deck.draw(g);
      
      for (Pile p : pileArray)
      {
         p.flipLastCard();
         //for (Card c : p.allCardsInPile())
         //{
            //c.draw(g);
         //}
      }
      
      dealButton = new Button(PILES_SPACE, Card.CARD_HEIGHT + PILES_SPACE - Button.BUTTON_HEIGHT, "Draw");
      ButtonListener dealListener = new ButtonListener(dealButton, deck);
      panel.addMouseListener(dealListener);
      
      drawScreen();
      
      //for testing only
      /**for (int ii = 0; ii < 7; ii++)
      {
         System.out.println("Pile " + (ii + 1) + ": " + pileArray[ii].toString());
      }
      System.out.println("Deck: " + deck.toString());**/
      
      //for testing only
      /**Card testCard = new Card(Card.Suit.Diamonds, 2, 500, 500);
      testCard.draw(g);**/
   }
   
   public static Pile makePile(int i)
   {
      return (new Pile(i * (Card.CARD_WIDTH + PILES_SPACE) + 2 * PILES_SPACE, 10));
   }
   
   public static Foundation makeFoundation(int i)
   {
      return (new Foundation(PANEL_WIDTH - Card.CARD_WIDTH - PILES_SPACE, i * (Card.CARD_HEIGHT + PILES_SPACE) + 10));
   }
   
   public static ArrayList<Card> generateAndShuffle()
   {
      ArrayList<Card> deck = new ArrayList<Card>(52);
      for (Card.Suit s : Card.Suit.values())
      {
         for (int value = 1; value <= 13; value++)
         {
            deck.add(new Card(s, value, 0, 0));
         }
      }
      //add shuffling here
      //System.out.println(deck.toString());
      return deck;
   }
   
   public static ArrayList<Card> tempGenerateAndShuffle()
   {
      Card[] deck = new Card[52];
      int ii = 0;
      for (Card.Suit s : Card.Suit.values())
      {
         for (int value = 1; value <= 13; value++)
         {
            deck[ii] = new Card(s, value, 0, 0);
            ii++;
         }
      }
      //add shuffling here
      DeckShuffler.shuffle(deck, 52);
      ArrayList<Card> realDeck = new ArrayList<Card>(52);
      for (Card c : deck)
      {
         realDeck.add(c);
      }
      
      //for rigging purposes
      /**for (int asdf = 0; asdf < 52; asdf++)
      {
         realDeck.set(asdf, new Card());
      }
      realDeck.set(27, new Card(Card.Suit.Spades, 2, 0, 0));
      realDeck.set(26, new Card(Card.Suit.Spades, 3, 0, 0));
      realDeck.set(24, new Card(Card.Suit.Hearts, 4, 0, 0));**/

      return realDeck;
   }
   
   //returns remaining deck
   public static ArrayList<Card> dealToPiles(ArrayList<Card> deck, Pile[] pileArray)
   {
      //int dealt = 0;
      for (int nPilesToDeal = 7; nPilesToDeal > 0; nPilesToDeal--)
      {
         for (int nCardsDealt = 1; nCardsDealt <= nPilesToDeal; nCardsDealt++)
         {
            pileArray[nCardsDealt - 1].dealCard(deck.remove(0));
            //dealt++;
            //System.out.println(dealt);
         }
      }
      
      return deck;
   }
   
   public static Pile whichPileIsHit(int x)
   {
      for (Pile p : pileArray)
      {
         if (x >= p.getX() && x <= p.getX() + Card.CARD_WIDTH)
         {
            return p;
         }
      }
      System.out.println("no pile was hit");
      return null; 
   }
   
   public static void drawScreen()
   {
      g.setColor(Color.WHITE);
      g.drawRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
      g.drawImage(background, 0, 0, null);
      for (Pile p : pileArray)
      {
         p.draw(g);
         for (Card c : p.getUnseen())
         {
            c.draw(g);
         }
         for (Card c : p.getSeeable())
         {
            c.draw(g);
         }
         //System.out.println(p.toString());
      } 
      deck.draw(g);
      for (Foundation f : foundationArray)
      {
         f.draw(g);
         for (Card c : f.getCards())
         {
            c.draw(g);
         }
      }
      dealButton.draw(g);
   }
   
   public static Foundation whichFoundationIsHit(int y)
   {
      for (Foundation f : foundationArray)
      {
         if (y >= f.getY() && y <= f.getY() + Card.CARD_HEIGHT)
         {
            return f;
         }
      }
      System.out.println("no foundation was hit");
      return null; 
   }
   
   public static Deck getDeck()
   {
      return deck;
   }
   
}