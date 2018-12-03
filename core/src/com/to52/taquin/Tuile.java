package com.to52.taquin;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.to52.taquin.Scenes.PlayScreen;

import java.util.ArrayList;
import java.util.Collections;

public class Tuile extends Image {

    public int ligne, colonne, longueur, largeur, solution;
    public float beforeX, beforeY;
    public PlayScreen screen;

    public Tuile(TextureRegion texture, int ligne2, int colonne2, int longueur2, int largeur2, int solution2, PlayScreen screen2){
        super(texture);
        this.ligne = ligne2;
        this.colonne = colonne2;
        this.longueur = longueur2;
        this.largeur = largeur2;
        this.screen = screen2;
        this.solution = solution2;
        addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toFront(); // met la tuile en premier plan lorsqu'on la déplace
                beforeX = getX();
                beforeY= getY();
                return true;

            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {

                moveBy(x - getWidth()/2, y - getHeight()/2);


            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                //Tuile touche = (Tuile) hit(x,y,true);
                //System.out.println(x + " " +  y + "stage "+ getX() + " " + getY());
                swapTuile(screen.listeTuile);
            }
        });
    }

    public void updatePos(int ligne, int colonne){
        this.ligne = ligne;
        this.colonne = colonne;
        setPosition((float)((getWidth()*colonne) + ((screen.getGamePort().getWorldWidth()-(screen.testTexture.getWidth()*2.2))/2)),
                (getHeight()*ligne) + ((screen.getGamePort().getWorldHeight()-screen.testTexture.getHeight())/2));
    }

    //methode pour echanger les tuiles de place ou remettre la tuile a sa place
    private void swapTuile(ArrayList<Tuile> liste){
        float realX = getX() + getWidth()/2;
        float realY = getY() + getHeight()/2;
        boolean changed = false;
        for (Tuile t : liste){
            if (t.ligne == ligne && t.colonne == colonne){

            }else{
                Vector2 coinBas = new Vector2(t.getX(), t.getY());
                Vector2 coinHaut= new Vector2(t.getX()+t.getWidth(), t.getY()+t.getHeight());
                if (realX <= coinHaut.x && realY <= coinHaut.y && realX >= coinBas.x && realY >= coinBas.y){
                    int tmpligne = t.ligne;
                    int tmpcolonne = t.colonne;
                    t.updatePos(ligne, colonne);
                    updatePos(tmpligne, tmpcolonne);
                    Collections.swap(liste, liste.indexOf(t), liste.indexOf(Tuile.this));
                    //for (Tuile tu : screen.listeTuile){
                    //    System.out.println("ordre : "+tu.solution);
                    //    System.out.println("solution : " + screen.solution.indexOf(tu));
                    //}
                    //System.out.println("------------------------------------------------");
                    changed = true;
                }
            }
        }
        if (changed == false){
            setPosition(beforeX, beforeY);
        }
    }
}
