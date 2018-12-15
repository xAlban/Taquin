package com.to52.taquin;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.to52.taquin.Scenes.PlayScreen;

import java.util.ArrayList;
import java.util.Collections;

public class Tuile extends Image {

    private int ligne, colonne, beforeLigne, beforeColonne, longueur, largeur, solution;
    private float beforeX, beforeY;
    private PlayScreen screen;

    public Tuile(TextureRegion texture, int ligne2, int colonne2, int longueur2, int largeur2, int solution2, PlayScreen screen2){
        super(texture);
        this.ligne = ligne2;
        this.colonne = colonne2;
        this.longueur = longueur2;
        this.largeur = largeur2;
        this.screen = screen2;
        this.solution = solution2;
        //ajout de listener permettant de déplacer la tuile
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
                swapTuile(screen.listeTuile);
                if (checkFinish()){
                    screen.finished = true;
                    screen.rejouer.setVisible(true);
                }
            }
        });
    }


    /**
     *  Mise a jour de la position dans la scene et dans la grille
     *
     * @param ligne ligne a laquel on déplace la tuile
     * @param colonne colonne a laquel on déplace la tuile
     */
    public void updatePos(int ligne, int colonne){
        this.ligne = ligne;
        this.colonne = colonne;
        addAction(Actions.moveTo((float)((getWidth()*colonne) + ((screen.getGamePort().getWorldWidth()-(screen.testTexture.getWidth()*2.2))/2)),
                (float)(getHeight()*ligne) + ((screen.getGamePort().getWorldHeight()-screen.testTexture.getHeight())/2),0.1f));
    }


    /**
     * Echange de tuile (methode appeller lorsque l'on relache une tuile
     *
     * @param liste liste de toute les tuiles dans la grille
     */
    private void swapTuile(ArrayList<Tuile> liste){
        float realX = getX() + getWidth()/2;
        float realY = getY() + getHeight()/2;
        boolean changed = false;
        beforeLigne = ligne;
        beforeColonne = colonne;

        for (Tuile t : liste){
            if (t != this && changed == false){

                Vector2 coinBas = new Vector2(t.getX(), t.getY());
                Vector2 coinHaut= new Vector2(t.getX()+t.getWidth(), t.getY()+t.getHeight());
                if (realX <= coinHaut.x && realY <= coinHaut.y && realX >= coinBas.x && realY >= coinBas.y){
                    int tmpligne = t.ligne;
                    int tmpcolonne = t.colonne;

                    t.updatePos(ligne, colonne);
                    updatePos(tmpligne, tmpcolonne);
                    Collections.swap(liste, liste.indexOf(t), liste.indexOf(Tuile.this));
                    changed = true;
                }
            }
        }
        if (changed == false){
            updatePos(beforeLigne, beforeColonne);
        }
    }


    /**
     * Verification de la résolution du Puzzle
     */
    private boolean checkFinish(){
        boolean finish = true;
        int screenLigne = screen.ligne;
        int i = screenLigne*(screenLigne-1);
        int col = 0;

        for (Tuile t : screen.listeTuile){
            if (t.solution != screen.solution.get(i).solution){
                finish = false;
            }
            if(i< screenLigne){
                col++;
                i = screenLigne*(screenLigne-1) + col;
            }else{
                i -= screenLigne;
            }
        }
        return finish;
    }
}
