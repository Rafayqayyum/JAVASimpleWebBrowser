import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rafay
 */
public class searchGUI {
    
    JButton btn;
    JTextField text;
    JFrame fr;
    JPanel pan1;
    GUIManager cl;
    
    //Constructor of searchGUI
    searchGUI(GUIManager g)
    {
        cl=g;
        initSearchGUI();
    }
    public void initSearchGUI()
    {
        //Creates instance of Jtextfield,Jbutton,Jpanel and Jframe
        text= new JTextField();
        btn= new JButton("Search");
        pan1= new JPanel();
        fr= new JFrame();
        
        //Sets button border false
        btn.setBorderPainted(false);
        //adds icon to button
        try {
        File imgFile=new File("search.png");
        Image img=ImageIO.read(imgFile);
        ImageIcon icon=new ImageIcon(img);
        btn.setIcon(icon);
        } catch (Exception ex) {
            System.out.println(ex);
        } 
        
        
        //sets size and layout of pan1
        pan1.setLayout(new GridLayout(2,1));
        pan1.setSize(170,170);
        
        //adds text and button to pan1
        pan1.add(text);
        pan1.add(btn);
        
        //adds pan1 to Jframe and sets the attributes of Jframe
        fr.add(pan1);
        fr.setSize(200,200);
        fr.setLocationRelativeTo(null);
        fr.setVisible((true));
        
        //action listener of btn
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showResult();
            }
        });
        //window listener of Jframe
        fr.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                cl.searchStat=true;      
        }});    
    }
    
    //Searches word in webpage for its occurances
    public String searchWord() 
    {
        String s=text.getText();
        int count=0;
        MyHtml2Text parser = new MyHtml2Text();
        try {
            parser.parse(new StringReader(cl.displayEditorPane.getText()));
        } catch (IOException ee) {
           //handle exception
        }
        String page[]=parser.getText().split(" ");
        for(int i=0;i< page.length;i++)
        {
            if(page[i].toLowerCase().contains(s.toLowerCase()))
            {
                count++;
            }
        }
        
        
        return "Number of times "+s+ " appears in Page= "+count;
    }
    
    //shows the result of search by creating searchResult instance
    public void showResult()
    {
        searchResult var= new searchResult(searchWord());
    }
}
