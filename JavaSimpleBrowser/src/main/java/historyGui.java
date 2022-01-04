
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.util.*;  
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;

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
public class historyGui {
    String ref[];
    String lastDate;
    JFrame fr;
    JTextField t1,t2;
    JPanel pan,pan2;
    JButton btn;
    List<String> list=new ArrayList<String>();
    JEditorPane Pane = new JEditorPane();//...the display panel
    GUIManager g;
    //Constructor of historyGUI
    historyGui(GUIManager tmp, List<String> li)
    {
        list=li;
        g=tmp;
        initGui();
    }
    public void initGui() {
        //Creates an instance of Jframe and sets its layout
        fr= new JFrame();
        fr.setLayout(new BorderLayout());
        //Creates instances of 2 JTextFields and sets text
        t1=new JTextField(10);
        t2= new JTextField(10);
        t1.setText("dd/mm/yyyy");
        t2.setText("dd/mm/yyyy");
        //adds window listener to Jframe
        fr.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                g.histStat=true;        
        }});
        
        //Creates instances of 2 JPanels
        pan= new JPanel();
        pan2= new JPanel();
        
        //Creates an instance of JButton
        btn=new JButton("Search");
        //Action listener of btn
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showHistory();
            }
        });
        
        //sets button border to false
        btn.setBorderPainted(false);
        
        //adds button and textfields to panel
        pan2.add(t1);
        pan2.add(t2);
        pan2.add(btn);
        
        //adds icon to button
        try {
        File imgFile=new File("search.png");
        Image img=ImageIO.read(imgFile);
        ImageIcon icon=new ImageIcon(img);
        btn.setIcon(icon);
        } catch (Exception ex) {
            System.out.println(ex);
        } 


        //Sets contenttype. size and alignment for JEditorpane
        Pane.setContentType("text/html");
        pan.setPreferredSize(new Dimension(300,200));
        Pane.setEditable(false);
        Pane.setAlignmentX(SwingConstants.CENTER);
        Pane.setAlignmentY(SwingConstants.CENTER);
        Pane.addHyperlinkListener(g);
        
        //Sets attributes of JFrame
        fr.add(pan2,BorderLayout.NORTH);
        fr.add(Pane);
        fr.setSize(420,420);
        fr.setLocationRelativeTo(null);
        fr.setVisible((true));   
        fr.getContentPane().add(new JScrollPane(Pane), BorderLayout.CENTER);
        
        //shows history
        readHistory();
        
    }
    //Updates history on Jeditorpane
    public void readHistory()
    {
        try{
        String s="<html><center><font size=5> ";
        Stack<String> tmp= new Stack<String>();
        for (String line:list)
        {
            tmp.add(line);
        }
        while(!tmp.empty())
        {
            String line=tmp.pop();
            System.out.println(line);
            if(Pattern.matches("[0-9]*/[0-9]*/[0-9]* [0-9]*:[0-9]*:[0-9]*", line))
            {
                String date=line.split(" ")[0];
                if(!date.equals(lastDate) || lastDate==null)
                {
                    s=s+"<br><b>"+date+"</b>";
                }
                lastDate=date;
            }
            else
            {
                s=s+"<br><a href=\""+line+"\">"+line+"</a>";
            }
        }
        s=s+"</font></center></html>";
        Pane.setText(s);
        }catch(Exception e) {
      System.out.println("Unable to open history");
    }
    }
    
    //Shows history after search is performed
    public void showHistory()
    {
        String d1=t1.getText();
        String d2=t2.getText();
        if(Pattern.matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$", d2) &&
                Pattern.matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$", d1))
        { 
            try{
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(d1); 
            Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(d2);         
            String s="<html><center><font size=5> ";
            Boolean check=false;
            Stack<String> tmp= new Stack<String>();
            for (String line:list)
            {
                tmp.add(line);
            }
            while(!tmp.empty())
            {
                String line=tmp.pop();
                if(Pattern.matches("[0-9]*/[0-9]*/[0-9]* [0-9]*:[0-9]*:[0-9]*",line))
                {
                    String date=line.split(" ")[0];
                    Date dat=new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    if(!date.equals(lastDate) || lastDate==null)
                    {
                        if((dat.equals(date1) || dat.after(date1)) && (dat.equals(date2)|| dat.before(date2)))
                        {
                            s=s+"<br><b>"+date+"</b>";
                            check=true;
                        }
                        else
                        {
                             check=false;   
                        }
                    }
                    lastDate=date;
                } 
                else
                {
                    if(check)
                    {
                        s=s+"<br><a href=\""+line+"\">"+line+"</a>";
                    }
                }
            }    
            s=s+"</font></center></html>";
            Pane.setText(s);
        }catch(ParseException e)
        {}
        }
        else
        {
            error obj=new error("Wrong Format");
        } 
    }
}
