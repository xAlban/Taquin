package com.to52.taquin.Scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sun.prism.image.ViewPort;
import com.to52.taquin.TaquinGame;
import com.to52.taquin.Tuile;

import java.util.ArrayList;


public class PlayScreen implements Screen {
    private TaquinGame game;
    private OrthographicCamera camera;
    private ScreenViewport gamePort;
    private Texture testTexture;
    private TextureRegion[][] tuilePuzzle;
    public ArrayList<Tuile> listeTuile = new ArrayList<Tuile>();
    private Stage stage;
    private int colonne = 3;
    private int ligne = 3;

    public PlayScreen(TaquinGame game){
        this.game = game;
        camera = new OrthographicCamera();
        gamePort = new ScreenViewport(camera);
        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
        testTexture = new Texture(Gdx.files.internal("images\\shrek.jpeg"));
        stage = new Stage();
        generatePuzzle(ligne,colonne);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.begin();

        //game.batch.draw(testTexture, 0,0);
        int i = 0; //utilisé pour placé les tuiles en longueurs
        int j = 0; //utilisé pour placé les tuiles en largeurs
        int k = 0; //utilisé pour savoir quand changer de colonne
        for (Tuile t : listeTuile){
            if (k == colonne){
                j += t.largeur;
                k=0;
                i=0;
            }
            game.batch.draw(tuilePuzzle[t.ligne][t.colonne], i, j); // dessine la tuile au bon endroit

            i += t.longueur;
            k++;
        }
        //stage.act();
        //stage.draw();
        game.batch.end();
}

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    //methode permettant de generer les tuiles en creant les objets necessaire et en les stockant dans une liste
    private void generatePuzzle(int ligne, int colonne){
         int tuileLongueur = testTexture.getWidth()/colonne;
         int tuileLargeur = testTexture.getHeight()/ligne;
         tuilePuzzle = new TextureRegion(testTexture).split(
                tuileLongueur,
                tuileLargeur);
         int tmpligne = 0;
         int tmpcolonne =0;
         for (TextureRegion[] tr : tuilePuzzle) {
             for (TextureRegion t : tr) {
                 if (tmpcolonne%(colonne-1) == 0 && tmpcolonne != 0){
                     Tuile tmptuile = new Tuile(t.getTexture(), tmpligne, tmpcolonne, tuileLongueur, tuileLargeur);
                     listeTuile.add(tmptuile);
                     stage.addActor(tmptuile);
                     tmpcolonne = 0;
                     tmpligne++;
                 }else{
                     Tuile tmptuile = new Tuile(t.getTexture(), tmpligne, tmpcolonne, tuileLongueur, tuileLargeur);
                     listeTuile.add(tmptuile);
                     stage.addActor(tmptuile);
                     tmpcolonne++;
                 }
             }
         }
    }

}
