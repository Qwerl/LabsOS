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
    private JButton addButton, startButton, cleanButton, obeyButton;
    private JTextField txtPriority, txtCommand, txtStarterNumber;
    private JLabel lblPriority, lblCommand, lblStarterNumber;
    private int loggersCount = 0;

    public ControlPanel() {
        init();
    }

    private void init() {
        setTitle("ControlPanel");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(328, 270);
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
                            380 * (loggersCount % 4),
                            300 * (loggersCount / 4));//по горизонтали при разрешении 1366x768 помещается 3 логгера
                    loggersCount++;
                }
                else
                    JOptionPane.showMessageDialog(contentPane, "Приоритет не задан!", "Задайте приоритет", JOptionPane.WARNING_MESSAGE);
            }
        });
        contentPane.add(addButton);

        txtPriority = new JTextField();
        txtPriority.setLocation(70, 27);
        txtPriority.setSize(50, 20);
        contentPane.add(txtPriority);

        lblPriority = new JLabel("Priority:");
        lblPriority.setLocation(20, 20);
        lblPriority.setSize(50, 30);
        contentPane.add(lblPriority);

        startButton = new JButton("start all");
        startButton.setLocation(20, 120);
        startButton.setSize(100, 30);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<ThreadStarter> list = factory.getThreadsList();
                list.get(0).startWorkWithResource();//псевдопоток занимает ресурс
                for (int i = 1; i < list.size(); i++) {
                    list.get(i).startThread();
                }
                try {
                    Thread.sleep(50);//небольшая пауза, чтоб точно все потоки стартовали
                } catch (InterruptedException e1) {}
                list.get(0).endWorkWithResource();//псевдопоток освобождает ресурс
            }
        });
        contentPane.add(startButton);

        cleanButton = new JButton("clean");
        cleanButton.setLocation(20, 180);
        cleanButton.setSize(100, 30);
        cleanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<ThreadStarter> list = factory.getThreadsList();
                for (int i = 1; i < list.size(); i++) {
                    list.get(i).getLogger().cleanLogger(); //отчистить txtArea i-го логгера
                }
            }
        });
        contentPane.add(cleanButton);

        txtCommand = new JTextField();
        txtCommand.setLocation(240, 27);
        txtCommand.setSize(50, 20);
        contentPane.add(txtCommand);



        lblCommand = new JLabel("Command:");
        lblCommand.setLocation(160, 20);
        lblCommand.setSize(65, 30);
        contentPane.add(lblCommand);

        txtStarterNumber = new JTextField();
        txtStarterNumber.setLocation(240, 67);
        txtStarterNumber.setSize(50, 20);
        contentPane.add(txtStarterNumber);

        lblStarterNumber = new JLabel("Num:");
        lblStarterNumber.setLocation(160, 60);
        lblStarterNumber.setSize(40, 30);
        contentPane.add(lblStarterNumber);

        obeyButton = new JButton("OBEEEEYYYY!!11");
        obeyButton.setLocation(160, 102);
        obeyButton.setSize(130, 40);
        obeyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (txtCommand.getText().equals("")) { //если строка команды пуста
                    JOptionPane.showMessageDialog(contentPane, "Команда не задана!", "Задайте команду", JOptionPane.WARNING_MESSAGE);
                } else if (txtStarterNumber.getText().equals("")) { //если номер потока не указан
                    JOptionPane.showMessageDialog(contentPane, "Номер не задан!", "Задайте номер", JOptionPane.WARNING_MESSAGE);
                } else if ((Integer.parseInt(txtStarterNumber.getText()) > factory.getThreadsList().size()-1)
                        || (Integer.parseInt(txtStarterNumber.getText()) < 1)) {//если указанный номер не входит в интервал (0;количество потоков)
                    JOptionPane.showMessageDialog(contentPane, "Номер указан неверно!", "Задайте корректный номер", JOptionPane.WARNING_MESSAGE);
                } else { //команда корректна, послать команду потоку
                    factory.getThreadsList().get(Integer.parseInt(txtStarterNumber.getText())).setCommand(txtCommand.getText());
                }
            }
        });
        contentPane.add(obeyButton);
        setVisible(true);
    }

    public static void main(String[] args) {
        ThreadStarter threadStarter = ThreadsFactory.getInstance().newThreadStarter(0);
        threadStarter.getLogger().updateTitle("Controller Logger");
        new ControlPanel();
    }
}