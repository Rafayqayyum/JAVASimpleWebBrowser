/*Swing Imports*/
import javax.swing.JButton;
import javax.swing.JEditorPane;
import java.io.*;
import javax.swing.JFrame;
import java.text.SimpleDateFormat;  
import java.util.Date; 
import java.util.*;  
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

/*Abstract Window ToolKit imports */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;



public class GUIManager implements HyperlinkListener {
  //declaration
  JFrame fr;
  JButton buttonBack = new JButton(), buttonForward = new JButton();
  JTextField locationTextField = new JTextField(35);
  JEditorPane displayEditorPane = new JEditorPane();
  //stacks for forward and back button
  Stack<String> forw = new Stack<>();
  Stack<String> back = new Stack<>();
  JButton fav, history, refresh, home,firewallButt,search;
  JPanel bttnPanel, pan1, main; // panels
  BufferedReader read;   // buffer read
  List<String> firewallList;  //to store firewall
  List<String> list;  // for history
  Boolean firewallStatus;
  Boolean favStat, histStat,homeStat,fireStat,searchStat;  //status of respective windows
  GUIManager()
  {
      favStat=histStat=homeStat=fireStat=searchStat=true;
      initGUIManager();
      getHomePage();
  }
  public  void initGUIManager() {
    fr= new JFrame();
    
    //reads history and firewall at start
    readHistory();
    readFirewall();
    
    //Create JPanel instances to hold buttons, editorpane and sub panels
    bttnPanel = new JPanel();
    pan1= new JPanel();
    main= new JPanel();
   //sets layout of main panel
    main.setLayout(new GridLayout(2,2));
    
    //Create JButton instances and sets their icons
    fav= new JButton();
    setButtonIcon(fav,"favourites.png",90,40);
    history= new JButton();
    setButtonIcon(history,"history.png",90,40);
    refresh= new JButton();
    setButtonIcon(refresh,"refresh.png",90,40);
    firewallButt= new JButton();
    setButtonIcon(firewallButt,"firewall.png",90,40);
    home= new JButton();
    setButtonIcon(home,"home.png",90,40);
    setButtonIcon(buttonBack,"back.png",30,30);
    setButtonIcon(buttonForward,"forward.png",30,30);
    search= new JButton();
    setButtonIcon(search, "search.png" , 90, 40);
    JButton bttnGo = new JButton();     
    setButtonIcon(bttnGo, "go.png", 50, 35);
     
    
    //****ACTION LISTENERS OF BUTTONS****\\
    
    //back button's ActionListener
    buttonBack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        backActn();
      }
    });
    //search button's ActionListener
    search.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(searchStat)  //checks if one window is open
        {
            searchStat=false;
            makeSearchGUI();
        }
      }
    });
    //fav button's ActionListener
    fav.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(favStat) //checks if one window is open
        {
            favStat=false;
            showFavGUI();
        }
      }
    });
    
    //forward Button's ActionListener
    buttonForward.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        forwardActn();
      }
    });
    
    //history Button's ActionListener
    history.addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e) {
        if(histStat)
        {
            histStat=false;
             historyGUI();
        }
      }
    });
    //firewall Button's ActionListener
    firewallButt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(fireStat)
        {
            fireStat=false;
            firewallWindow();
        }
      }
    });
    //LocationTextField key Listener
    locationTextField.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          actionGo();
        }
      }
    });
    //bttnGo's ActionListener
    bttnGo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionGo();
      }
    });
    //refresh Button's ActionListener
    refresh.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        refreshPage();
      }
    });
    //home Button's ActionListener
    home.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(homeStat)
        {
            homeStat=false;
            homepageGUI();
        }
      }
    });
    
    //disables forward and back button at start
    buttonForward.setEnabled(false);
    buttonBack.setEnabled(false);
    
    
    //adds buttons to their respective panels
    //and panel in main panel
    bttnPanel.add(buttonBack);
    bttnPanel.add(buttonForward);
    bttnPanel.add(locationTextField);
    pan1.add(fav);
    pan1.add(history);
    pan1.add(search);
    pan1.add(home);
    pan1.add(refresh);
    pan1.add(firewallButt);
    bttnPanel.add(bttnGo);
    main.add(pan1);
    main.add(bttnPanel);
    
    
    //sets editor pane settings
    displayEditorPane.setContentType("text/html/CSS");
    displayEditorPane.setEditable(false);
    displayEditorPane.addHyperlinkListener(this);
    
    //sets Jframe
    fr.getContentPane().setLayout(new BorderLayout());
    fr.getContentPane().add(main, BorderLayout.NORTH);
    fr.getContentPane().add(new JScrollPane(displayEditorPane), BorderLayout.CENTER);
    fr.setSize(640, 480);//window size to 640px by 480px
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//what to do on close (default)
    fr.setVisible(true);
  }//close GUIManager()



  //Navigate back action
  private void backActn() {
    URL currentUrl = displayEditorPane.getPage();
    if(currentUrl!=null)
    {
        forw.add(currentUrl.toString());
    }
    try {
      showPage(new URL(back.pop()), false);
    } catch (Exception e) {
    }
  }

  //Navigate forward action
  private void forwardActn() {
    URL currentUrl = displayEditorPane.getPage();
    if(currentUrl!=null)
    {
        back.add(currentUrl.toString());
    }
    try {
      showPage(new URL(forw.pop()), false);
    } catch (Exception e) {
    }
  }

  //Go action
  private void actionGo() {
    URL verifiedUrl = verifyUrl(locationTextField.getText());
    locationTextField.setText(verifiedUrl.toString());
    if (verifiedUrl != null) {
      showPage(verifiedUrl, true);
    } else {
      error obj=new error("Unable to read firewall settings");
    }
  }


  //verify the URL
  private URL verifyUrl(String url) {
    String oldURL=url;

    if(!(url.contains("www.") || url.contains("https://")))
    {
        url="www."+url;
    }
    if(url.startsWith("www."))
    {
        url="https://"+url;
    }
    try{
    return new URL(url);
    }
    catch (Exception e)
    {
       return null;
    }
  }

  //Display the page
  private void showPage(URL pageUrl, boolean addToList) {
    Boolean open=true;
    for(String loc:firewallList)
    {
        if(pageUrl.toString().contains(loc))
        {
            open=false;
        }
    }        
    if(open || firewallStatus )
    {
        try {
          displayEditorPane.setPage(pageUrl);
          URL newUrl = pageUrl;
          if (addToList && newUrl!=null) {
              back.add(newUrl.toString());
              historyFile(newUrl);
          }
          locationTextField.setText(pageUrl.toString());
          updateBttns();
        } catch (Exception e) {
          System.out.println("Unable to load page");
        }
    }
    else
    {
       error obj=new error("Site Blocked"); 
    }
  }




  //Update the Buttons
  private void updateBttns() {
    if (back.empty()) {
      buttonBack.setEnabled(false);
    }
    else
    {
        buttonBack.setEnabled(true);
    }
    if(forw.empty()){
      buttonForward.setEnabled(false);
    }
    else
    {
        buttonForward.setEnabled(true);
    }
  }

  //Update the links
  public void hyperlinkUpdate(HyperlinkEvent event) {
    HyperlinkEvent.EventType eventType = event.getEventType();
    if (eventType == HyperlinkEvent.EventType.ACTIVATED) {
      if (event instanceof HTMLFrameHyperlinkEvent) {
        HTMLFrameHyperlinkEvent linkEvent = (HTMLFrameHyperlinkEvent) event;
        HTMLDocument document = (HTMLDocument) displayEditorPane.getDocument();
        document.processHTMLFrameHyperlinkEvent(linkEvent);
      } else {
        showPage(event.getURL(), true);
      }
    }
  }
  
