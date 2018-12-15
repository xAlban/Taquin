package com.to52.taquin;

import com.badlogic.gdx.Game;
import com.to52.taquin.Scenes.*;

public class PuzzleGame extends Game {

	@Override
	public void create () {
		setScreen(new FirstScreen(this, "JOUER"));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () { super.dispose(); }
}
