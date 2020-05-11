
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HMM extends JFrame implements ActionListener {

    private Matrix_Propabilities t1;

    static int counter = 0;
    static int counter1 = 0;
    static int counter2 = 0;
    private int deviation_between_real_noice = 0;
    private int deviation_between_real_HMM_Filter = 0;
    private ArrayList<String> pragmatiki = new ArrayList<String>();
    private ArrayList<String> metritheisa = new ArrayList<String>();
    private ArrayList<String> HMM_Filter = new ArrayList<String>();

    private String pr = "";
    private String metr = "";
    private String filtr = "";

    private JPanel row1;
    private JLabel Real_measuements_label;
    private JTextField Real_measuements;

    private JPanel row2;
    private JButton ReadFiles;
    private JButton filter;
    private JButton ob_tr_prob;
    private JButton calculate_deviation_values;

    private JPanel row3;
    private JLabel Noise_measuements_label;
    private JTextField Noise_measuements;

    private JPanel row4;
    private JLabel Filtered_measuements_label;
    private JTextField Filtered_measuements;
    
        private JPanel row5;
    private JLabel Running_TIme_HMM_label;
    private JTextField Running_TIme_HMM;

    double[][] Observation_probabilities = {
        {0.5, 0.5, 0.0, 0.0, 0.0},
        {0.25, 0.5, 0.25, 0.0, 0.0},
        {0.0, 0.25, 0.5, 0.25, 0.0},
        {0.0, 0.0, 0.25, 0.5, 0.25},
        {0.0, 0.0, 0.0, 0.5, 0.5}
    };
    // Transition_probabilities
    double[][] Transition_probabilities = {
        {0.1, 0.7, 0.2, 0, 0},
        {0, 0.1, 0.7, 0, 0},
        {0, 0, 0.2, 0.1, 0.2},
        {0, 0.7, 0.2, 0.1, 0.9},
        {0, 0.7, 0.2, 0.7, 0.1},};

    double[] initial_state = {1, 0, 0, 0, 0};
    int[] states = new int[12];

    public void GUI() {
        row1 = new JPanel();
        Real_measuements_label = new JLabel("Real measurmnets :", JLabel.RIGHT);
        Real_measuements = new JTextField(50);

        row2 = new JPanel();
        ReadFiles = new JButton("Read Files");
        filter = new JButton("Filter");
        ob_tr_prob = new JButton("show obser/trans probabilities");
        calculate_deviation_values = new JButton("calculate deviation values");

        row3 = new JPanel();
        Noise_measuements_label = new JLabel("Noise measurments :", JLabel.RIGHT);
        Noise_measuements = new JTextField(50);

        row4 = new JPanel();
        Filtered_measuements_label = new JLabel(" Filtered measurments :", JLabel.RIGHT);
        Filtered_measuements = new JTextField(50);
        
         row5 = new JPanel();
        Running_TIme_HMM_label = new JLabel(" Running Time of HMM Filtering :", JLabel.RIGHT);
         Running_TIme_HMM = new JTextField(50);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Container pane = getContentPane();
        GridLayout layout = new GridLayout(5, 1);
        pane.setLayout(layout);
        //Prwti grammi
        FlowLayout layout1 = new FlowLayout();
        row1.setLayout(layout1);
        row1.add(Real_measuements_label);
        row1.add(Real_measuements);

//Deuteri grammi
        FlowLayout layout2 = new FlowLayout();
        row2.setLayout(layout2);
        row2.add(ReadFiles);
        row2.add(filter);
        row2.add(ob_tr_prob);
        row2.add(calculate_deviation_values);
//Triti grammi
        FlowLayout layout3 = new FlowLayout();
        row3.setLayout(layout3);
        row3.add(Noise_measuements_label);
        row3.add(Noise_measuements);
        //Tetarti grammi 
        FlowLayout layout4 = new FlowLayout();
        row4.setLayout(layout4);
        row4.add(Filtered_measuements_label);
        row4.add(Filtered_measuements);

         FlowLayout layout5 = new FlowLayout();
        row5.setLayout(layout4);
        row5.add(Running_TIme_HMM_label);
        row5.add(Running_TIme_HMM);
        
        pane.add(row1);
        pane.add(row3);
        pane.add(row4);
         pane.add(row5);
        pane.add(row2);

        setContentPane(pane);
        pack();
        ReadFiles.addActionListener(this);
        filter.addActionListener(this);
        ob_tr_prob.addActionListener(this);
        calculate_deviation_values.addActionListener(this);
    }

    public void actionPerformed(ActionEvent evt) {

        Object source = evt.getSource();
        if (source == ReadFiles) {
            counter++;
            if (counter == 1) {
                ReadMasurments();
                Real_measuements.setText(pr);
                Noise_measuements.setText(metr);
            }

        } else if (source == ob_tr_prob) {

            Set_Transition_Propabilities();

        } else if (source == calculate_deviation_values) {
            Calculate_Deviation_Values();
        } else if (source == filter) {
            counter1++;
            if (Filtered_measuements.getText().equals("")) {
                if (counter == 0) {
                    JOptionPane.showMessageDialog(null, "Press the ReadFiles Button First", "Alert", JOptionPane.ERROR_MESSAGE);
                } else {
                        testing();
                }
            
            }

        }

    }

    public void Calculate_Deviation_Values() {
        if (counter == 0 && counter1 != 0) {
            JOptionPane.showMessageDialog(null, "Press the ReadFiles Button First", "Alert", JOptionPane.ERROR_MESSAGE);
        }

        if (counter == 0 && counter1 == 0) {
            JOptionPane.showMessageDialog(null, "Press the Filter and ReadFiles Buttons First : ", "Alert2", JOptionPane.ERROR_MESSAGE);
        }

        if (counter != 0 && counter1 == 0) {
            JOptionPane.showMessageDialog(null, "Press the Filter Button First", "Alert1", JOptionPane.ERROR_MESSAGE);
        } else {
            String[] real = new String[pragmatiki.size()];
            real = pragmatiki.toArray(real);

            String[] measured = new String[metritheisa.size()];
            measured = metritheisa.toArray(measured);

            String[] HMMFILTER = new String[HMM_Filter.size()];
            HMMFILTER = HMM_Filter.toArray(HMMFILTER);

            int[] integer_real = new int[real.length];
            int[] integer_measured = new int[measured.length];
            int[] integer_HMMFILTER = new int[HMMFILTER.length];

            for (int i = 0; i < integer_real.length; i++) {

                integer_real[i] = Integer.parseInt(real[i]);
                integer_measured[i] = Integer.parseInt(measured[i]);
                integer_HMMFILTER[i] = Integer.parseInt(HMMFILTER[i]);

            }

            for (int i = 0; i < integer_real.length; i++) {

                deviation_between_real_noice = deviation_between_real_noice + Math.abs(integer_real[i] - integer_measured[i]);
                deviation_between_real_HMM_Filter = deviation_between_real_HMM_Filter + Math.abs(integer_real[i] - integer_HMMFILTER[i]);
            }
            counter2++;

            if (counter != 0 && counter1 != 0) {
                JOptionPane.showMessageDialog(null, "Deviation between noise and real measurement : " +(double) ((double)deviation_between_real_noice/(double)pragmatiki.size() )+ " \n " + "Deviation between HMM filter result and real measurement : " + (double)((double)deviation_between_real_HMM_Filter/(double)pragmatiki.size() ), "Calculated deviation values", JOptionPane.INFORMATION_MESSAGE);
            }

            deviation_between_real_noice = 0;
            deviation_between_real_HMM_Filter = 0;
        }
    }

    public void ReadMasurments() {
        try {
            Scanner scanFile = new Scanner(new FileReader("measurements.txt"));
            String theWord;
            int number = 0;
            while (scanFile.hasNext()) {
                theWord = scanFile.next();
                number++;
                if (number % 2 == 0) {
                    metritheisa.add(theWord);
                } else {
                    pragmatiki.add(theWord);
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Read error");
        }

        System.out.println(pragmatiki);
        System.out.println(metritheisa);
        for (int i = 0; i < pragmatiki.size(); i++) {
            pr = pr + pragmatiki.get(i);
            pr = pr + " ";
        }
        for (int i = 0; i < metritheisa.size(); i++) {
            metr = metr + metritheisa.get(i);
            metr = metr + " ";
        }

    }

    public void Set_Transition_Propabilities() {
        t1 = new Matrix_Propabilities();

    }

    public void testing() {
         JOptionPane.showMessageDialog(null, "Filtering will take a few minutes ", "HHM Filtering Information", JOptionPane.INFORMATION_MESSAGE);
        int metritis=0;
        double max = -1;
        double temp = 0.0;
        System.out.println("start");
        long startTime = System.nanoTime();
        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 5; b++) {//1
                for (int c = 0; c < 5; c++) {
                    for (int d = 0; d < 5; d++) {//2
                        for (int e = 0; e < 5; e++) {
                            for (int f = 0; f < 5; f++) {//3
                                for (int g = 0; g < 5; g++) {
                                    for (int h = 0; h < 5; h++) {//4
                                        for (int i = 0; i < 5; i++) {
                                            for (int j = 0; j < 5; j++) {//5
                                                for (int k = 0; k < 5; k++) {
                                                    for (int l = 0; l < 5; l++) {//6
                                                        metritis++;
                                                        temp = (double) initial_state[a] * Transition_probabilities[a][b] * Observation_probabilities[a][b] * Transition_probabilities[b][c] * Observation_probabilities[b][c] * Transition_probabilities[c][d] * Observation_probabilities[c][d] * Transition_probabilities[d][e] * Observation_probabilities[d][e] * Transition_probabilities[e][f] * Observation_probabilities[e][f] * Transition_probabilities[f][g] * Observation_probabilities[f][g] * Transition_probabilities[g][h] * Observation_probabilities[g][h] * Transition_probabilities[h][i] * Observation_probabilities[h][i] * Transition_probabilities[i][j] * Observation_probabilities[i][j] * Transition_probabilities[j][k] * Observation_probabilities[j][k] * Transition_probabilities[k][l] * Observation_probabilities[k][l];
                                                        if (temp > max) {
                                                            max = temp;
                                                            System.out.println("max-->" + max);
                                                            states[0] = a;
                                                            states[1] = b;
                                                            states[2] = c;
                                                            states[3] = d;
                                                            states[4] = e;
                                                            states[5] = f;
                                                            states[6] = g;
                                                            states[7] = h;
                                                            states[8] = i;
                                                            states[9] = j;
                                                            states[10] = k;
                                                            states[11] = l;

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("finish");
        long endTime   = System.nanoTime();
        long totalTime = (long) ((endTime - startTime)*(0.000000001));
        System.out.println("totalTime (sec);"+(totalTime*0.000000001));
        System.out.println("metritis -->"+metritis);
        for (int w = 0; w < 12; w++) {
            System.out.println(states[w]);
            HMM_Filter.add(Integer.toString(states[w]));
        }

        for (int i = 0; i < states.length; i++) {
            filtr = filtr + Integer.toString(states[i]);
            filtr = filtr + " ";
        }
        Filtered_measuements.setText(filtr);
        String str = Long.toString(totalTime);
        Running_TIme_HMM.setText(str+" (sec)");
        System.out.println(HMM_Filter);

    }

    public static void main(String[] args) {
        HMM t1 = new HMM();
        t1.GUI();
    }
}
