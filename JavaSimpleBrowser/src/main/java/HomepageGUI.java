
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rafay
 */
public class HomepageGUI {
    JButton makeHomepage;
    JTextField text;
    JEditorPane Pane = new JEditorPane();//...the display panel
    JPanel p1,p2,p3;
    JFrame fr;
    GUIManager cl;
    HomepageGUI(GUIManager a)
    {
        cl=a;
        initHomepageGUI();
    }
    public void initHomepageGUI()      
    {
        
        
        //Creates a Jframe instance
        fr= new JFrame();
        
        //Creates a JButton instance and sets borders false;
        makeHomepage= new JButton ("Set Homepage");
        makeHomepage.setBorderPainted(false);
        
        //Creates a JTextField instance, sets it font and size
        text= new JTextField(20);
        text.setFont(new Font("Arial", Font.PLAIN, 17));
        text.setPreferredSize(new Dimension(30,30));
        
        //Creates a JPanel instance
        p1= new JPanel();
        p2= new JPanel();
        p3= new JPanel();
        
        
        //makeHomepage Button's ActionListener
        makeHomepage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateHomepage(text.getText());
                setHomepage();}});
        
        
        //adds textfield, button and panels in main panel
        p1.setLayout(new GridLayout(2,1));
        p1.add(text);
        p1.add(makeHomepage);
        p2.add(Pane);
        p3.add(p1);
        p3.setLayout(new GridLayout(2,1));
        p3.add(p2);
        p3.setPreferredSize(new Dimension(520,270));
                
        //sets JEditorPane's content and alignment
        Pane.setContentType("text/html");
        Pane.setEditable(false);
        Pane.addHyperlinkListener(cl);
        Pane.setAlignmentX(SwingConstants.CENTER);
        Pane.setAlignmentY(SwingConstants.CENTER);
        
        
        
        //sets frame size,border,layout and window listener
        fr.setLayout(new BorderLayout());
        fr.add(p3, BorderLayout.NORTH);
        fr.setSize(520,270);
        fr.setLocationRelativeTo(null);
        fr.setVisible((true)); 
        fr.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                cl.homeStat=true;
                
        }});
            updateHomepage(getHomePage()); // Shows homepage
    }
    
    
    //writes New homepage to the file
    public void setHomepage()
    {
      try{
        File in= new File("homepage.txt");
        in.delete();
        File out= new File("homepage.txt");
        out.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter("homepage.txt", false));
        writer.write(text.getText()); 
        writer.close();
      }catch(Exception e) {
            error obj=new error("Unable to OpenPage");
        }   
    }
    
    //Reads homepage from file and returns a string
    public String getHomePage()
    {
      try
      {
          BufferedReader read = new BufferedReader(new FileReader("homepage.txt"));   
          String s =read.readLine();
          read.close();
          return (s);
      }catch(Exception e)
      {   
          error obj=new error("Unable to open Homepage");
          return null;
      }
    }
    
    //Updates JEditorPane
    public void updateHomepage(String s)
    {
        Pane.setText("<html><center><font size=5><a href=\""+s+"\">"+s+"</a></font></center></html>");
    }
    
}
