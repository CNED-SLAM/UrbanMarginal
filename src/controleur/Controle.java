package controleur;

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
 * Classe qui gère les demandes de la vue
 */
public class Controle implements AsyncResponse, Global {

	private EntreeJeu frmEntreeJeu ;
	private Arene frmArene ;
	private ChoixJoueur frmChoixJouur ;
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
	 * @param info
	 */
	public void evenementEntreeJeu(String info) {
		if (info.equals(SERVEUR)) {
			new ServeurSocket(this, 6666);
			this.leJeu = new JeuServeur(this);
			this.frmEntreeJeu.dispose();
			this.frmArene = new Arene();
			this.frmArene.setVisible(true);
		}else {
			new ClientSocket(this, info, PORT);
		}
	}
	
	/**
	 * Gère les demandes provenant de ChoixJoueur
	 * @param pseudo
	 * @param numPerso
	 */
	public void evenementChoixJoueur(String pseudo, int numPerso) {
		this.frmChoixJouur.dispose();
		this.frmArene.setVisible(true);
		((JeuClient)this.leJeu).envoi(PSEUDO+STRINGSEPARE+pseudo+STRINGSEPARE+numPerso);
	}
	
	/**
	 * Envoi d'une information vers l'ordinateur distant
	 * @param connection
	 * @param info
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
					this.frmArene = new Arene();
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
		}
		
	}

}
