package lw1b;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Window1 implements ActionListener{
    int semafor = 1;

    final private JFrame frame;
    final private JSlider slider;
    final private JButton buttonOK1;
    final private JButton buttonOK2;
    final private JButton buttonStop1;
    final private JButton buttonStop2;
    final private JOptionPane message;
    private MyThread1 th1, th2;

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==buttonOK1){
            if(semafor==0){
                JOptionPane.showMessageDialog(null, "Семафор знаходиться в положенні \"зайнято\"", "", 1);
            }
            else{
                th1 = new MyThread1(slider,10);
                th1.setPriority(1);
                th1.start();
                semafor = 0;
                buttonStop1.setEnabled(true);
                buttonStop2.setEnabled(false);
            }
        }
        else if (e.getSource()==buttonOK2) {
            if(semafor==0){
                JOptionPane.showMessageDialog(null, "Семафор знаходиться в положенні \"зайнято\"", "", 1);
            }
            else{
                th2 = new MyThread1(slider,90);
                th2.setPriority(10);
                th2.start();
                semafor = 0;
                buttonStop2.setEnabled(true);
                buttonStop1.setEnabled(false);
            }
        }
        else if (e.getSource()==buttonStop1){
            th1.interrupt();
            semafor = 1;
            buttonStop1.setEnabled(false);
        }
        else if (e.getSource()==buttonStop2){
            th2.interrupt();
            semafor = 1;
            buttonStop2.setEnabled(false);
        }
    }
    Window1 (String name, int width, int height) {
        frame = new JFrame(name);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);

        slider = new JSlider(0, 100, 50);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(10);
        slider.setPaintLabels(true);
        slider.setBounds(30, 30, 525, 75);
        frame.add(slider);

        buttonOK1 = new JButton("ПУСК1!");
        buttonOK1.setBounds(40, 120, 220, 75);
        buttonOK1.addActionListener(this);
        frame.add(buttonOK1);

        buttonOK2= new JButton("ПУСК2!");
        buttonOK2.setBounds(330, 120, 220, 75);
        buttonOK2.addActionListener(this);
        frame.add(buttonOK2);

        buttonStop1= new JButton("СТОП1!");
        buttonStop1.setBounds(40, 220, 220, 75);
        buttonStop1.addActionListener(this);
        frame.add(buttonStop1);

        buttonStop2= new JButton("СТОП2!");
        buttonStop2.setBounds(330, 220, 220, 75);
        buttonStop2.addActionListener(this);
        frame.add(buttonStop2);

        buttonStop1.setEnabled(false);
        buttonStop2.setEnabled(false);

        message = new JOptionPane();

        frame.setVisible(true);

    }
}
class MyThread1 extends Thread{
    private final JSlider slider;
    private int goal;
    MyThread1(JSlider slider, int goal){
        this.goal=goal;
        this.slider=slider;
    }
    @Override
    public void run() {
        while(!this.isInterrupted()) {
            synchronized (slider){
            if (goal == 10 && slider.getValue()>10) {slider.setValue(slider.getValue() - 5);}
            else if (goal == 90 && slider.getValue()<90) {slider.setValue(slider.getValue() + 5);}
            try {
                this.sleep(50);
            } catch (Exception e) {
                this.interrupt();
                System.out.println(e);
            }
            }
        }
    }
}