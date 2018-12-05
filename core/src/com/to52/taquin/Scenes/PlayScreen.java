package com.to52.taquin.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.to52.taquin.TaquinGame;
import com.to52.taquin.Tuile;

import java.util.ArrayList;
import java.util.Collections;


public class PlayScreen implements Screen {
    private TaquinGame game;
    private OrthographicCamera camera;
    private StretchViewport gamePort;
    public Texture testTexture;
    private Texture background;
    private TextureRegion[][] tuilePuzzle;
    public ArrayList<Tuile> listeTuile = new ArrayList<Tuile>();
    public ArrayList<Tuile> solution = new ArrayList<Tuile>();
    private Stage stage;
    private int colonne = 3;
    private int ligne = 3;

    private TextButton melanger;
    private TextButton grille3x3;
    private TextButton grille4x4;
    private TextButton grille5x5;
    private TextButton rejouer;

    public PlayScreen(TaquinGame game){
        this.game = game;
        camera = new OrthographicCamera();
        gamePort = new StretchViewport(1280,720,camera);
        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        testTexture = new Texture(Gdx.files.internal("images\\shrek.jpeg"));
        background = new Texture(Gdx.files.internal("background.jpg"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        generatePuzzle(ligne,colonne);
        createButtons();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(checkFinish()){
            //System.out.println(checkFinish());
            removeTuile(solution);
            removeActors();
            rejouer.setVisible(true);
            stage.act();
            stage.getBatch().begin();
            stage.getBatch().draw(background,0,0);
            stage.getBatch().end();
            stage.addActor(rejouer);
            stage.draw();

        }else{
            camera.update();
            stage.act();
            stage.getBatch().begin();
            stage.getBatch().draw(background,0,0);
            stage.getBatch().draw(testTexture,
                    (float) (testTexture.getWidth()*1.4),
                    (gamePort.getWorldHeight()-testTexture.getHeight()) / 2);
            stage.getBatch().end();
            stage.draw();
        }


}

    @Override
    public void resize(int width, int height) {

        gamePort.update(width, height, true);
        stage.setViewport(gamePort);

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
        stage.dispose();
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
         int ordre = 0;
         for (TextureRegion[] tr : tuilePuzzle) {
             for (TextureRegion t : tr) {
                 if (tmpcolonne%(colonne-1) == 0 && tmpcolonne != 0){
                     Tuile tmptuile = new Tuile(t, tmpligne, tmpcolonne, tuileLongueur, tuileLargeur, ordre, this);
                     solution.add(tmptuile);
                     stage.addActor(tmptuile);
                     tmpcolonne = 0;
                     tmpligne++;
                     ordre++;
                 }else{
                     Tuile tmptuile = new Tuile(t, tmpligne, tmpcolonne, tuileLongueur, tuileLargeur, ordre, this);
                     solution.add(tmptuile);
                     stage.addActor(tmptuile);
                     tmpcolonne++;
                     ordre++;
                 }
             }
         }
         listeTuile = (ArrayList<Tuile>) solution.clone();
         shuffleAndPlace(listeTuile, ligne, colonne);
    }

    //methode utilisé pour mélanger les tuiles et les placer
    private void shuffleAndPlace(ArrayList<Tuile> liste, int ligne, int colonne){
        Collections.shuffle(liste);
        for (Tuile t : liste){
            int index = liste.indexOf(t);
            int x = index%ligne;
            int y = index/colonne;
            t.updatePos(x, y);
        }

    }

    private boolean checkFinish(){
        boolean finish = true;
        int i = ligne*(ligne-1);
        int col = 0;
        for (Tuile t : listeTuile){
            if (t.solution != solution.get(i).solution){
                finish = false;
            }
            if(i< ligne){
                col++;
                i = ligne*(ligne-1) + col;
            }else{
                i -= ligne;
            }
        }
        return finish;
    }

    public StretchViewport getGamePort(){
        return this.gamePort;
    }

    private void createButtons(){
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        melanger = new TextButton("MELANGER",style);
        melanger.setPosition(40,690);
        melanger.setHeight(20);
        melanger.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                shuffleAndPlace(listeTuile, ligne, colonne);
            }
        });

        grille3x3 = new TextButton("GRILLE 3X3",style);
        grille3x3.setPosition(melanger.getX() + melanger.getWidth() + 100,690);
        grille3x3.setHeight(20);
        grille3x3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                removeTuile(solution);
                solution = new ArrayList<Tuile>();
                ligne = 3;
                colonne = 3;
                generatePuzzle(ligne,colonne);
            }
        });


        grille4x4 = new TextButton("GRILLE 4X4",style);
        grille4x4.setPosition(grille3x3.getX()+grille3x3.getWidth() + 100,690);
        grille4x4.setHeight(20);
        grille4x4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                removeTuile(solution);
                solution = new ArrayList<Tuile>();
                ligne = 4;
                colonne = 4;
                generatePuzzle(ligne,colonne);
            }
        });

        grille5x5 = new TextButton("GRILLE 5X5",style);
        grille5x5.setPosition(grille4x4.getX()+grille4x4.getWidth() + 100,690);
        grille5x5.setHeight(20);
        grille5x5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                removeTuile(solution);
                solution = new ArrayList<Tuile>();
                ligne = 5;
                colonne = 5;
                generatePuzzle(ligne,colonne);
            }
        });

        rejouer = new TextButton("REJOUER",style);
        rejouer.setHeight(20);
        rejouer.setPosition(gamePort.getWorldWidth()/2 - rejouer.getWidth()/2,gamePort.getWorldHeight()/2 - rejouer.getHeight()/2);
        rejouer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){

                solution = new ArrayList<Tuile>();
                ligne = 5;
                colonne = 5;
                generatePuzzle(ligne,colonne);
                createButtons();
            }
        });
        rejouer.setVisible(false);

        stage.addActor(grille3x3);
        stage.addActor(grille4x4);
        stage.addActor(grille5x5);
        stage.addActor(melanger);
        stage.addActor(rejouer);
    }

    private void removeTuile(ArrayList<Tuile> liste){
        for (Tuile t : liste){
            t.remove();
        }
    }

    private void removeActors(){
        for(Actor a : stage.getActors()){
            a.remove();
        }
    }
}
