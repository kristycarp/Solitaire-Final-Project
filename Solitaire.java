import javax.swing.*; //for gui components
import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

///UNIT TESTING AAAAh
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
   
   public static void main(String[] args)
   {
      /**JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setTitle("Solitaire");
      frame.setSize(new Dimension(800, 600));
      
      JPanel panel = new JPanel();
      panel.setBackground(Color.BLUE);
      frame.add(panel);
      panel.setLayout(new GridLayout());**/
      
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
      CardListener cListener = new CardListener(permaDeck, panel);
      panel.addMouseListener(cListener);
      deck = new Deck(dealToPiles(deck52, pileArray), PILES_SPACE, Card.CARD_HEIGHT + PILES_SPACE + 10);
      //deck.draw(g);
      
      for (Pile p : pileArray)
      {
         p.flipLastCard();
         //for (Card c : p.allCardsInPile())
         //{
            //c.draw(g);
         //}
      }
      
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
      System.out.println("something went wrong");
      return null; 
   }
   
   public static void drawScreen()
   {
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
      }
   }
}