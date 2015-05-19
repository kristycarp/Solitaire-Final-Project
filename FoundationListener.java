import javax.swing.event.*;
import java.awt.event.*;

public class FoundationListener extends MouseInputAdapter
{
   private Foundation f;
   
   public FoundationListener(Foundation f)
   {
      this.f = f;
   }
   
   public void mouseClicked(MouseEvent event)
   {
      //System.out.println("It worked!!!!!!! :D");
   }
}