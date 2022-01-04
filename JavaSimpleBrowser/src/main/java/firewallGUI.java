
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
public class firewallGUI {
    JButton add,delete;
    JPanel p1,p2,p3;
    JFrame fr;
    JEditorPane Pane = new JEditorPane();//...the display panel
    JTextField text;
    String s=new String();
    List<String> list;
    GUIManager cl;
    JRadioButton r1;
    JRadioButton r2;
    ButtonGroup bg;
    //Constructor of firewallGUI class
    firewallGUI(GUIManager tmp)
    {
        cl=tmp;
        initFirewall();
    }
    //Function initializes GUI
    public void initFirewall()
    {
        //Creates instances of 2 Jradiobuttons and sets their bounds
        r1=new JRadioButton("Enable");    
        r2=new JRadioButton("Disable");  
        r1.setBounds(75,50,100,30);r2.setBounds(75,100,100,30);    
        
        //Creates instance of button group and adds button
        bg=new ButtonGroup();    
        bg.add(r1);bg.add(r2);        

        //creates instances of JPanels
        p1= new JPanel();
        p2= new JPanel();
        p3= new JPanel();
        
        //Creates instances of JTextField and 2 Jbuttons
        text= new JTextField(14);
        fr= new JFrame();
        add = new JButton("add");
        delete = new JButton("delete");
        
        //Sets border of buttons to false;
        add.setBorderPainted(false);
        delete.setBorderPainted(false);
        
        //adds icon to add button
        try {
        File imgFile=new File("add.png");
        Image img=ImageIO.read(imgFile);
        ImageIcon icon=new ImageIcon(img);
        add.setIcon(icon);
        } catch (Exception ex) {
            System.out.println(ex);
        } 
        //adds icon to delete button
        try {
        File imgFile=new File("remove.png");
        Image img=ImageIO.read(imgFile);
        ImageIcon icon=new ImageIcon(img);
        delete.setIcon(icon);
        } catch (Exception ex) {
            System.out.println(ex);
        }  
        
        //Action listener of add button
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToFirewall();}});
        //Action listener of add button
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeFromFirewall();}});
        
        //adds buttons and text fields to the panels 
        // and adds sub panels to main panels
        p3.add(r1);
        p3.add(r2);
        p2.add(text);
        p2.add(add);
        p2.add(delete);
        p1.add(p3);
        p1.add(p2);
        p1.setLayout(new GridLayout(2,3));
        
        //sets the content type and allignment
        Pane.setContentType("text/html");
        Pane.setEditable(false);
        Pane.setAlignmentX(SwingConstants.CENTER);
        Pane.setAlignmentY(SwingConstants.CENTER);
        
        
        //Sets the attributes of frames
        fr.setLayout(new BorderLayout());
        fr.add(p1, BorderLayout.NORTH);
        fr.setSize(420,420);
        fr.setLocationRelativeTo(null);
        fr.setVisible((true)); 
        fr.getContentPane().add(new JScrollPane(Pane), BorderLayout.CENTER);
        //Window listener to update firewall
        fr.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            UpdateFirewall();
            cl.readFirewall();
            cl.fireStat=true;
        }});
        
        //updates JEditorPane
        showFirewall();
    }
    
    //Updates JEditorPane after loading firewall setting from file
    public void showFirewall()  
    {
        list=new ArrayList<String>();
        try{
        BufferedReader read = new BufferedReader(new FileReader("firewall.txt"));  
        s="";
        s="<html><center><font size=5> ";
        String line;
        while ((line = read.readLine()) != null) 
        {
            if("Enable".equals(line))
            {
                r1.setSelected(true);
            }
            else if( "Disable".equals(line))
            {
                r2.setSelected(true);
            }
            else
            {
                list.add(line);
                s=s+"<br>"+line;
            }
        }
        s=s+"</font></center></html>";
        Pane.setText(s);
        read.close();
        }catch(Exception e) {
            error obj=new error("Unable to Read Firewall Settings");
        }
    }
    
    //adds new words to firewall
    public void addToFirewall()
    {
      try{
      String tmp=text.getText();
      BufferedWriter writer = new BufferedWriter(new FileWriter("firewall.txt", true));
      Boolean check=false;
      for(String str:list)
      {
          if(str.matches(tmp))
          {
              check=true;
          }
      }
      if(!check)
      {
          writer.append(tmp);
          writer.append('\n');
      }
      writer.close();
      }catch(Exception e) {
            error obj=new error("Unable to write Firewall Settings");
        }
      showFirewall();
    }
    
    //removes the world from the firewall file.
    public void removeFromFirewall()
    {
        String tmp= text.getText();
        try{
        List<String> t=new ArrayList<String>();
        File in= new File("firewall.txt");
        BufferedReader reader = new BufferedReader(new FileReader("firewall.txt"));
        String line;
        while ((line=reader.readLine())!=null)
        {
            if(!line.matches(tmp))
            {
                t.add(line);
            }
        }
        in.delete();
        File out= new File("firewall.txt");
        out.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter("firewall.txt", false));
        for(String wr:t)
        {
            writer.write(wr+"\n");
        }
        reader.close();
        writer.close();
        showFirewall();
        }catch(Exception e) {
            error obj=new error("Unable to write Firewall Settings");
        }
    }
    
    //Updates the firewall file 
    public void UpdateFirewall()
    {
        String tmp= text.getText();
        try{
        List<String> t=new ArrayList<String>();
        File in= new File("firewall.txt");
        BufferedReader reader = new BufferedReader(new FileReader("firewall.txt"));
        String line;
        while ((line=reader.readLine())!=null)
        {
            if(!("Enable".equals(line) || "Disable".equals(line)))
            {
               t.add(line); 
            }
        }
        in.delete();
        File out= new File("firewall.txt");
        out.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter("firewall.txt", false));
        if(r1.isSelected())
        {
            writer.write("Enable\n");
        }
        else if(r2.isSelected())
        {
            writer.write("Disable\n");
        }
        for(String wr:t)
        {
            writer.write(wr+"\n");
        }
        reader.close();
        writer.close();
        showFirewall();
        }catch(Exception e) {
            error obj=new error("Unable to write Firewall Settings");
        }
    }
}
