package com.to52.taquin.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.to52.taquin.PuzzleGame;
import com.to52.taquin.Tuile;

import java.util.ArrayList;
import java.util.Collections;


public class PlayScreen implements Screen {
    //variable de structure libGDX
    private PuzzleGame game;
    private OrthographicCamera camera;
    private StretchViewport gamePort;
    private Stage stage;

    //variable utilitaire
    public Texture testTexture;
    private Texture background;
    private TextureRegion[][] tuilePuzzle;
    private Table buttonTable;
    private TextButton melanger;
    private TextButton grille3x3;
    private TextButton grille4x4;
    private TextButton grille5x5;
    public TextButton rejouer;

    //variable propre au Puzzle
    public ArrayList<Tuile> listeTuile = new ArrayList<Tuile>();
    public ArrayList<Tuile> solution = new ArrayList<Tuile>();
    public int colonne = 3;
    public int ligne = 3;
    public boolean finished = false;
    private boolean nextGame = false;


    public PlayScreen(PuzzleGame game){
        this.game = game;
        camera = new OrthographicCamera();
        gamePort = new StretchViewport(1280,720,camera);
        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        testTexture = new Texture(Gdx.files.internal("images\\shrek.jpeg"));
        background = new Texture(Gdx.files.internal("background.jpg"));
        buttonTable = new Table();
        stage = new Stage(gamePort);
        buttonTable.setWidth(stage.getWidth());
        buttonTable.setPosition(0,gamePort.getWorldHeight()-40);
        Gdx.input.setInputProcessor(stage);

        generatePuzzle(ligne,colonne);
        createButtons();
    }


    @Override
    public void render(float delta) {
        camera.update();
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(background,0,0);
        stage.getBatch().draw(testTexture,
                (float) (testTexture.getWidth()*1.4),
                (gamePort.getWorldHeight()-testTexture.getHeight()) / 2);
        stage.getBatch().end();
        stage.draw();

        if(finished){
            if(nextGame){
                removeTuile(solution);
                removeActors();
                game.setScreen(new FirstScreen(game, "REJOUER"));
            }
        }
}

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height, true);
        stage.setViewport(gamePort);
    }


    @Override
    public void dispose() {
        stage.dispose();
    }


    /**
     * Genere le puzzle avec une taille de grille en entrée
     *
     * @param  ligne le nombre de ligne dans notre grille
     * @param  colonne le nombre de colonne dans notre grille
     */
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


    /**
     * Methode permettant de mélanger les tuiles et de les placer sur la scene
     *
     * @param  liste  la liste de toute les tuiles
     * @param  ligne le nombre de ligne dans notre grille
     * @param  colonne le nombre de colonne dans notre grille
     */
    private void shuffleAndPlace(ArrayList<Tuile> liste, int ligne, int colonne){
        Collections.shuffle(liste);
        for (Tuile t : liste){
            int index = liste.indexOf(t);
            int x = index%ligne;
            int y = index/colonne;
            t.updatePos(x, y);
        }

    }


    /**
     * Création des boutons permettant de changer la taille de la grille
     * et du bouton caché de retour au menu
     */
    private void createButtons(){
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();

        melanger = new TextButton("MELANGER",style);
        melanger.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                shuffleAndPlace(listeTuile, ligne, colonne);
            }
        });

        grille3x3 = new TextButton("GRILLE 3X3",style);
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

        TextButton.TextButtonStyle styleRouge = new TextButton.TextButtonStyle();
        styleRouge.font = new BitmapFont();
        styleRouge.fontColor = Color.RED;
        rejouer = new TextButton("RETOUR MENU",styleRouge);
        rejouer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){

                nextGame = true;
            }
        });
        rejouer.setVisible(false);

        buttonTable.add(melanger).pad(30);
        buttonTable.add(grille3x3).pad(30);
        buttonTable.add(grille4x4).pad(30);
        buttonTable.add(grille5x5).pad(30);
        buttonTable.add(rejouer).pad(30);
        stage.addActor(buttonTable);
    }


    /**
     * Enleve toutes les tuiles de la scene
     *
     * @param  liste  la liste de toute les tuiles
     */
    private void removeTuile(ArrayList<Tuile> liste){
        for (Tuile t : liste){
            t.remove();
        }
    }


    /**
     * Enleve toutes les acteurs de la scene
     */
    private void removeActors(){
        for(Actor a : stage.getActors()){
            a.remove();
        }
    }


    public StretchViewport getGamePort(){
        return this.gamePort;
    }


    @Override
    public void show() {

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


}
