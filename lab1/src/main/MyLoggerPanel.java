package lab1.main;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.util.Random;

public class MyLoggerPanel extends JFrame {

    private int width = 320;
    private int height = 300;
    private JTextArea textArea = null;
    private JScrollPane pane = null;
    private DefaultCaret caret = null;
    public MyLoggerPanel (int id) {
        setTitle(Integer.toString(id));
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(new Random().nextInt(1000), new Random().nextInt(500));
        textArea = new JTextArea();
        textArea.setEditable(false);
        pane = new JScrollPane(textArea);
        caret =(DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        getContentPane().add(pane);
        setVisible(true);
    }

    /**
     * добавить строку в лог
     * @param str
     */
    public void addLog(String str) {
        textArea.append(str + "\n");
    }
    public void updateTitle(int id, String str) {
        this.setTitle(id + " " + str);
    }
}
