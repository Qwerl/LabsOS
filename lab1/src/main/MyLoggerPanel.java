package lab1.main;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.util.Date;
import java.util.Random;

public class MyLoggerPanel extends JFrame {

    private int width = 380;
    private int height = 300;
    private JTextArea textArea = null;
    private JScrollPane pane = null;
    private DefaultCaret caret = null;

    /**
     * конструктор
     *
     * создаёт логгер в случайном положении на экране(в пределах 1000, 500)
     * @param id порядковый номер ThreadStarter' а
     */
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
     * конструктор
     *
     * создаёт логгер с координатами locationX, locationY
     * @param id порядковый номер ThreadStarter' а
     * @param locationX координата по x
     * @param locationY координата по y
     */
    public MyLoggerPanel (int id, int locationX, int locationY) {
        setTitle(Integer.toString(id));
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(locationX, locationY);
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
     */
    public void addLog(String str) {
        Date date = new Date();
        textArea.append(date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "   " +  str + "\n");
    }
    public void updateTitle(int id, String str) {
        this.setTitle(id + " " + str);
    }
    public void updateTitle(String str) {
        this.setTitle(str);
    }
    public void cleanLogger(){
        textArea.setText(null);
    }
}