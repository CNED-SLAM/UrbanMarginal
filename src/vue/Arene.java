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

import controleur.Controle;
import controleur.Global;
import outils.son.Son;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Frame qui affiche et gère l'arène du jeu
 */
public class Arene extends JFrame implements Global {

	/**
	 * numéro interne
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * panel général
	 */
	private JPanel contentPane;
	/**
	 * panel transparent qui contient les murs
	 */
	private JPanel jpnMurs;
	/**
	 * panel transparent qui contient les objets du jeu (joueurs, boules...)
	 */
	private JPanel jpnJeu;
	/**
	 * zone de saisie de la phrase du tchat
	 */
	private JTextField txtSaisie;
	/**
	 * zone d'affichage de l'historique du tchat
	 */
	private JTextArea txtChat;
	/**
	 * objet permettant de communiquer avec le contrôleur
	 */
	private Controle controle;
	/**
	 * booléen pour savoir si c'est l'arène du client ou du serveur
	 */
	private boolean client;
	/**
	 * Talbeau des sons de l'arène
	 */
	private Son[] lesSons = new Son[SON.length];
	
	/**
	 * Getter panel des murs
	 * @return jpnMurs
	 */
	public JPanel getJpnMurs() {
		return jpnMurs;
	}
	
	/**
	 * Setter panel des murs
	 * @param jpnMurs panel contenant les murs
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
	 * @param jpnJeu panel contenant les objets du jeu
	 */
	public void setJpnJeu(JPanel jpnJeu) {
		this.jpnJeu.removeAll();
		this.jpnJeu.add(jpnJeu);
		this.jpnJeu.repaint();
		this.contentPane.requestFocus();
	}
	
	/**
	 * Getter sur l'affichage du tchat
	 * @return txtChat
	 */
	public String getTxtChat() {
		return txtChat.getText();
	}

	/**
	 * Setter sur l'affichage du tchat
	 * @param txtChat zone d'affichage du tchat
	 */
	public void setTxtChat(String txtChat) {
		this.txtChat.setText(txtChat);
		this.txtChat.setCaretPosition(this.txtChat.getDocument().getLength());
	}
	
	/**
	 * Ajout d'une phrase à insérer à la fin du tchat
	 * @param phrase phase à insérer
	 */
	public void ajoutTchat(String phrase) {
		this.txtChat.setText(this.txtChat.getText()+phrase+"\r\n");
		this.txtChat.setCaretPosition(this.txtChat.getDocument().getLength());
	}
	
	/**
	 * Affichage d'un mur
	 * @param mur mur à insérer
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
	 * Evénément touche pressée dans la zone de saisie
	 * @param e informations sur la touche
	 */
	private void txtSaisie_KeyPressed(KeyEvent e) {
		// si validation
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			// si la zone de saisie n'est pas vide
			if(!this.txtSaisie.getText().equals("")) {
				this.controle.evenementArene(this.txtSaisie.getText());
				this.txtSaisie.setText("");
			}
			this.contentPane.requestFocus();
		}
	}
	
	/**
	 * Evénement touche pressée dans la frame
	 * @param e informations sur la touche
	 */
	private void contentPane_KeyPressed(KeyEvent e) {
		int touche = -1;
		switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT :
			case KeyEvent.VK_RIGHT :
			case KeyEvent.VK_UP :
			case KeyEvent.VK_DOWN :
			case KeyEvent.VK_SPACE :
				touche = e.getKeyCode();
				break;
		}
		// si touche correcte, alors envoi de sa valeur
		if(touche != -1) {
			this.controle.evenementArene(touche);
		}
	}
	
	/**
	 * Joue le son correspondant au numéro reçu
	 * @param numSon numéro du son (0 : fight, 1 : hurt; 2 : death)
	 */
	public void joueSon(Integer numSon) {
		this.lesSons[numSon].play();
	}
	
	/**
	 * Create the frame. 
	 * @param controle controleur
	 * @param typeJeu CLIENT ou SERVEUR
	 */
	public Arene(Controle controle, String typeJeu) {
		this.client = typeJeu.equals(CLIENT);
		setTitle("Arena");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setPreferredSize(new Dimension(LARGEURARENE, HAUTEURARENE + 25 + 140));
		this.pack();
		contentPane = new JPanel();
		if (this.client) {
			contentPane.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					contentPane_KeyPressed(e);
				}
			});
		}
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
		
		if(this.client) {
			txtSaisie = new JTextField();
			txtSaisie.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					txtSaisie_KeyPressed(e);
				}
			});
			txtSaisie.setBounds(0, 600, 800, 25);
			contentPane.add(txtSaisie);
			txtSaisie.setColumns(10);
		}
		
		JScrollPane jspChat = new JScrollPane();
		if (this.client) {
			jspChat.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					contentPane_KeyPressed(e);
				}
			});
		}
		jspChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspChat.setBounds(0, 625, LARGEURARENE, 140);
		contentPane.add(jspChat);
		
		txtChat = new JTextArea();
		txtChat.setEditable(false);
		jspChat.setViewportView(txtChat);
		
		// gestion des sons pour le client
		if (client) {
			for (int k=0 ; k<SON.length ; k++) {
				lesSons[k] = new Son(getClass().getClassLoader().getResource(SON[k])) ;
			}
		}
		
		this.controle = controle;
	}
}
