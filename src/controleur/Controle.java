package controleur;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Jeu;
import modele.JeuClient;
import modele.JeuServeur;
import outils.connexion.AsyncResponse;
import outils.connexion.ClientSocket;
import outils.connexion.Connection;
import outils.connexion.ServeurSocket;
import vue.Arene;
import vue.ChoixJoueur;
import vue.EntreeJeu;

/**
 * Classe qui gère les demandes de la vue et du modèle
 */
public class Controle implements AsyncResponse, Global {

	/**
	 * objet de la frame EntreeJeu
	 */
	private EntreeJeu frmEntreeJeu ;
	/**
	 * objet de la frame Arene
	 */
	private Arene frmArene ;
	/**
	 * objet de la frame ChoixJoueur
	 */
	private ChoixJoueur frmChoixJouur ;
	/**
	 * objet de type JeuClient ou JeuServeur
	 */
	private Jeu leJeu;
	
	/**
	 * Constructeur
	 */
	private Controle() {
		this.frmEntreeJeu = new EntreeJeu(this);
		this.frmEntreeJeu.setVisible(true);
	}
	
	/**
	 * Gèe les demandes provenant de EntreeJeu
	 * @param info information envoyée par EntreeJeu
	 */
	public void evenementEntreeJeu(String info) {
		if (info.equals(SERVEUR)) {
			new ServeurSocket(this, 6666);
			this.leJeu = new JeuServeur(this);
			this.frmEntreeJeu.dispose();
			this.frmArene = new Arene(this, SERVEUR);
			((JeuServeur)leJeu).constructionMurs();
			this.frmArene.setVisible(true);
		}else {
			new ClientSocket(this, info, PORT);
		}
	}
	
	/**
	 * Gère les demandes provenant de ChoixJoueur
	 * @param pseudo pseudo choisi par le joueur
	 * @param numPerso numéro de l'avatar choisi par le joueur
	 */
	public void evenementChoixJoueur(String pseudo, int numPerso) {
		this.frmChoixJouur.dispose();
		this.frmArene.setVisible(true);
		((JeuClient)this.leJeu).envoi(PSEUDO+STRINGSEPARE+pseudo+STRINGSEPARE+numPerso);
	}
	
	/**
	 * Gère les demandes provenant de Arene
	 * @param info information envoyée par Arene
	 */
	public void evenementArene(Object info) {
		if(info instanceof String) {
			((JeuClient)this.leJeu).envoi(TCHAT+STRINGSEPARE+info);
		}else if (info instanceof Integer) {
			((JeuClient)this.leJeu).envoi(ACTION+STRINGSEPARE+info);
		}
	}
	
	/**
	 * Gère les demandes de JeuServeur
	 * @param ordre demande textuelle pour savoir quel traitement exécuter
	 * @param info information à traiter
	 */
	public void evenementJeuServeur(String ordre, Object info) {
		switch (ordre) {
			case AJOUTMUR :
				this.frmArene.ajoutMurs(info);
				break;
			case AJOUTPANELMURS :
				this.leJeu.envoi((Connection)info, this.frmArene.getJpnMurs());
				break;
			case AJOUTJLABELJEU :
				this.frmArene.ajoutJLabelJeu((JLabel)info);
				break;
			case MODIFPANELJEU :
				this.leJeu.envoi((Connection)info, this.frmArene.getJpnJeu());
				break;
			case AJOUTPHRASE :
				this.frmArene.ajoutTchat((String)info);
				((JeuServeur)this.leJeu).envoi(this.frmArene.getTxtChat());
				break;
		}
	}
	
	/**
	 * Gère les demandes de JeuClient
	 * @param ordre demande textuelle pour savoir quel traitement exécuter
	 * @param info information à traiter
	 */
	public void evenementJeuClient(String ordre, Object info) {
		switch (ordre) {
			case AJOUTPANELMURS :
				this.frmArene.setJpnMurs((JPanel)info);
				break;
			case MODIFPANELJEU :
				this.frmArene.setJpnJeu((JPanel)info);
				break;
			case MODIFTCHAT :
				this.frmArene.setTxtChat((String)info);
				break;
			case JOUESON :
				this.frmArene.joueSon((Integer)info);
				break;
		}
	}
	
	/**
	 * Envoi d'une information vers l'ordinateur distant
	 * @param connection objet de connexion de l'ordinateur distant
	 * @param info information à envoyer
	 */
	public void envoi(Connection connection, Object info) {
		connection.envoi(info);
	}
	
	/**
	 * Méthode d'entrée dans l'application
	 * @param args non utilisé
	 */
	public static void main(String[] args) {
		new Controle();
	}

	/**
	 * Gère la réception de données provenant de l'ordinateur distant
	 * @param connection objet de connexion de l'ordinateur distant
	 * @param ordre connexion, reception ou deconnexon
	 * @param info information reçue
	 */
	@Override
	public void reception(Connection connection, String ordre, Object info) {
		switch(ordre) {
			case CONNEXION :
				if (!(this.leJeu instanceof JeuServeur)) {
					this.frmEntreeJeu.dispose();
					this.frmChoixJouur = new ChoixJoueur(this);
					this.frmArene = new Arene(this, CLIENT);
					this.frmChoixJouur.setVisible(true);
					this.leJeu = new JeuClient(this);
					this.leJeu.connexion(connection);
				}else {
					this.leJeu.connexion(connection);
				}
				break;
			case RECEPTION :
				this.leJeu.reception(connection, info);
				break;
			case DECONNEXION :
				this.leJeu.deconnexion(connection);
				break;
		}
		
	}

}
