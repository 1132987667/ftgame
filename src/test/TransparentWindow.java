package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TransparentWindow extends JFrame {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                	UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                TransparentWindow transparentWindow = new TransparentWindow();
                transparentWindow.setVisible(true);
                transparentWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                transparentWindow.setVisible(true);
            }
        });
    }

    JMenuBar menuBar;
    TransparentCanvas canvas;
    JComponent pane;
    JScrollPane scrollPane;

    public TransparentWindow() {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(new Dimension(500, 500));
        setLocationRelativeTo(null); //set location at the center
        setTitle("Transparency");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu help = new JMenu("Help");
        menuBar.add(help);
        canvas = new TransparentCanvas();
        pane = (JComponent) this.getContentPane();
        pane.add(canvas);
        scrollPane = new JScrollPane(canvas);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        pane.add(scrollPane, BorderLayout.CENTER);
    }

    class TransparentCanvas extends JComponent {

        public TransparentCanvas() {
            super();
            setPreferredSize(new Dimension(500, 500));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2D = (Graphics2D) g;
            g2D.setColor(new Color(240, 240, 240, 128));
            g2D.fillRect(0, 0, getWidth(), getHeight());
            g2D.setColor(Color.blue);
            g2D.fillOval(200, 150, 100, 100);
            g2D.dispose();
        }
    }
}