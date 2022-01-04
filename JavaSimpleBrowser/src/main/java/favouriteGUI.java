
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
public class favouriteGUI {
    JButton add,delete;
    JPanel p1,p2;
    JFrame fr;
    JEditorPane Pane = new JEditorPane();
    JTextField text;
    String s=new String();
    List<String> list;
    GUIManager cl;
    favouriteGUI(GUIManager tmp) {
        cl=tmp;
        initfavouriteGUI();
    }
    public void initfavouriteGUI()
    {     
        //Creates instances of JPanels
        p1= new JPanel();
        p2= new JPanel();
        
        //Creates instance of JTextField and sets text
        text= new JTextField(14);
        text.setText("Type fav to be deleted");
        
        //Creates instances of JFrame and JButtons
        fr= new JFrame();
        add = new JButton();
        delete = new JButton();
        
        //Adds icon to add button 
        try {
        File imgFile=new File("add.png");
        Image img=ImageIO.read(imgFile);
        ImageIcon icon=new ImageIcon(img);
        add.setIcon(icon);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //Adds icon to delete button
        try {
        File imgFile=new File("remove.png");
        Image img=ImageIO.read(imgFile);
        ImageIcon icon=new ImageIcon(img);
        delete.setIcon(icon);
        } catch (Exception ex) {
            System.out.println(ex);
        } 
        //action listener of add button
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToFav();}});
        //action listener of delete button
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeFromFav();}});
        
        //removes border of both buttons
        add.setBorderPainted(false);
        delete.setBorderPainted(false);
        
        //sets JEditorPane content type,hyperlinkListener and allignment
        Pane.setContentType("text/html");
        Pane.setEditable(false);
        Pane.addHyperlinkListener(cl);
        Pane.setAlignmentX(SwingConstants.CENTER);
        Pane.setAlignmentY(SwingConstants.CENTER);
        
        //adds window Listener to frame to check the status
        fr.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                cl.favStat=true;
                
        }});

        
        //adds buttons, textfields and subpanel to main panel;
        p2.add(text);
        p2.add(add);
        p2.add(delete);
        p1.add(p2);
        
        //adds main panel p1 to frame, sets layout, adds a JScrollPane  and sets
        //it visible
        fr.setLayout(new BorderLayout());
        fr.add(p1, BorderLayout.NORTH);
        fr.setSize(420,420);
        fr.setLocationRelativeTo(null);
        fr.setVisible((true)); 
        fr.getContentPane().add(new JScrollPane(Pane), BorderLayout.CENTER);
       
        //call to show fav method
        showFav();
    }
    
    //shows favourite in JEditorPane after reading from file
    public void showFav() 
    {
        list=new ArrayList<String>();
        try{
        BufferedReader read = new BufferedReader(new FileReader("favourite.txt"));  
        s="";
        s="<html><center><font size=5> ";
        String line;
        while ((line = read.readLine()) != null) 
        {
                list.add(line);
                s=s+"<br>"+line;
        }
        s=s+"</font></center></html>";
        Pane.setText(s);
        read.close();
        }catch(Exception e) {
            error obj=new error("Unable to fetch favourites");
        }
    }
    
    //adds current page to favourites
    public void addToFav() 
    {
      try{
      URL url=cl.displayEditorPane.getPage();
      BufferedWriter writer = new BufferedWriter(new FileWriter("favourite.txt", true));
      Boolean check=false;
      for(String str:list)
      {
          if(url.toString().equals(str))
          {
               check=true;   
          }
          
      }
      if(!check)
      {
          writer.append(url.toString());
          writer.append('\n');
      }
      writer.close();
      }catch(Exception e) {
            error obj=new error("Unable to add Favourite");
        }
      showFav();
    }
    
    //removes the typed link from fav
    public void removeFromFav() 
    {
        String tmp= text.getText();
        try{
        List<String> t=new ArrayList<String>();
        File in= new File("favourite.txt");
        BufferedReader reader = new BufferedReader(new FileReader("favourite.txt"));
        String line;
        while ((line=reader.readLine())!=null)
        {
            if(!line.matches(tmp))
            {
                t.add(line);
            }
        }
        in.delete();
        File out= new File("favourite.txt");
        out.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter("favourite.txt", false));
        for(String wr:t)
        {
            writer.write(wr+"\n");
        }
        reader.close();
        writer.close();
        showFav();
        }catch(Exception e) {
            error obj=new error("Unable to remove favourite");
        }
    }
  
}
