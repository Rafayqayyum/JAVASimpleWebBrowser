
import javax.swing.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rafay
 */

//Displays search result recieved from searcGUI's Show result function
public class searchResult {
    String message;
    searchResult(String mess)
    {
        message=mess;
        initSearchResult();
    }
    public void initSearchResult()
    {
        final JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, message, "Result", JOptionPane.INFORMATION_MESSAGE);
        
    }
}
