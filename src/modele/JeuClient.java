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
	
	/**
	 * Objet de connexion au serveur
	 */
	private Connection connectionServeur;
	/**
	 * Booléen pour savoir si les murs sont déjà arrivés
	 */
	private boolean mursOk = false;
	
	/**
	 * Constructeur
	 * @param controle instance du contrôleur
	 */
	public JeuClient(Controle controle) {
		super.controle = controle;
	}
	
	/**
	 * Récupère l'objet de connexion du serveur pour pouvoir communiquer avec lui
	 * @param connection objet de connexion de l'ordinateur distant
	 */
	@Override
	public void connexion(Connection connection) {
		this.connectionServeur = connection;
	}

	/**
	 * Récupère l"ibformation envoyée par le serveur
	 * @param connection objet de connexion de l'ordinateur distant
	 * @param info information reçue
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
		} else if(info instanceof String) {
			this.controle.evenementJeuClient(MODIFTCHAT, info);
		} else if(info instanceof Integer) {
			this.controle.evenementJeuClient(JOUESON, info);
		}
	}
	
	@Override
	public void deconnexion(Connection connection) {
		System.exit(0);
	}

	/**
	 * Envoi d'une information vers le serveur
	 * fais appel une fois à l'envoi dans la classe Jeu
	 * @param info information à envoyer
	 */
	public void envoi(String info) {
		super.envoi(this.connectionServeur, info);
	}

}
