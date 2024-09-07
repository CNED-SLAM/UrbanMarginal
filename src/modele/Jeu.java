package modele;

import controleur.Controle;
import outils.connexion.Connection;

/**
 * Informations et méthodes communes aux jeux client et serveur
 *
 */
public abstract class Jeu {
	
	/**
	 * Instance de Controle, pour communiquer avec le contrôleur
	 */
	protected Controle controle;

	/**
	 * Réception d'une connexion (pour communiquer avec un ordinateur distant)
	 * @param connection TODO
	 */
	public abstract void connexion(Connection connection) ;
	
	/**
	 * Réception d'une information provenant de l'ordinateur distant
	 * @param connection objet de connexion d'où provient l'information
	 * @param info information reçue
	 */
	public abstract void reception(Connection connection, Object info) ;
	
	/**
	 * Déconnexion de l'ordinateur distant
	 */
	public abstract void deconnexion() ;
	
	/**
	 * Envoi d'une information vers un ordinateur distant
	 * @param connection objet de connexion pour accéder à l'ordinateur distant
	 * @param info information à envoyer
	 */
	public void envoi(Connection connection, Object info) {
		this.controle.envoi(connection, info);
	}
	
}
