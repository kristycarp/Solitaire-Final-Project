import javax.swing.event.*;
import java.awt.event.*;

public class CardListener extends MouseInputAdapter
{
   public static boolean firstClickMade;
   
   public CardListener()
   {
      firstClickMade = false;
   }
   
   public void mouseClicked(MouseEvent event)
   {
      firstClickMade = !firstClickMade;
      System.out.println("you clicked on a card :):):)");
   }
}