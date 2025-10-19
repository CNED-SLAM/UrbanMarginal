package vue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur.Controle;
import controleur.Global;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Frame d'entrée du jeu, pour démarrer un serveur ou un client
 */
public class EntreeJeu extends JFrame implements Global{

	/**
	 * numéro interne
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * panel principal
	 */
	private JPanel contentPane;
	/**
	 * zone de saisie de l'IP
	 */
	private JTextField txtIp;
	/**
	 * frame Arne
	 */
	private Arene frmArene;
	/**
	 * frame ChoixJoueur
	 */
	private ChoixJoueur frmChoixJoueur;
	/**
	 * objet permettant de communiquer avec le contrôleur
	 */
	private Controle controle;
	
	/**
	 * Clic sur le bouton Exit
	 * Sortie de l'application
	 */
	private void btnExit_clic() {
		System.exit(0);
	}
	
	/**
	 * Clic sur le bouton Connect
	 * demande au contrôleur de se connecter à un serveur
	 */
	private void btnConnect_clic() {
		this.controle.evenementEntreeJeu(txtIp.getText().toString());
	}
	
	/**
	 * Clic sur le bouton Start
	 * demande au contrôleur de démarrer un serveur
	 */
	private void btnStart_clic() {
		this.controle.evenementEntreeJeu(SERVEUR);
	}

	/**
	 * Create the frame.
	 * @param controle instance du contrôleur
	 */
	public EntreeJeu(Controle controle) {
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
		
		this.controle = controle;

	}

}
