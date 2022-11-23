package sanzol.aitrader.ui.forms;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import sanzol.aitrader.be.config.Constants;
import sanzol.aitrader.ui.config.Styles;
import sanzol.aitrader.ui.controls.CtrlError;
import sanzol.util.log.LogListener;
import sanzol.util.log.LogService;

public class FrmLogs extends JFrame implements LogListener
{
	private static final long serialVersionUID = 1L;

	private static final String TITLE = Constants.APP_NAME + " - Logs";

	private static FrmLogs myJFrame = null;

    DefaultTableModel tableModel;

    private CtrlError ctrlError;

	private JPanel pnlContent;
	private JPanel pnlStatusBar;
	private JTextField txtWithdrawal;
	private JTextArea txtResult;

	public FrmLogs()
	{
		initComponents();

		onLogUpdate();
		LogService.attachRefreshObserver(this);
	}

	private void initComponents()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 760, 500);
		setMinimumSize(new Dimension(880, 400));
		setTitle(TITLE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmLogs.class.getResource("/resources/bug.png")));
		setLocationRelativeTo(null);
		pnlContent = new JPanel();
		pnlStatusBar = new JPanel();
		pnlStatusBar.setBorder(Styles.BORDER_UP);

		JLabel lblWithdrawal = new JLabel();
		lblWithdrawal.setText("Withdrawal");

		txtWithdrawal = new JTextField();
		txtWithdrawal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtWithdrawal.setForeground(Styles.COLOR_TEXT_ALT1);
		txtWithdrawal.setEditable(false);

		ctrlError = new CtrlError();

        JScrollPane scrollPane = new JScrollPane();

        GroupLayout pnlContentLayout = new GroupLayout(pnlContent);
        pnlContentLayout.setHorizontalGroup(
        	pnlContentLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(pnlContentLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
        			.addContainerGap())
        );
        pnlContentLayout.setVerticalGroup(
        	pnlContentLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(pnlContentLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
        			.addContainerGap())
        );

        txtResult = new JTextArea();
		txtResult.setFont(new Font("Courier New", Font.PLAIN, 12));
		txtResult.setBackground(Styles.COLOR_TEXT_AREA_BG);
		txtResult.setForeground(Styles.COLOR_TEXT_AREA_FG);
		txtResult.setEditable(false);

        scrollPane.setViewportView(txtResult);
        pnlContent.setLayout(pnlContentLayout);

		// --------------------------------------------------------------------
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addComponent(pnlStatusBar, GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
				.addComponent(pnlContent, GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
					.addComponent(pnlContent, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlStatusBar, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
		);
		getContentPane().setLayout(layout);

		// --------------------------------------------------------------------
		GroupLayout pnlStatusBarLayout = new GroupLayout(pnlStatusBar);
		pnlStatusBarLayout.setHorizontalGroup(
			pnlStatusBarLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, pnlStatusBarLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(ctrlError, GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
					.addContainerGap())
		);
		pnlStatusBarLayout.setVerticalGroup(
			pnlStatusBarLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pnlStatusBarLayout.createSequentialGroup()
					.addGap(7)
					.addComponent(ctrlError, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
					.addGap(7))
		);
		pnlStatusBar.setLayout(pnlStatusBarLayout);

		pack();

		// --------------------------------------------------------------------

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				LogService.deattachRefreshObserver(myJFrame);
				myJFrame = null;
			}
		});

	}

	// ------------------------------------------------------------------------

	@Override
	public void onLogUpdate()
	{
		txtResult.setText(LogService.getLOG());
	}

	// ------------------------------------------------------------------------

	public static void launch()
	{
		if (myJFrame != null)
		{
			myJFrame.toFront();
			myJFrame.setState(Frame.NORMAL);
			return;
		}

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					myJFrame = new FrmLogs();
					myJFrame.setVisible(true);
				}
				catch (Exception e)
				{
					LogService.error(e);
				}
			}
		});
	}

}
