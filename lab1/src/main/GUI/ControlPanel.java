package lab1.main.GUI;

import lab1.main.ThreadStarter;
import lab1.main.ThreadsFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ControlPanel extends JFrame {
    private ThreadsFactory factory = ThreadsFactory.getInstance();
    private JPanel contentPane;
    private JButton addButton, startButton, cleanButton;
    private JTextField txtPriority;
    private JLabel lblPriority;
    private int loggersCount = 0;

    public ControlPanel() {
        init();
    }

    private void init() {
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(148, 270);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        addButton = new JButton("add");
        addButton.setLocation(20, 62);
        addButton.setSize(100, 30);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtPriority.getText().equals("")) {
                    factory.newThreadStarter(Integer.parseInt(txtPriority.getText()),
                            380*(loggersCount%3) , 300*(loggersCount/3));//по горизонтали при разрешении 1366x768 помещается 3 логгера
                    loggersCount++;
                }
                else {
                    JOptionPane.showMessageDialog(contentPane, "Приоритет не задан.", "WARNING", JOptionPane.ERROR_MESSAGE);;
                }
            }
        });

        txtPriority = new JTextField();
        txtPriority.setLocation(70, 27);
        txtPriority.setSize(50, 20);
        contentPane.add(txtPriority);

        lblPriority = new JLabel("Priority:");
        lblPriority.setLocation(20, 20);
        lblPriority.setSize(80, 30);
        contentPane.add(lblPriority);

        startButton = new JButton("start all");
        startButton.setLocation(20, 120);
        startButton.setSize(100, 30);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<ThreadStarter> list = factory.getThreadsList();
                list.get(0).startWorkWithResource();//псевдопоток занимает ресурс
                for (int i = 1; i < list.size(); i++) {
                    ThreadStarter threadStarter = list.get(i);
                    threadStarter.startThread();
                }
                try {
                    Thread.sleep(50);//небольшая пауза, чтоб точно все потоки стартовали
                } catch (InterruptedException e1) {}
                list.get(0).endWorkWithResource();//псевдопоток освобождает ресурс
            }
        });

        cleanButton = new JButton("clean");
        cleanButton.setLocation(20, 180);
        cleanButton.setSize(100, 30);
        cleanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<ThreadStarter> list = factory.getThreadsList();
                for (int i = 1; i < list.size(); i++) {
                    ThreadStarter threadStarter = list.get(i);
                    threadStarter.getLogger().cleanLogger();
                }
            }
        });

        contentPane.add(addButton);
        contentPane.add(startButton);
        contentPane.add(cleanButton);
    }

    public static void main(String[] args) {
        ThreadStarter threadStarter = ThreadsFactory.getInstance().newThreadStarter(0);
        threadStarter.getLogger().updateTitle("Controller Logger");
        new ControlPanel();
    }
}