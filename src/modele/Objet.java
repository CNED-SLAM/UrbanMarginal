package modele;

import javax.swing.JLabel;

/**
 * Informations communes à tous les objets (joueurs, murs, boules)
 * permet de mémoriser la position de l'objet et de gérer les  collisions
 *
 */
public abstract class Objet {

	/**
	 * position X de l'objet
	 */
	protected Integer posX ;
	/**
	 * position Y de l'objet
	 */
	protected Integer posY ;
	/**
	 * label contenant l'objet graphique (personnage, mur, boule)
	 */
	protected JLabel jLabel;
	
	/**
	 * @return the jLabel
	 */
	public JLabel getjLabel() {
		return jLabel;
	}

	/**
	 * contrôle si l'objet actuel touche l'objet passé en paramètre
	 * @param objet contient l'objet à contrôler
	 * @return true si les 2 objets se touchent
	 */
	public Boolean toucheObjet (Objet objet) {
		return null;
	}
	
}
