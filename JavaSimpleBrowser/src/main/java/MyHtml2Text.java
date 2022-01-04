import java.io.IOException;
import java.io.Reader;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

//This class converts html to text for searching
public class MyHtml2Text extends HTMLEditorKit.ParserCallback {
    StringBuffer s;
    public MyHtml2Text() {}
    public void parse(Reader in) throws IOException {
        s = new StringBuffer();
        ParserDelegator delegator = new ParserDelegator();
        delegator.parse(in, this, Boolean.TRUE);
    }
    public void handleText(char[] text, int pos) {
        s.append(text);
        s.append("\n");
    }
    public String getText() {
        return s.toString();
    }
}