package vue;

import java.awt.Cursor;
import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controleur.Controle;
import controleur.Global;
import outils.son.Son;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

/**
 * Frame qui permet au client de choisir un avatar et un pseudo
 */
public class ChoixJoueur extends JFrame implements Global {

	/**
	 * numéro interne
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * panel général
	 */
	private JPanel contentPane;
	/**
	 * zone de saisie du pseudo
	 */
	private JTextField txtPseudo;
	/**
	 * label du personnage (image de l'avatar)
	 */
	private JLabel lblPersonnage;
	/**
	 * frame de Arene
	 */
	private Arene frmArene;
	/**
	 * objet permettant de communiquer avec le contrôleur
	 */
	private Controle controle;
	/**
	 * numéro du personnage (avatar) initialisé à 1
	 */
	private int numPerso = 1;
	/**
	 * son d'entrée dans la frame
	 */
	private Son welcome;
	/**
	 * son du clic sur la flèche de gauche
	 */
	private Son precedent;
	/**
	 * son du clic sur la flèche de droite
	 */
	private Son suivant;
	/**
	 * son du clic sur go
	 */
	private Son go;
	
	/**
	 * Clic sur la flèche gauche
	 */
	private void lblPrecedent_clic() {
		precedent.play();
		numPerso = (numPerso + 1) % MAXPERSO + 1;
		affichePerso();
	}
	
	/**
	 * Clic sur la flèche droite
	 */
	private void lblSuivant_clic() {
		suivant.play();
		numPerso = numPerso % MAXPERSO + 1;
		affichePerso();
	}
	
	/**
	 * Clic sur GO
	 */
	private void lblGo_clic() {
		go.play();
		if(txtPseudo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "La saisie du pseudo est obligatoire");
			txtPseudo.requestFocus();
		}else {
			controle.evenementChoixJoueur(txtPseudo.getText().toString(), numPerso);
		}
	}
	
	/**
	 * Affiche le personnage
	 */
	private void affichePerso() {
		String chemin = CHEMINPERSONNAGES+PERSO+numPerso+MARCHE+"1d1"+EXTFICHIERPERSO;
		URL resource = getClass().getClassLoader().getResource(chemin);
		lblPersonnage.setIcon(new ImageIcon(resource));
	}
	
	/**
	 * Rend le curseur de la souris normal (curseur par défaut)
	 */
	private void sourisNormale() {
		contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	/**
	 * Rend le curseur de la souris en forme de doigt pointé
	 */
	private void sourisDoigt() {
		contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * Create the frame.
	 * @param controle instance du contrôleur
	 */
	public ChoixJoueur(Controle controle) {
		setTitle("Choice");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setPreferredSize(new Dimension(400, 275));
		this.pack();
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblPersonnage = new JLabel("");
		lblPersonnage.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonnage.setBounds(142, 114, 123, 119);
		contentPane.add(lblPersonnage);
		
		txtPseudo = new JTextField();
		txtPseudo.setBounds(142, 244, 123, 20);
		contentPane.add(txtPseudo);
		txtPseudo.setColumns(10);
		
		JLabel lblPrecedent = new JLabel("");
		lblPrecedent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblPrecedent_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
		lblPrecedent.setBounds(69, 146, 25, 40);
		contentPane.add(lblPrecedent);
		
		JLabel lblSuivant = new JLabel("");
		lblSuivant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblSuivant_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
		lblSuivant.setBounds(300, 144, 30, 42);
		contentPane.add(lblSuivant);
		
		JLabel lblGo = new JLabel("");
		lblGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblGo_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
		lblGo.setBounds(310, 197, 66, 67);
		contentPane.add(lblGo);

		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, 400, 275);
		String chemin = FONDCHOIX;
		URL resource = getClass().getClassLoader().getResource(chemin);
		lblFond.setIcon(new ImageIcon(resource));
		contentPane.add(lblFond);
		
		this.controle = controle;
		this.affichePerso();
		
		// récupération des sons
		precedent = new Son(getClass().getClassLoader().getResource(SONPRECEDENT));
		suivant = new Son(getClass().getClassLoader().getResource(SONSUIVANT));
		go = new Son(getClass().getClassLoader().getResource(SONGO));
		welcome = new Son(getClass().getClassLoader().getResource(SONWELCOME));
		welcome.play();
		
	}
}
