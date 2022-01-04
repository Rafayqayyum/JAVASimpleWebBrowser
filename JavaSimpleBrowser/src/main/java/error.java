import javax.swing.*;
/**
 *
 * @author rafay
 */

//This class displays error messages recieved by other classes as 
//a parameter in constructor
public class error {
    String message;
    error(String mess)
    {
        message=mess;
        initError();
    }
    public void initError()
    {
        final JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
        
    }
}
