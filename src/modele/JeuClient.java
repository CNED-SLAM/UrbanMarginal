package modele;

import javax.swing.JPanel;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté client
 *
 */
public class JeuClient extends Jeu implements Global{
	
	private Connection connectionServeur;
	private boolean mursOk = false;
	
	/**
	 * Constructeur
	 * @param controle
	 */
	public JeuClient(Controle controle) {
		super.controle = controle;
	}
	
	/**
	 * Récupère l'objet de connexion du serveur pour pouvoir communiquer avec lui
	 * @param connection
	 */
	@Override
	public void connexion(Connection connection) {
		this.connectionServeur = connection;
	}

	/**
	 * Récupère l"ibformation envoyée par le serveur
	 * @param connection
	 * @param info
	 */
	@Override
	public void reception(Connection connection, Object info) {
		if (info instanceof JPanel) {
			if(!this.mursOk) {
				// arrivée du panel des murs
				this.controle.evenementJeuClient(AJOUTPANELMURS, info);
				this.mursOk = true;
			} else {
				// arrivée du panel de jeu
				this.controle.evenementJeuClient(MODIFPANELJEU, info);
			}
		}
	}
	
	@Override
	public void deconnexion() {
	}

	/**
	 * Envoi d'une information vers le serveur
	 * fais appel une fois à l'envoi dans la classe Jeu
	 * @param info
	 */
	public void envoi(String info) {
		super.envoi(this.connectionServeur, info);
	}

}
