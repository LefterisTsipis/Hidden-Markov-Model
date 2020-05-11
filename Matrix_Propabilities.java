
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LETERIS
 */
public class Matrix_Propabilities extends JFrame implements ActionListener {
     private JPanel row1;
    private JLabel Observation_probabilities_label;
    private JScrollPane scrollPane1;
    private  JTable jt1;
    
    private JPanel row2;
    private JLabel Transition_probabilities_label;
    private JScrollPane scrollPane2;
     private  JTable jt2;
  String[] Colnames = {"Pij", "0", "1", "2 ", "3", "4"};
    JFrame f;
        Object[][] Observation_probabilities = {
        {"0", 0.5, 0.5, 0, 0, 0},
        {"1", 0.25, 0.5, 0.25, 0, 0},
        {"2", 0, 0.25, 0.5, 0.25, 0},
        {"3", 0, 0, 0.25, 0.5, 0.25},
        {"4", 0, 0, 0, 0.5, 0.5}
        };
       // Transition_probabilities
         Object[][] Transition_probabilities = {{"0", 0.1, 0.7, 0.2, 0, 0},
        {"1", 0, 0.1, 0.7, 0, 0},
        {"2", 0, 0, 0.2, 0.1, 0.2},
        {"3", 0, 0.7, 0.2, 0.1, 0.9},
        {"4", 0, 0.7, 0.2, 0.7, 0.1},};
         
  
    Matrix_Propabilities() {
    row1 = new JPanel();
    Observation_probabilities_label = new JLabel("Observation probabilities :", JLabel.RIGHT);    
    jt1 = new JTable(Observation_probabilities, Colnames); 
    
     row2 = new JPanel();
     Transition_probabilities_label =new JLabel("Transition probabilities :", JLabel.RIGHT); 
     jt2 = new JTable(Transition_probabilities, Colnames); 
     
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Container pane = getContentPane();
        GridLayout layout = new GridLayout(2, 1);
        pane.setLayout(layout);
        
        
        //Prwti grammi
        FlowLayout layout1 = new FlowLayout();
        row1.setLayout(layout1);
        scrollPane1=new JScrollPane(jt2);
        row1.add(Transition_probabilities_label);
        row1.add(scrollPane1);

//Deuteri grammi
        FlowLayout layout2 = new FlowLayout();
        row2.setLayout(layout2);
        scrollPane2=new JScrollPane(jt1);
        row2.add(Observation_probabilities_label);
        
        row2.add(scrollPane2);
        
       pane.add(row1);
     
        pane.add(row2);

        setContentPane(pane);
        pack();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}