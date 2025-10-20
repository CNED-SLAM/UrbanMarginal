package modele;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;

/**
 * Gestion des joueurs
 *
 */
public class Joueur extends Objet implements Global {

	/**
	 * pseudo saisi
	 */
	private String pseudo ;
	/**
	 * n° correspondant au personnage (avatar) pour le fichier correspondant
	 */
	private int numPerso ; 
	/**
	 * instance de JeuServeur pour communiquer avec lui
	 */
	private JeuServeur jeuServeur ;
	/**
	 * numéro d'�tape dans l'animation (de la marche, touché ou mort)
	 */
	private int etape ;
	/**
	 * la boule du joueur
	 */
	private Boule boule ;
	/**
	* vie restante du joueur
	*/
	private int vie ; 
	/**
	* tourné vers la gauche (0) ou vers la droite (1)
	*/
	private int orientation ;
	/**
	 * message contenant pseudo et vie sous le peronnage
	 */
	private JLabel message ;
	
	/**
	 * Constructeur 
	 * @param jeuServeur instance de JeuServeur pour communiquer
	 */
	public Joueur(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		this.vie = MAXVIE;
		this.etape = 1;
		this.orientation = DROITE;
	}
	
	/**
	 * Getter du pseudo
	 * @return pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}
	
	/**
	 * Getter de orientation (GAUCHE ou DROITE)
	 * @return orientation
	 */
	public int getOrientation() {
		return orientation;
	}

