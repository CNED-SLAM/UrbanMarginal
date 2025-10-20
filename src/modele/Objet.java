package modele;

import java.util.Collection;

import javax.swing.JLabel;

/**
 * Informations communes � tous les objets (joueurs, murs, boules)
 * permet de m�moriser la position de l'objet et de g�rer les  collisions
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
	 * objet graphique
	 */
	protected JLabel jLabel;
	
	/**
	 * getter 
	 * @return jLbal
	 */
	public JLabel getJLabel() {
		return jLabel;
	}
	
	/**
	 * getter de posX
	 * @return posX
	 */
	public Integer getPosX() {
		return posX;
	}

	/**
	 * getter de posY
	 * @return posY
	 */
	public Integer getPosY() {
		return posY;
	}
	
	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	/**
	 * controle si l'objet actuel touche l'objet pass� en param�tre
	 * @param objet contient l'objet à controler
	 * @return true si les 2 objets se touchent
	 */
	public Boolean toucheObjet (Objet objet) {
		if (objet.jLabel==null || objet.jLabel==null) {
			return false ;
		}else{
			return(this.posX+this.jLabel.getWidth()>objet.posX &&
				this.posX<objet.posX+objet.jLabel.getWidth() && 
				this.posY+this.jLabel.getHeight()>objet.posY &&
				this.posY<objet.posY+objet.jLabel.getHeight()) ;
		}
	}
	
	/**
	 * Vérifie si l'objet actuel touche un des objets de la collection
	 * @param lesObjets collection d'objets (murs, joueurs ou boules)
	 * @return l'objet touché ou null
	 */
	public Objet toucheCollectionObjets (Collection<Objet> lesObjets) {
		for (Objet unObjet : lesObjets) {
			if (!unObjet.equals(this)) {
				if (toucheObjet(unObjet)) {
					return unObjet ;
				}
			}
		}
		return null ;
	}	
	
}
