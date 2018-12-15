package com.to52.taquin.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.to52.taquin.PuzzleGame;

public class FirstScreen implements Screen {
    //variable de structure libGDX
    private PuzzleGame game;
    private OrthographicCamera camera;
    private StretchViewport gamePort;

    //variable utilitaire
    private Texture background;
    private TextButton jouer;
    private Stage stage;
    private String buttonLabel;

    public FirstScreen(PuzzleGame game, String buttonLabel){
        this.game = game;
        camera = new OrthographicCamera();
        gamePort = new StretchViewport(1280,720,camera);
        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        background = new Texture(Gdx.files.internal("background.jpg"));
        this.buttonLabel = buttonLabel;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createButton();
    }

    /**
     * Création du boutons pour lancer une partie
     */
    private void createButton() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        jouer = new TextButton(buttonLabel, style);
        jouer.setPosition(gamePort.getWorldWidth()/2 - jouer.getWidth(), gamePort.getWorldHeight()/2 + jouer.getHeight()/2);
        jouer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new PlayScreen(game));
                dispose();
            }
        });
        jouer.setTransform(true);
        jouer.setScale(2f);
        stage.addActor(jouer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(background,0,0);
        stage.getBatch().end();
        stage.draw();
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
}
