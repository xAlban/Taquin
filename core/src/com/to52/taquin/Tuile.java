package com.to52.taquin;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class Tuile extends Image {

    public int ligne, colonne, longueur, largeur;

    public Tuile(TextureRegion texture, int ligne, int colonne, int longueur, int largeur){
        super(texture);
        this.ligne = ligne;
        this.colonne = colonne;
        this.longueur = longueur;
        this.largeur = largeur;
        addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toFront(); // met la tuile en premier plan lorsqu'on la déplace
                return true;

            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                moveBy(x - getWidth()/2, y - getHeight()/2);


            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                //Tuile touche = (Tuile) hit(x,y,true);
                //System.out.println(touche.colonne + " " +  touche.ligne);
            }
        });
    }

    public void updatePos(int ligne, int colonne){
        this.ligne = ligne;
        this.colonne = colonne;
        setPosition(getWidth()*(colonne+1), getHeight()*(ligne+1));
    }
}
