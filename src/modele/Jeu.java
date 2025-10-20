package modele;

import controleur.Controle;
import outils.connexion.Connection;

/**
 * Informations et méthodes communes aux jeux client et serveur
 *
 */
public abstract class Jeu {
	
	/**
	 * objet permettant de communiquer avec le contrôleur
	 */
	protected Controle controle;

	/**
	 * Réception d'une connexion (pour communiquer avec un ordinateur distant)
	 * @param connection objet de connexion de l'ordinateur distant
	 */
	public abstract void connexion(Connection connection) ;
	
	/**
	 * Réception d'une information provenant de l'ordinateur distant
	 * @param connection objet de connexion de l'ordinateur distant
	 * @param info information reçue
	 */
	public abstract void reception(Connection connection, Object info) ;
	
	/**
	 * Déconnexion de l'ordinateur distant
	 * @param connection objet de connexion de l'ordinateur distant
	 */
	public abstract void deconnexion(Connection connection) ;
	
	/**
	 * Envoi d'une information vers un ordinateur distant
	 * @param connection objet de connexion de l'ordinteur distant
	 * @param info information à envoyer
	 */
	public void envoi(Connection connection, Object info) {
		this.controle.envoi(connection, info);
	}
	
}
