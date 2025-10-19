package modele;

import java.net.URL;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controleur.Global;

/**
 * Gestion de la boule
 *
 */
public class Boule extends Objet implements Global, Runnable {

	/**
	 * instance de JeuServeur pour la communication
	 */
	private JeuServeur jeuServeur ;
	/**
	 * Collection de murs
	 */
	private Collection lesMurs;
	/**
	 * joueur qui lance la boule
	 */
	private Joueur attaquant ;
	
	/**
	 * Constructeur
	 */
	public Boule(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		super.jLabel = new JLabel();
		super.jLabel.setVisible(false);
		URL resource = getClass().getClassLoader().getResource(BOULE);
		super.jLabel.setIcon(new ImageIcon(resource));
		super.jLabel.setBounds(0, 0, LARGEURBOULE, HAUTEURBOULE);
	}
	
	/**
	 * Tire d'une boule
	 * @param lesMurs collection de murs
	 * @param attaquant joueur qui lance la boule
	 */
	public void tireBoule(Joueur attaquant, Collection lesMurs) {
		this.lesMurs = lesMurs;
		this.attaquant = attaquant;
		// positionnement de la boule
		if(attaquant.getOrientation()==GAUCHE) {
			posX = attaquant.getPosX() - LARGEURBOULE - 1 ;
		}else{
			posX = attaquant.getPosX() + LARGEURPERSO + 1 ;
		}
		posY = attaquant.getPosY() + HAUTEURPERSO/2 ;
		// démarrer le thread pour gérer le tir de la boule
		new Thread(this).start();
	}

	/**
	 * boule tirée et en déplacement dans un thread indépendant
	 */
	@Override
	public void run() {
		// envoi du son FIGHT
		this.jeuServeur.envoi(FIGHT);
		// afficher l'attaquant à l'étape repos de la marche
		this.attaquant.affiche(MARCHE, 1);
		// rendre la boule visible
		super.jLabel.setVisible(true);
		// préparer la victime (dans le cas où un joueur est touché)
		Joueur victime = null;
		// pas positif ou négatif (suivant l'orientation du joueur) pour faire avancer la boule
		int lePas;
		if (attaquant.getOrientation() == GAUCHE) {
			lePas = - PAS;
		}else{
			lePas = PAS;
		}		// gestion de la trajectoire de la boule
		do {
			// la boule avance
			posX += lePas;
			jLabel.setBounds(posX, posY, LARGEURBOULE, HAUTEURBOULE);
			// envoi de la nouvelle zone de jeu à tous (pour que tous voient la boule avancer)
			this.jeuServeur.envoiJeuATous();
			// récupère la collection actuelle de joueurs
			Collection lesJoueurs = this.jeuServeur.getLesJoueurs();
			// récupération de l'éventuelle victime
			victime = (Joueur)super.toucheCollectionObjets(lesJoueurs);
		}while(posX>=0 && posX<=LARGEURARENE && this.toucheCollectionObjets(lesMurs)==null && victime==null);
		// vérifier s'il y a une victime et qu'elle n'est pas déjà morte
		if(victime != null && !victime.estMort()) {
			// envoi du son HURT
			this.jeuServeur.envoi(HURT);
			// gestion du gain et de la perte de vie
			victime.perteVie();
			attaquant.gainVie();
			// joue l'animation de la victime blessée
			for(int k=1 ; k<=NBETAPESTOUCHE ; k++) {
				victime.affiche(TOUCHE, k);
				pause(80, 0);
			}
			// contrôle si la victime est morte
			if(victime.estMort()) {
				// envoi du son DEATH
				this.jeuServeur.envoi(DEATH);
				// joue l'animation de la mort
				for(int k=1 ; k<=NBETAPESMORT ; k++) {
					victime.affiche(MORT, k);
					pause(80, 0);
				}
			} else {
				// remettrele joueur dans la position de repos (marche)
				victime.affiche(MARCHE, 1);
			}
		}
		// rendre à nouveau la boule invisible
		this.jLabel.setVisible(false);
		// envoyer le nouveau jeu à tous
		this.jeuServeur.envoiJeuATous();
	}
	
	/**
	 * fais une pause (bloque le processus) d'une durée précise
	 * @param millis millisecondes
	 * @param nanos nanosecondes
	 */
	private void pause(long millis, int nanos) {
		try {
			Thread.sleep(millis, nanos);
		} catch (InterruptedException e) {
			System.out.println("erreur pause");
		}
	}
	
}
