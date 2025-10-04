package vue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EntreeJeu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIp;
	private Arene frmArene;
	private ChoixJoueur frmChoixJoueur;
	
	/**
	 * Clic sur le bouton Exit
	 * Sortie de l'application
	 */
	private void btnExit_clic() {
		System.exit(0);
	}
	
	/**
	 * Clic sur le bouton Connect
	 * Ouverture de ChoixJoueur
	 */
	private void btnConnect_clic() {
		this.frmChoixJoueur = new ChoixJoueur();
		this.frmChoixJoueur.setVisible(true);
		this.dispose();
	}
	
	/**
	 * Clic sur le bouton Start
	 * Ouverture de Arene
	 */
	private void btnStart_clic() {
		this.frmArene = new Arene();
		this.frmArene.setVisible(true);
		this.dispose();
	}

	/**
	 * Create the frame.
	 */
	public EntreeJeu() {
		setTitle("Urban Marginal");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 302, 159);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblStartAServer = new JLabel("Start a server :");
		lblStartAServer.setBounds(10, 11, 94, 14);
		contentPane.add(lblStartAServer);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStart_clic();
			}
		});
		btnStart.setBounds(186, 7, 89, 23);
		contentPane.add(btnStart);
		
		JLabel lblConnectAnExisting = new JLabel("Connect an existing server :");
		lblConnectAnExisting.setBounds(10, 36, 197, 14);
		contentPane.add(lblConnectAnExisting);
		
		JLabel lblIpServer = new JLabel("IP server :");
		lblIpServer.setBounds(10, 61, 68, 14);
		contentPane.add(lblIpServer);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 btnConnect_clic();
			}
		});
		btnConnect.setBounds(186, 57, 89, 23);
		contentPane.add(btnConnect);
		
		txtIp = new JTextField();
		txtIp.setText("127.0.0.1");
		txtIp.setBounds(69, 58, 107, 20);
		contentPane.add(txtIp);
		txtIp.setColumns(10);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExit_clic();
			}
		});
		btnExit.setBounds(186, 91, 89, 23);
		contentPane.add(btnExit);

	}

}
