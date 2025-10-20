package modele;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import javax.swing.JLabel;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté serveur
 *
 */
public class JeuServeur extends Jeu implements Global {

	/**
	 * Collection de murs
	 */
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>() ;
	/**
	 * Dictionnaire de joueurs indexés sur l'objet de connexion pour communiquer avec eux
	 */
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>() ;
	
	/**
	 * Constructeur
	 * @param controle instance du contrôleur
	 */
	public JeuServeur(Controle controle) {
		super.controle = controle;
	}
	
	/**
	 * Getter juste sur les objets Joueur de la collectiop
	 * @return lesJoueurs.values
	 */
	public Collection getLesJoueurs() {
		return lesJoueurs.values();
	}
	
	/**
	 * Récupère la connexion du joueur et l'enregistre en créant le joueur correspondant
	 * @param connection objet de connexion de l'ordinateur distant
	 */
	@Override
	public void connexion(Connection connection) {
		this.lesJoueurs.put(connection, new Joueur(this));
	}

	/**
	 * Gère la réception d'information provenant de l'ordinateur distant (un joueur)
	 * @param connection objet de connexion de l'ordinateur distant
	 * @param info information reçue
	 */
	@Override
	public void reception(Connection connection, Object info) {
		String[] message = ((String)info).split(STRINGSEPARE);
		switch (message[0]) {
		case PSEUDO :
			// arrivée des informations d'un nouveau joueur
			this.controle.evenementJeuServeur(AJOUTPANELMURS, connection);
			String pseudo = message[1];
			Integer numPerso = Integer.parseInt(message[2]);
			this.lesJoueurs.get(connection).initPerso(pseudo, numPerso, this.lesJoueurs.values(), this.lesMurs);
			String premierMessage = "*** "+pseudo+" vient de se connecter ***";
			this.controle.evenementJeuServeur(AJOUTPHRASE, premierMessage);
			break;
		case TCHAT :
			String phrase = message[1];
			phrase = this.lesJoueurs.get(connection).getPseudo()+" > "+phrase;
			this.controle.evenementJeuServeur(AJOUTPHRASE, phrase);
			break;
		case ACTION :
			Integer action = Integer.parseInt(message[1]);
			this.lesJoueurs.get(connection).action(action, this.lesJoueurs.values(), this.lesMurs);
			break;
		}
	}
	
	@Override
	public void deconnexion(Connection connection) {
		this.lesJoueurs.get(connection).departJoueur();
		this.lesJoueurs.remove(connection);
	}

	/**
	 * Envoi d'une information vers tous les clients
	 * fais appel plusieurs fois à l'envoi de la classe Jeu
	 * @param info information à envoyer
	 */
	public void envoi(Object info) {
		for(Connection connection : this.lesJoueurs.keySet()) {
			super.envoi(connection, info);
		}		
	}

	/**
	 * Envoi du panel de jeu à tous les joueurs
	 */
	public void envoiJeuATous() {
		for(Connection connection : this.lesJoueurs.keySet()) {
			this.controle.evenementJeuServeur(MODIFPANELJEU, connection);
		}
	}
	
	/**
	 * Demande d'ajout d'un jlabel de jeu
	 * @param jLabel label du jeu
	 */
	public void ajoutJLabelJeuArene(JLabel jLabel) {
		this.controle.evenementJeuServeur(AJOUTJLABELJEU, jLabel);
	}
	
	/**
	 * Génération des murs
	 */
	public void constructionMurs() {
		for(int k=0 ; k < NBMURS ; k++) {
			this.lesMurs.add(new Mur());
			this.controle.evenementJeuServeur(AJOUTMUR, lesMurs.get(lesMurs.size()-1).getJLabel());
		}
	}
	
}
