package lw1a;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Window implements ActionListener, ChangeListener {
    final private JFrame frame;
    final private JSlider slider;
    final private JButton button;
    final private JSpinner spinner0;
    final private JSpinner spinner1;
    private MyThread th1, th2;
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==button) {
            th1 = new MyThread(slider,10);
            th2 = new MyThread(slider,90);
            th1.setPriority(1);
            th2.setPriority(1);
            th1.setDaemon(true);
            th2.setDaemon(true);
            th1.start();
            th2.start();
            button.setEnabled(false);
            spinner0.setEnabled(true);
            spinner1.setEnabled(true);
        }
    }
    public void stateChanged(ChangeEvent e) {
        if(e.getSource()==spinner0) {
            th1.setPriority((Integer)spinner0.getValue());
        }
        else if (e.getSource()==spinner1) {
            th2.setPriority((Integer)spinner1.getValue());
        }
        System.out.println("th1: " + th1.getPriority());
        System.out.println("th2: " + th2.getPriority());
    }
    Window (String name, int width, int height) {
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

        button = new JButton("Start!");
        button.setBounds(225, 215, 150, 75);
        button.addActionListener(this);
        frame.add(button);

        spinner0 = new JSpinner();
        spinner0.setModel(new SpinnerNumberModel(1, Thread.MIN_PRIORITY, Thread.MAX_PRIORITY, 1));
        spinner0.setValue(1);
        spinner0.setBounds(120, 110, 90, 90);
        spinner0.addChangeListener(this);
        spinner0.setEnabled(false);

        spinner1 = new JSpinner();
        spinner1.setModel(new SpinnerNumberModel(1, Thread.MIN_PRIORITY, Thread.MAX_PRIORITY, 1));
        spinner1.setValue(1);
        spinner1.setBounds(390, 110, 90, 90);
        spinner1.addChangeListener(this);
        spinner1.setEnabled(false);


        frame.add(spinner0);
        frame.add(spinner1);

        frame.setVisible(true);

    }
}
class MyThread extends Thread{
    private final JSlider slider;
    private int goal;
    MyThread(JSlider slider, int goal){
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
                this.sleep(100);
            } catch (Exception e) {
                this.interrupt();
                System.out.println(e);
            }
            }
        }
    }
}