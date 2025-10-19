package controleur;

/**
 * Constantes globales
 */
public interface Global {
	/**
	 * N° du port d'écoute du serveur
	 */
	int PORT = 6666;
	/**
	 * Nombre de personnages différents
	 */
	int MAXPERSO = 3;
	/**
	 * Caractère de séparation dans un chemin de fichiers
	 */
	String CHEMINSEPARATOR = "/";
	/**
	 * Chemin du dossier des images de fonds
	 */
	String CHEMINFONDS = "fonds"+CHEMINSEPARATOR;
	/**
	 * Chemin du dossier des images des personnages
	 */
	String CHEMINPERSONNAGES = "personnages"+CHEMINSEPARATOR;
	/**
	 * Chemin du dossier de l'image du mur
	 */
	String CHEMINMURS = "murs"+CHEMINSEPARATOR;
	/**
	 * Chemin du dossier de l'image de la boule
	 */
	String CHEMINBOULES = "boules"+CHEMINSEPARATOR;
	/**
	 * Chemin du dossier des sons
	 */
	String CHEMINSONS = "sons"+CHEMINSEPARATOR;
	/**
	 * Chemin du son welcome (entrée dans la vue ChoixJoueur)
	 */
	String SONWELCOME = CHEMINSONS+"welcome.wav";
	/**
	 * Chemin du son precedent (clic sur le bouton précédent de la vue ChoixJoueur)
	 */
	String SONPRECEDENT = CHEMINSONS+"precedent.wav";
	/**
	 * Chemin du son suivant (clic sur le bouton suivant de la vue ChoixJoueur)
	 */
	String SONSUIVANT = CHEMINSONS+"suivant.wav";
	/**
	 * Chemin du son go (clic sur le bouton GO de la vue ChoixJoueur)
	 */
	String SONGO = CHEMINSONS+"go.wav";
	/**
	 * Chemin du son flight (tir de la boule)
	 */
	String SONFIGHT = CHEMINSONS+"fight.wav";
	/**
	 * Chemin du son hurt (joueur blessé)
	 */
	String SONHURT = CHEMINSONS+"hurt.wav";
	/**
	 * Chemin du son death (joueur tué)
	 */
	String SONDEATH = CHEMINSONS+"death.wav";
	/**
	 * tableau des sons de la vue Arene
	 */
	String[] SON = {SONFIGHT, SONHURT, SONDEATH} ;
	/**
	 * numéro correspondant au son FIGHT
	 */
	int FIGHT = 0;
	/**
	 * numéro correspondant au son HURT
	 */
	int HURT = 1;
	/**
	 * numéro correspondant au son DEATH
	 */
	int DEATH = 2;
	/**
	 * Chemin de l'image de fond de la vue ChoixJoueur
	 */
	String FONDCHOIX = CHEMINFONDS+"fondchoix.jpg";
	/**
	 * Chemin de l'image de fond de la vue Arene
	 */
	String FONDARENE = CHEMINFONDS+"fondarene.jpg";
	/**
	 * Extension des fichiers des images des personnages
	 */
	String EXTFICHIERPERSO = ".gif";
	/**
	 * Chemin de l'image du mur
	 */
	String MUR = CHEMINMURS+"mur.gif";
	/**
	 * Chemin de l'image de la boule
	 */
	String BOULE = CHEMINBOULES+"boule.gif";
	/**
	 * Début du nom des images des personnages
	 */
	String PERSO = "perso";
	/**
	 * état marche du personnage
	 */
	String MARCHE = "marche";
	/**
	 * état touché du personnage
	 */
	String TOUCHE = "touche";
	/**
	 * état mort du personnage
	 */
	String MORT = "mort";
	/**
	 * Caractère de séparation dans les chaines transférées
	 */
	String STRINGSEPARE = "~";
	/**
	 * Type de jeu "serveur"
	 */
	String SERVEUR = "serveur";
	/**
	 * Type de jeu "client"
	 */
	String CLIENT = "client";
	/**
	 * Message "connexion" envoyé par la classe Connection
	 */
	String CONNEXION = "connexion";
	/**
	 * Message "réception" envoyé par la classe Connection
	 */
	String RECEPTION = "reception";
	/**
	 * Message "déconnexion" envoyé par la classe Connection
	 */
	String DECONNEXION = "deconnexion";
	/**
	 * Message "pseudo" envoyé pour la création d'un joueur
	 */
	String PSEUDO = "pseudo";
	/**
	 * vie de départ pour tous les joueurs
	 */
	int MAXVIE = 10 ;
	/**
	 * gain de points de vie lors d'une attaque
	 */
	int GAIN = 1 ; 
	/**
	 * perte de points de vie lors d'une attaque
	 */
	int PERTE = 2 ;
	/**
	 * hauteur de la zone de jeu de l'arène
	 */
	int HAUTEURARENE = 600;
	/**
	 * largeur de la zone de heu de l'arène
	 */
	int LARGEURARENE = 800;
	/**
	 * hauteur d'un mur
	 */
	int HAUTEURMUR = 35;
	/**
	 * largeur d'un mur
	 */
	int LARGEURMUR = 34;
	/**
	 * hauteur du personnage
	 */
	int HAUTEURPERSO = 44;
	/**
	 * largeur du personnage
	 */
	int LARGEURPERSO = 39;
	/**
	 * hauteur du message
	 */
	int HAUTEURMESSAGE = 8;
	/**
	 * marge à gauche et à droite du message par rapport au personnage
	 */
	int MARGEMESSAGE = 10;
	/**
	 * nombre de murs dans l'arène
	 */
	int NBMURS = 20;
	/**
	 * taille du pas quand le personnage avance (nombre de pixels)
	 */
	int PAS = 10;
	/**
	 * nombre d'étapes (d'images) pour donner l'impresson de marche
	 */
	int NBETAPESMARCHE = 4;
	/**
	 * nombre d'étapes (d'images) pour donner l'impression d'être touché
	 */
	int NBETAPESTOUCHE = 2;
	/**
	 * nombre d'étapes (d'images) pour donner l'impresson de mourir
	 */
	int NBETAPESMORT = 2;
	/**
	 * ordre pour ajouter un mur dans l'arène du serveur
	 */
	String AJOUTMUR = "ajout mur";
	/**
	 * ordre pour ajouter le panel des murs dans l'arène du client
	 */
	String AJOUTPANELMURS = "ajout panel murs";
	/**
	 * ordre pour ajouter un jLabel dans l'arène du serveur (joueur, message, boule)
	 */
	String AJOUTJLABELJEU = "ajout jLabel jeu";
	/**
	 * ordre pour modifier le panel du jeu dans l'aeène du client
	 */
	String MODIFPANELJEU = "modif panel jeu";
	/**
	 * ordre pour ajouter une phrase dans l'arène du serveur
	 */
	String AJOUTPHRASE = "ajout phrase";
	/**
	 * ordre pour modifier le contenu du tchat dans l'arène du client
	 */
	String MODIFTCHAT = "modif tchat";
	/**
	 * ordre pour jouer un son (dans l'arène du client)
	 */
	String JOUESON = "joue son";
	/**
	 * ordre pour demander d'ajouter une phrase au tchat
	 */
	String TCHAT = "tchat";
	/**
	 * orientation du personnage vers la gauche
	 */
	int GAUCHE = 0;
	/**
	 * orientation du personnage vers la droite
	 */
	int DROITE = 1;
	/**
	 * ordre pour exécuter une action (déplacement, tire de boule)
	 */
	String ACTION = "action";
	/**
	 * largeur de la boule
	 */
	int LARGEURBOULE = 17;
	/**
	 * hauteur de la boule
	 */
	int HAUTEURBOULE = 17;
	

}