	/**
	 * Initialisation d'un joueur (pseudo et numéro, calcul de la 1ère position, affichage, création de la boule)
	 * @param pseudo pseudo du jouur
	 * @param numPerso numéro de l'avatar du joueur
	 * @param lesJoueurs collection de joueurs
	 * @param lesMurs collection de murs
	 */
	public void initPerso(String pseudo, Integer numPerso, Collection lesJoueurs, Collection lesMurs) {
		this.pseudo = pseudo;
		this.numPerso = numPerso;
		System.out.println("joueur "+pseudo+" - num perso "+numPerso+" créé");
		// création du label du personnage
		super.jLabel = new JLabel();
		// création du label du message sous le personnage
		this.message = new JLabel();
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font("Dialog", Font.PLAIN, 8));
		// calcul de la première position du personnage
		this.premierePosition(lesJoueurs, lesMurs);
		// création de la boule
		this.boule = new Boule(this.jeuServeur);
		// demande d'ajout du label du personnage et du message dans l'arène du serveur
		this.jeuServeur.ajoutJLabelJeuArene(jLabel);
		this.jeuServeur.ajoutJLabelJeuArene(message);	
		this.jeuServeur.ajoutJLabelJeuArene(boule.getJLabel());
		// demande d'affichage du personnage
		this.affiche(MARCHE, this.etape);
	}

	/**
	 * Calcul de la première position aléatoire du joueur (sans chevaucher un autre joueur ou un mur)
	 * @param lesJoueurs collection de joueurs
	 * @param lesMurs tableau des murs
	 */
	private void premierePosition(Collection lesJoueurs, Collection lesMurs) {
		jLabel.setBounds(0, 0, LARGEURPERSO, HAUTEURPERSO);
		do {
			posX = (int) Math.round(Math.random() * (LARGEURARENE - LARGEURPERSO)) ;
			posY = (int) Math.round(Math.random() * (HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE)) ;
		}while(toucheCollectionObjets(lesJoueurs)!=null || toucheCollectionObjets(lesMurs)!=null);
	}
	
	/**
	 * Affiche le personnage et son message
	 * @param etat état du personnage (MARCHE, TOUCH, MORT)
	 * @param etape étape d'animation dans l'état
	 */
	public void affiche(String etat, int etape) {
		// positionnement du personnage et affectation de la bonne image
		super.jLabel.setBounds(posX, posY, LARGEURPERSO, HAUTEURPERSO);
		String chemin = CHEMINPERSONNAGES+PERSO+this.numPerso+etat+etape+"d"+this.orientation+EXTFICHIERPERSO;
		URL resource = getClass().getClassLoader().getResource(chemin);
		super.jLabel.setIcon(new ImageIcon(resource));
		// positionnement et remplissage du message sous le perosnnage
		this.message.setBounds(posX-MARGEMESSAGE, posY+HAUTEURPERSO, LARGEURPERSO+MARGEMESSAGE, HAUTEURMESSAGE);
		this.message.setText(pseudo+" : "+vie);
		// demande d'envoi à tous des modifications d'affichage
		this.jeuServeur.envoiJeuATous();
	}

	/**
	 * Gère une action reçue et qu'il faut afficher (déplacement, tire de boule...)
	 * @param action numéro d'action correspondant à une touche.
	 * @param lesJoueurs la collection de joueurs
	 * @param lesMurs le tableau des murs
	 */
	public void action(Integer action, Collection lesJoueurs, Collection lesMurs) {
		if (!this.estMort()) {
			switch(action){
				case KeyEvent.VK_LEFT :
					orientation = GAUCHE; 
					posX = deplace(posX, action, -PAS, LARGEURARENE - LARGEURPERSO, lesJoueurs, lesMurs);
					break;
				case KeyEvent.VK_RIGHT :
					orientation = DROITE; 
					posX = deplace(posX, action, PAS, LARGEURARENE - LARGEURPERSO, lesJoueurs, lesMurs);
					break;
				case KeyEvent.VK_UP :
					posY = deplace(posY, action, -PAS, HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE, lesJoueurs, lesMurs) ;
					break;
				case KeyEvent.VK_DOWN :
					posY = deplace(posY,  action, PAS, HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE, lesJoueurs, lesMurs) ;
					break;		
				case KeyEvent.VK_SPACE :
					if(!this.boule.getJLabel().isVisible()) {
						this.boule.tireBoule(this, lesMurs);
					}
					break;
			}
			this.affiche(MARCHE, this.etape);
		}
	}

	/**
	 * Gère le déplacement du personnage 
	 * @param position position de départ
	 * @param action gauche, droite, haut ou bas
	 * @param lepas valeur de déplacement (positif ou négatif)
	 * @param max valeur à ne pas dépasser
	 * @param lesJoueurs collection de joueurs pour éviter les collisions
	 * @param lesMurs collection de murs pour éviter les collisions
	 * @return nouvelle position
	 */
	private int deplace(int position, // position de départ
			int action, // gauche, droite, haut, bas
			int lepas, // valeur du déplacement (positif ou négatif)
			int max, // valeur à ne pas dépasser
			Collection lesJoueurs, // les joueurs (pour éviter les collisions)
			Collection lesMurs) { // les murs (pour éviter les collisions)
		int ancpos = position ;
		position += lepas ;
		position = Math.max(position, 0) ;
		position = Math.min(position,  max) ;
		if (action==KeyEvent.VK_LEFT || action==KeyEvent.VK_RIGHT) {
			posX = position ;
		}else{
			posY = position ;
		}
		// controle s'il y a collision, dans ce cas, le personnage reste sur place
		if (toucheCollectionObjets(lesJoueurs)!=null || toucheCollectionObjets(lesMurs)!=null) {
			position = ancpos ;
		}
		// passe à l'étape suivante de l'animation de la marche
		etape = (etape % NBETAPESMARCHE) + 1 ;
		return position ;
	}

	/**
	 * Gain de points de vie après avoir touché un joueur
	 */
	public void gainVie() {
		this.vie += GAIN;
		affiche(MARCHE, etape);
	}
	
	/**
	 * Perte de points de vie après avoir été touché 
	 */
	public void perteVie() {
		this.vie = Math.max(0, this.vie - PERTE);
		affiche(MARCHE, etape);
	}
	
	/**
	 * vrai si la vie est à 0
	 * @return true si vie = 0
	 */
	public Boolean estMort() {
		return vie == 0;
	}
	
	/**
	 * Le joueur se déconnecte et disparait
	 */
	public void departJoueur() {
		if (super.jLabel != null) {
			super.jLabel.setVisible(false);
			this.message.setVisible(false);
			this.boule.getJLabel().setVisible(false);
			this.jeuServeur.envoiJeuATous();
		}
	}
	
}