//Gets Current date and time
  public String getDateTime()
  {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    Date date = new Date();  
    return formatter.format(date);  
  }
  
  //Writes history into file
  public void historyFile(URL url) throws IOException
  {
      String tmp=(url.toString());
      BufferedWriter writer = new BufferedWriter(new FileWriter("history.txt", true));
      writer.append(tmp);
      writer.append('\n');
      writer.append(getDateTime());
      writer.append('\n');
      writer.close();
  }
  
  //Reads history file
  public void readHistory()
  {
      list=new ArrayList<String>();
      try{
          read= new BufferedReader(new FileReader("history.txt"));
          String line;
          int i=0;
          while((line = read.readLine())!=null)
          {
              list.add(line);
              i++;
          }
      }catch (Exception e)
      {
          error obj=new error("Error opening some files");
      }
  }
  
  //Reads firewall settings from file
  public void readFirewall()
  {
      firewallList=new ArrayList<String>();  
      try{
        BufferedReader reader = new BufferedReader(new FileReader("firewall.txt"));
        String line;
        String s=reader.readLine();
        if("Enable".equals(s))
        {
            firewallStatus=false;   
        }
        else if("Disable".equals(s))
        {
            firewallStatus=true;   
        }
        while ((line=reader.readLine())!=null)
        {
            firewallList.add(line);
        }
        reader.close();
      }catch(Exception e) {
            error obj=new error("Unable to read firewall settings");
        }
  }
  
  //Refreshes the current page
  public void refreshPage()
  {
      URL currentUrl = displayEditorPane.getPage();
      if(currentUrl!=null)
      {
          showPage(currentUrl,false);
      }
  }
  
  //Opens the homepage at start
  public void getHomePage()
  {
      try
      {
          BufferedReader read = new BufferedReader(new FileReader("homepage.txt"));   
          URL url=new URL(read.readLine());
          read.close();
          showPage(url, false);
          
      }catch(Exception e)
      {   
          error obj=new error("Unable to open Homepage");
          e.printStackTrace();
      }
  }
  
  //Sets icons of buttons
  public void setButtonIcon(JButton btn, String name, int l, int h)
  {
    btn.setBorderPainted(false);
    btn.setPreferredSize(new Dimension(l,h));
    try {
        File imgFile=new File(name);
        Image img=ImageIO.read(imgFile);
        ImageIcon icon=new ImageIcon(img);
        btn.setIcon(icon);
    } catch (Exception ex) {
        System.out.println(ex);
    }   
  }
  
  //Creates a searchGUI instance
  public void makeSearchGUI()
  {   
      searchGUI var= new searchGUI(this);
  }
  
  //Creates a homepageGUI instance
  public void homepageGUI()
  {
      HomepageGUI var= new HomepageGUI(this);
  }
  
  //Creates a historyGUI Fav button instance
  public void showFavGUI()
  {
      favouriteGUI fav = new favouriteGUI(this);
  } 
  
  //Creates a historyGUI instance
  public void historyGUI()
  {
        readHistory();
        historyGui var =new historyGui(this,list);   
  }
  //Creates a firewallGUI instance
  public void firewallWindow()
  {
      firewallGUI fire= new firewallGUI(this);  
  }
  
}