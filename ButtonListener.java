import javax.swing.event.*;
import java.awt.event.*;

public class ButtonListener extends MouseInputAdapter
{
   private Button b;
   private Deck d;
   
   public ButtonListener(Button b, Deck d)
   {
      this.b = b;
      this.d = d;
      //System.out.println("oithowaehgoewahg");
   }
   
   public void mouseClicked(MouseEvent event)
   {
      //System.out.println("you clicked the screen");
      if (b.isHit(event.getX(), event.getY()))
      {
         d.deal();
      }
   }
}