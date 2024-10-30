
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RSADemo extends JFrame {
	
	private static final int MAC_OS_WASTED_HEIGHT = 93;
	
	private BigInteger p, q, N, M, exp, d;
	private BigInteger base = BigInteger.valueOf(MAC_OS_WASTED_HEIGHT + 2);
	private static final Integer[] BIT_CHOICES = {2048, 1024, 512, 256, 128};
	private static final String[] FRIENDS = {"Serena Zhao", "John Pau", "Felix Alexander"};
	
	JPanel mainPanel = new JPanel(new GridLayout(2, 3));
	JPanel inputPanel = new JPanel();
	JPanel rsaPanel = new JPanel(new GridLayout(2, 1));
	JPanel outputPanel = new JPanel();
	
	JPanel bottomLeftPanel = new JPanel(new GridLayout(4, 1));
	JPanel namePanel = new JPanel(new GridLayout(2, 1));
	JPanel nPanel = new JPanel(new GridLayout(2, 1));
	JPanel ePanel = new JPanel(new GridLayout(2, 1));
	JPanel dPanel = new JPanel(new GridLayout(2, 1));

	JTextField nameField = new JTextField();
	JTextField nField = new JTextField();
	JTextField eField = new JTextField();
	JTextField dField = new JTextField();
	
	JPanel rsaButtons = new JPanel(new GridLayout(1, 3));
	JPanel lasButtons = new JPanel(new GridLayout(1, 3));
	
	JTextArea inputArea = new JTextArea(10, 20);
	JTextArea outputArea = new JTextArea(10, 20);
	JTextArea infoArea = new JTextArea(10, 20);
	
	JPanel bottomCenterPanel = new JPanel(new GridLayout(2, 1));
	JPanel infoPanel = new JPanel();

	private Map<String, List<Integer>> data = new HashMap<>();

    public RSADemo() {
    	int screenWidth = 810;
		int screenHeight = 480;
		
		// Main window panel
		
		JComboBox<Integer> bitLengths = new JComboBox<>(BIT_CHOICES);
		JComboBox<String> contacts = new JComboBox<>(FRIENDS);
		
		mainPanel.add(inputPanel);
		mainPanel.add(rsaPanel);
		mainPanel.add(outputPanel);
		
		// Remove the following when user input for N & e is implemented
		generate((int) bitLengths.getSelectedItem(), new Random(3447679086515839964L));
		nField.setEditable(false);
		dField.setEditable(false);
		
		int big = (int) Math.round(screenWidth / 10.0);
		int mid = (int) Math.round(screenHeight / 20.0);
		int small = (int) Math.round(screenHeight / 30.0);
		
		JScrollBar nameBar = new JScrollBar(JScrollBar.HORIZONTAL);
		nameField.setText(String.valueOf(contacts.getSelectedItem()));
		BoundedRangeModel nameBrm = nameField.getHorizontalVisibility();
	    nameBar.setModel(nameBrm);
		namePanel.add(nameField);
		namePanel.add(nameBar);
		namePanel.setBorder(BorderFactory.createEmptyBorder(0, 12, small, 12));
		
		JScrollBar nBar = new JScrollBar(JScrollBar.HORIZONTAL);
		nField.setText(N.toString());
		BoundedRangeModel nBrm = nField.getHorizontalVisibility();
	    nBar.setModel(nBrm);
		nPanel.add(nField);
		nPanel.add(nBar);
		nPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, small, 12));

		JScrollBar eBar = new JScrollBar(JScrollBar.HORIZONTAL);
		eField.setText(exp.toString());
		BoundedRangeModel eBrm = eField.getHorizontalVisibility();
	    eBar.setModel(eBrm);
		ePanel.add(eField);
		ePanel.add(eBar);
		ePanel.setBorder(BorderFactory.createEmptyBorder(0, 12, small, 12));

		JScrollBar dBar = new JScrollBar(JScrollBar.HORIZONTAL);
		dField.setText(d.toString());
		BoundedRangeModel dBrm = dField.getHorizontalVisibility();
	    dBar.setModel(dBrm);
		dPanel.add(dField);
		dPanel.add(dBar);
		dPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, small, 12));
		
		bottomLeftPanel.add(namePanel);
		bottomLeftPanel.add(nPanel);
		bottomLeftPanel.add(ePanel);
		bottomLeftPanel.add(dPanel);
		mainPanel.add(bottomLeftPanel);
		
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);
		inputArea.setDocument(new RSATextAreaCharacterLimit(getLimit(N)));
		JScrollPane inputPane = new JScrollPane(inputArea);
		inputPanel.add(inputPane);
		inputPanel.setBorder(BorderFactory.createEmptyBorder(mid, big, mid, big));
		
		outputArea.setLineWrap(true);
		outputArea.setWrapStyleWord(true);
		outputArea.setEditable(false);
		JScrollPane outputPane = new JScrollPane(outputArea);
		outputPanel.add(outputPane);
		outputPanel.setBorder(BorderFactory.createEmptyBorder(mid, big, mid, big));
		
		infoArea.setEditable(false);
		infoArea.setLineWrap(true);
		infoArea.setWrapStyleWord(true);
		infoArea.setText("Session started on " + LocalDateTime.now());
		JScrollPane infoPane = new JScrollPane(infoArea);

		JButton encryptButton = new JButton(new AbstractAction("Encrypt") {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String s = inputArea.getText();
	    		s = s.replace(String.valueOf((char) (8217)), String.valueOf((char) (39)));
	            try {
		            N = new BigInteger(nField.getText());
		    		exp = new BigInteger(eField.getText());
		    		if (eField.getText().equals("65537")) {
			    		outputArea.setText(RSAMethods.encryptString(s, N, exp));
			        	printInfo(infoArea);
		    		} else {
		    			d = exp.modInverse(M);
		    			dField.setText(d.toString());
		    			outputArea.setText(RSAMethods.encryptString(s, N, exp));
			        	printInfo(infoArea);
		    		}
	            } catch (NumberFormatException err) {
	            	outputArea.setText("");
	            	infoArea.setText("Please enter a number for N and e.");
	            } catch (ArithmeticException err) {
	            	outputArea.setText("");
	            	printNonInvertibleInfo(infoArea);
	            }
	        }
	    });

		JButton decryptButton = new JButton(new AbstractAction("Decrypt") {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String s = inputArea.getText();
	            try {
		            N = new BigInteger(nField.getText());
		    		d = new BigInteger(dField.getText());
		    		exp = new BigInteger(eField.getText());
		    		if (d.compareTo(exp.modInverse(M)) == 0) {
			    		outputArea.setText(RSAMethods.decryptString(s, N, d));
			        	printInfo(infoArea);
		    		} else {
		    			outputArea.setText("");
		    			infoArea.setText("N, e, or d are not correct values.");
		    		}
	            } catch (NumberFormatException err) {
	            	outputArea.setText("");
	            	infoArea.setText("Please enter a number for N and d.");
	            } catch (ArithmeticException err) {
	            	outputArea.setText("");
	            	printNonInvertibleInfo(infoArea);
	            }
	        }
	    });
		
		JButton generateButton = new JButton(new AbstractAction("New") {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            generate((int) bitLengths.getSelectedItem(), new Random());
	            int newLimit = getLimit(N);
	            String currentText = inputArea.getText();
	            if (currentText.length() > newLimit) {
	            	currentText = currentText.substring(0, newLimit);
	            }
	            inputArea.setDocument(new RSATextAreaCharacterLimit(newLimit));
	            inputArea.setText(currentText);
	            printInfo(infoArea);
	            update(nField, eField, dField);
	        }
	    });
		
		JButton loadButton = new JButton(new AbstractAction("Load") {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // TODO
	        }
	    });
		
		JButton saveButton = new JButton(new AbstractAction("Save") {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // TODO
	        }
	    });
		
		rsaButtons.add(encryptButton);
		rsaButtons.add(decryptButton);
		rsaButtons.add(generateButton);
		
		rsaPanel.add(bitLengths);
		rsaPanel.add(rsaButtons);
		
		lasButtons.add(loadButton);
		lasButtons.add(saveButton);
		
		bottomCenterPanel.add(contacts);
		bottomCenterPanel.add(lasButtons);
		mainPanel.add(bottomCenterPanel);
		
		infoPanel.add(infoPane);
		infoPanel.setBorder(BorderFactory.createEmptyBorder(mid, big, mid, big));
		
		mainPanel.add(infoPanel);
		
		this.add(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(screenWidth, screenHeight);
		this.setTitle("Demo Application");
		this.setResizable(false);
		this.setVisible(true);
    }
    
    private void generate(int bitLength, Random r) {
		p = BigInteger.probablePrime(bitLength, r);
		q = BigInteger.probablePrime(bitLength, r);
		N = p.multiply(q);
		M = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		
		exp = BigInteger.valueOf(65537);
		d = exp.modInverse(M);
    }
    
    private void printInfo(JTextArea jta) {
    	jta.setText("p: \n" + p + "\nq: \n" + q
    			+ "\nN: \n" + N + "\n(p - 1)(q - 1): \n" + M
    			+ "\ne: " + exp + "\nd: \n" + d
    			+ "\ngcd(e, (p - 1)(q - 1)): " + exp.gcd(d));
    }
    
    private void printNonInvertibleInfo(JTextArea jta) {
    	jta.setText("p: \n" + p + "\nq: \n" + q
    			+ "\nN: \n" + N + "\n(p - 1)(q - 1): \n" + M
    			+ "\ne: " + exp + "\ngcd(e, (p - 1)(q - 1)): " + exp.gcd(M) + "\nd: undefined"
    			+ "\ne is not invertible mod (p-1)(q-1), please choose another e.");
    }
    
    private void update(JTextField jtf1, JTextField jtf2, JTextField jtf3) {
    	jtf1.setText(N.toString());
    	jtf2.setText(exp.toString());
    	jtf3.setText(d.toString());
    }
    
    private int getLimit(BigInteger N) {
    	int limit = 0;
		while (base.pow(limit).compareTo(N) < 0) {
			limit++;
		}
		return limit + 1;
    }
    
	public static void main(String[] args) {
		new RSADemo();
    }
}
