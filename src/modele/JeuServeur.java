package modele;

import java.util.ArrayList;
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
	 * Dictionnaire de joueurs indexé sur leur objet de connexion
	 */
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>() ;
	
	/**
	 * Constructeur
	 * @param controle instance du contrôleur pour les échanges
	 */
	public JeuServeur(Controle controle) {
		super.controle = controle;
	}
	
	@Override
	public void connexion(Connection connection) {
		this.lesJoueurs.put(connection, new Joueur(this));
	}

	@Override
	public void reception(Connection connection, Object info) {
		String[] infos = ((String)info).split(STRINGSEPARE);
		String ordre = infos[0];
		switch(ordre) {
		case PSEUDO :
			// arrivée des informations d'un nouveau joueur
			controle.evenementJeuServeur(AJOUTPANELMURS, connection);
			String pseudo = infos[1];
			int numPerso = Integer.parseInt(infos[2]);
			this.lesJoueurs.get(connection).initPerso(pseudo, numPerso, this.lesJoueurs.values(), this.lesMurs);
			break;
		}
	}
	
	@Override
	public void deconnexion() {
	}

	public void ajoutJLabelJeuArene(JLabel jLabel) {
		this.controle.evenementJeuServeur(AJOUTJLABELJEU, jLabel);
	}
	
	/**
	 * Envoi d'une information vers tous les clients
	 * fais appel plusieurs fois à l'envoi de la classe Jeu
	 */
	public void envoi() {
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
	 * Génération des murs
	 */
	public void constructionMurs() {
		for(int k=0 ; k < NBMURS ; k++) {
			this.lesMurs.add(new Mur());
			this.controle.evenementJeuServeur(AJOUTMUR, lesMurs.get(lesMurs.size()-1).getjLabel());
		}
	}
	
}
