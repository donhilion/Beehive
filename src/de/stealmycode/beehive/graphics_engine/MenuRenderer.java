package de.stealmycode.beehive.graphics_engine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import de.stealmycode.beehive.model.world.IDrawable;
import de.stealmycode.beehive.model.world.animals.IMovable;
import de.stealmycode.beehive.utils.Constants;
import de.stealmycode.beehive.utils.Direction;
import de.stealmycode.beehive.utils.Position;

import de.stealmycode.beehive.graphics_engine.FontManager;


public class MenuRenderer implements IRenderer  {

	private int width;
	private int height;
	
//	public UnicodeFont fancy;

	
	private ImageManager imageManager;
	
	
	public boolean init(int width, int height){
		this.width = width;
		this.height = height;
		
//		createFancyFont(fancypath);
		
		setViewport();

		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);		
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		return true;
	}
	
	/*
	private void drawSprite(Sprite sprite, float x, float y, float angle, boolean absolute) {
		Color.white.bind();
		sprite.texture.bind();
		
		
	}
	*/

	private void drawHUD(Sprite sprite,float x1, float y1,float x2,float y2) throws SlickException{
		Color.white.bind();
		
//		String[] hudElements = {"Start","Speichern","Laden","Ende"};
	
		sprite.texture.bind();
		
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(sprite.x,sprite.y);
		GL11.glVertex2f(x1, height-y1);										//oben-links
		GL11.glTexCoord2f(sprite.x+sprite.rectWidth,sprite.y);
		GL11.glVertex2f(x2, height-y1);										//oben-rechts
		GL11.glTexCoord2f(sprite.x+sprite.rectWidth,sprite.y+sprite.rectHeight);
		GL11.glVertex2f(x2, height-y2);										//unten-rechts
		GL11.glTexCoord2f(sprite.x,sprite.y+sprite.rectHeight);
		GL11.glVertex2f(x1, height-y2);										//unten-links
		GL11.glEnd();

//		GL11.glBegin(GL11.GL_)
			

		
	}
	
	public void setImageRenderer(ImageManager imageManager) {
		this.imageManager = imageManager;
	}
	
	public void draw(){
		try {
		if(imageManager == null) return;
		setViewport();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
//		Sprite hud = imageManager.getSprite(40);


		
//		FontManager newf = 	;

//		createFancyFont("ressources/MysteryQuest-Regular.ttf");
		
		
		Sprite newgame;
		newgame = imageManager.getSprite(39);	// königin bild
		drawHUD(newgame, 10 , 10, 310, 310);
		newgame = null;

		newgame = imageManager.getSprite(38);	// worrior bild
		drawHUD(newgame, 500 , 280, 800, 580);
		newgame = null;
		

		for(int t=1;t<=5;t++){
			newgame = null;
			newgame = imageManager.getSprite(40+t);
			drawHUD(newgame, (width/2)-160, 10+70*t, (width/2)+160, 10+140*t);	
		}
		
		FontManager bla = new FontManager();
//		GL11.glBegin(GL11.GL_BLEND);
//		GL11.glScalef(1.1f, 1.1f, 1.1f);
		bla.drawString("blabla1234567890");
//		GL11.glEnd();

//		FontManager.drawString("blabla");
//		Sprite newgame = imageManager.getSprite(41);
//		drawHUD(newgame, (width/2)-160, 100, (width/2)+160, 200);
		}		
		catch (SlickException e) {}
		
	}


	@Override
	public void scrollX(int x) {
		return;
	}
	
	@Override
	public void scrollY(int y) {
		return;
	}
	
	@Override
	public Position getGamePosition(int x, int y) {
		return new Position(x,y);
	}
	
	private void setViewport() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, 0, height, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
}