package vue;

import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import controleur.Global;

/**
 * Frame qui affiche et gère l'arène du jeu
 */
public class Arene extends JFrame implements Global {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel jpnMurs;
	private JPanel jpnJeu;
	private JTextField txtSaisie;
	
	/**
	 * Getter panel des murs
	 * @return jpnMurs
	 */
	public JPanel getJpnMurs() {
		return jpnMurs;
	}
	
	/**
	 * Setter panel des murs
	 * @param jpnMurs
	 */
	public void setJpnMurs(JPanel jpnMurs) {
		this.jpnMurs.add(jpnMurs);
		this.jpnMurs.repaint();
	}
	
	/**
	 * Getter panel du jeu
	 * @return jpnJeu
	 */
	public JPanel getJpnJeu() {
		return jpnJeu;
	}

	/**
	 * Setter panel du jeu
	 * @param jpnJeu
	 */
	public void setJpnJeu(JPanel jpnJeu) {
		this.jpnJeu.removeAll();
		this.jpnJeu.add(jpnJeu);
		this.jpnJeu.repaint();
	}
	
	/**
	 * Affichage d'un mur
	 * @param mur
	 */
	public void ajoutMurs(Object mur) {
		jpnMurs.add((JLabel)mur);
		jpnMurs.repaint();
	}

	/**
	 * Ajout d'un joueur, son message ou sa boule, dans le panel de jeu
	 * @param unJLabel le label à ajouter
	 */
	public void ajoutJLabelJeu(JLabel unJLabel) {
		this.jpnJeu.add(unJLabel);
		this.jpnJeu.repaint();
	}	
	
	/**
	 * Create the frame.
	 */
	public Arene() {
		setTitle("Arena");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setPreferredSize(new Dimension(LARGEURARENE, HAUTEURARENE + 25 + 140));
		this.pack();
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		jpnJeu = new JPanel();
		jpnJeu.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		jpnJeu.setOpaque(false);
		jpnJeu.setLayout(null);		
		contentPane.add(jpnJeu);
		
		jpnMurs = new JPanel();
		jpnMurs.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		jpnMurs.setOpaque(false);
		jpnMurs.setLayout(null);		
		contentPane.add(jpnMurs);
		
		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		String chemin = FONDARENE;
		URL resource = getClass().getClassLoader().getResource(chemin);
		lblFond.setIcon(new ImageIcon(resource));
		contentPane.add(lblFond);
		
		txtSaisie = new JTextField();
		txtSaisie.setBounds(0, 600, 800, 25);
		contentPane.add(txtSaisie);
		txtSaisie.setColumns(10);
		
		JScrollPane jspChat = new JScrollPane();
		jspChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspChat.setBounds(0, 625, LARGEURARENE, 140);
		contentPane.add(jspChat);
		
		JTextArea txtChat = new JTextArea();
		jspChat.setViewportView(txtChat);

	}
}
