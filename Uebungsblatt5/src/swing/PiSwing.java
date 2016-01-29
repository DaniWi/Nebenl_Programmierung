package swing;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class PiSwing extends JPanel implements PropertyChangeListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel infoLabel;
	private JButton startButton;
	private JButton cancelButton;
	private JProgressBar progressBar;
	private SwingWorker<Double,Integer> workerThread;
	
	/**
	 * Event-Handling for Progress Bar
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("progress")) {
		      int progress = (Integer) evt.getNewValue();
		      progressBar.setValue(progress);
		    }
	}

	/**
	 * PiSwing Constructor
	 * Representing the Panel with GUI Elements
	 */
	public PiSwing() {
	    super(new FlowLayout(FlowLayout.CENTER));

	    // Create the demo's UI.
	    startButton = new JButton("Calculate");
	    startButton.setActionCommand("start");
	    startButton.addActionListener(this);

	    progressBar = new JProgressBar(0, 100);
	    progressBar.setValue(0);

	    cancelButton = new JButton("Cancel");
	    cancelButton.setActionCommand("cancel");
	    cancelButton.addActionListener(this);
	    cancelButton.setEnabled(false);
	    
	    infoLabel = new JLabel("", JLabel.CENTER);

	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(5,1,10,10));
	    panel.add(new JLabel("Calculate Pi", JLabel.CENTER));
	    panel.add(startButton);
	    panel.add(progressBar);
	    panel.add(cancelButton);
	    panel.add(infoLabel);

	    this.add(panel);
	  }
	
	/**
	 * Helper Method to create the GUI
	 */
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Calculate Pi");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    // Create and set up the content pane.
	    JComponent newContentPane = new PiSwing();
	    newContentPane.setOpaque(true); // content panes must be opaque
	    frame.setContentPane(newContentPane);

	    // Display the window.
	    frame.pack();
	    frame.setVisible(true);
	}
	
	/**
	 * Main Method calling invokeLater to create GUI
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		        createAndShowGUI();
		      }
		    });
	}

	/**
	 * ActionListener implementation
	 * Handling the button clicks
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("start")) {
			workerThread = new CalculatePi();
			workerThread.addPropertyChangeListener(this);
			startButton.setEnabled(false);
			cancelButton.setEnabled(true);
			progressBar.setStringPainted(true);
			infoLabel.setText("");
			workerThread.execute();
		}
		else if (e.getActionCommand().equals("cancel")) {
			workerThread.cancel(false);
		}
		else {
			System.out.println("unknown command");
		}
	}
	
	/**
	 * CalculatePi inner class extending SwingWorker
	 * SwingWorker represents a long running task that is executed in a worker thread
	 * NOT the event dispatch thread
	 *
	 */
	class CalculatePi extends SwingWorker<Double, Integer> {

		private double threshold = 1e-5;
		private int numThreads = 4;
		private int indexStep = 4;
		
		@Override
		protected Double doInBackground() throws Exception {
			double cum_result = 0;
			double delta = 1;
			int position = 0;
			while (delta > threshold & !isCancelled()) {
				CalculatePartOfPi[] threads = new CalculatePartOfPi[numThreads];
				for (int i = 0; i < numThreads; i++) {
					threads[i] = new CalculatePartOfPi(position, position + indexStep);
					position += indexStep + 1;
					threads[i].start();
				}
				
				for (int i = 0; i < numThreads; i++) {
					cum_result += threads[i].get();
				}
				
				delta = Math.abs(4*cum_result - Math.PI);
				double progress = 100 * (threshold / delta);
				setProgress((int)progress);
				//progressBar.setValue((int)progress);
			}
			
			return 4*cum_result;
		}
		
		/**
		 * called after doInBackground finished or after cancellation
		 */
		@Override
		protected void done() {
			startButton.setEnabled(true);
			cancelButton.setEnabled(false);
			progressBar.setStringPainted(false);
			//progressBar.setValue(0);
			if (!isCancelled()) {
				try {
					Double result = get();
					infoLabel.setText(result.toString());
				}
				catch (InterruptedException ignored) {}
				catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			else {
				infoLabel.setText("Cancelled");
			}
		}
		
	}

}
