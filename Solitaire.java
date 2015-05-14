import javax.swing.*; //for gui components
import java.awt.*;
import java.util.*;

public class Solitaire
{
   public static final int PANEL_WIDTH = 800;
   public static final int PANEL_HEIGHT = 600;
   public static final int PILES_SPACE = 20;
   //private static enum Piles {pile1, pile2, pile3, pile4, pile5, pile6, pile7}
   
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
      Graphics g = panel.getGraphics();
      //panel.setBackground(Color.BLUE);

      Pile[] pileArray = new Pile[7];
      for (int ii = 1; ii <= 7; ii++)
      {
         pileArray[ii - 1] = makePile(ii);
         pileArray[ii - 1].draw(g);
      }
      
      for (int jj = 1; jj <= 4; jj++)
      {
         makeFoundation(jj).draw(g);
      }
      FoundationListener fListener = new FoundationListener(); 
      panel.addMouseListener(fListener);
      
      //frame.setVisible(true);
      
      ArrayList<Card> deck52 = generateAndShuffle();
      Deck deck = new Deck(dealToPiles(deck52, pileArray), PILES_SPACE, Card.CARD_HEIGHT + PILES_SPACE + 10);
      deck.draw(g);
      
      for (Pile p : pileArray)
      {
         for (Card c : p.unseenCardList)
         {
            c.draw(g);
         }
         p.unseenCardList.get(p.unseenCardList.size() - 1).flip();
         p.unseenCardList.get(p.unseenCardList.size() - 1).draw(g);
      }
      
      //for testing only
      /**for (int ii = 0; ii < 7; ii++)
      {
         System.out.println("Pile " + (ii + 1) + ": " + pileArray[ii].toString());
      }
      System.out.println("Deck: " + deck.toString());**/
      
      //CardListener cListener = new CardListener();
     // panel.addMouseListener(cListener);
      
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
}