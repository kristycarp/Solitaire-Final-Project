import javax.swing.event.*;
import java.awt.event.*;

public class DealButtonListener extends MouseInputAdapter
{
   private Button b;
   private Deck d;
   
   public DealButtonListener(Button b, Deck d)
   {
      this.b = b;
      this.d = d;
      //System.out.println("oithowaehgoewahg");
   }
   
   public void mouseClicked(MouseEvent event)
   {
      //System.out.println("you clicked the screen");
      if (!CardListener.isWon() && !CardListener.somethingSelected() && b.isHit(event.getX(), event.getY()))
      {
         d.deal();
      }
   }
}