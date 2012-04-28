package de.stealmycode.beehive.graphics_engine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import de.stealmycode.beehive.model.world.IDrawable;
import de.stealmycode.beehive.model.world.animals.IMovable;

/**
 * This renderer will render the meadow view.
 * 
 * @author donhilion
 *
 */
public class MeadowRenderer {
	
	/**
	 * The id of the background image.
	 */
	private static final int BACKGROUND_ID = 0;
	
	private static final float DIV_SQRT_2 = (float) (1.0 / Math.sqrt(2.0));
	
	private static final float SIN_60 = 0.866025404f;
	
	private static final float ANGLE_60 = (float)(Math.PI/3.0f);
	
	/**
	 * List of static objects to draw.
	 */
	private List<IDrawable> staticObjects;
	/**
	 * List of dynamic objects to draw.
	 */
	private List<IMovable> dynamicObjects;
	
	/**
	 * The size of on comb. This is equal to the width of the comb.
	 */
	private int sizeOfComb = 50;
	/**
	 * Count of the combs in x-direction.
	 */
	private int combCountX = 21;
	/**
	 * Count of the combs in y-direction.
	 */
	private int combCountY = 18;
	
	private int width;
	private int height;
	
	private ImageManager imageManager;
	
	public boolean init(int width, int height) {
		this.width = width;
		this.height = height;
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, 0, height, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		return true;
	}
	
	private void drawSprite(Sprite sprite, float x, float y, float angle) {
		Color.white.bind();
		sprite.texture.bind(); // or GL11.glBind(texture.getTextureID());
		
		angle += (float)(Math.PI / 4.0);
		float midX = x + sprite.width / 2.0f;
		float midY = y + sprite.height / 2.0f;
		float cosVal = (float) Math.cos(angle);
		float sinVal = (float) Math.sin(angle);
		
		float x1 = midX - sprite.width * DIV_SQRT_2 * sinVal;
		float y1 = midY + sprite.height * DIV_SQRT_2 * cosVal;
		
		float x2 = midX + sprite.width * DIV_SQRT_2 * cosVal;
		float y2 = midY + sprite.height * DIV_SQRT_2 * sinVal;
		
		float x3 = midX + sprite.width * DIV_SQRT_2 * sinVal;
		float y3 = midY - sprite.height * DIV_SQRT_2 * cosVal;
		
		float x4 = midX - sprite.width * DIV_SQRT_2 * cosVal;
		float y4 = midY - sprite.height * DIV_SQRT_2 * sinVal;
		

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(sprite.x,sprite.y);
		GL11.glVertex2f(x1, y1);
		GL11.glTexCoord2f(sprite.x+sprite.rectWidth,sprite.y);
		GL11.glVertex2f(x2, y2);
		GL11.glTexCoord2f(sprite.x+sprite.rectWidth,sprite.y+sprite.rectHeight);
		GL11.glVertex2f(x3, y3);
		GL11.glTexCoord2f(sprite.x,sprite.y+sprite.rectHeight);
		GL11.glVertex2f(x4, y4);
		GL11.glEnd();
	}
	
	private void renderBackground() {
		Sprite background = imageManager.getSprite(BACKGROUND_ID);
		for(float x=0; x<width; x+=background.width) {
			for(float y=0; y<height; y+=background.height) {
				drawSprite(background, x, y, 0.0f);
			}
		}
	}
	
	private void renderField() {
		Color.green.bind();		
		GL11.glLineWidth(2.0f);
		
		for(int x=0; x<combCountX; x++) {
			for(int y=0; y<combCountY; y++) {
				GL11.glBegin(GL11.GL_LINE_LOOP);
				GL11.glVertex2f(x*sizeOfComb*0.75f,
						height-(y*sizeOfComb*SIN_60
								+sizeOfComb*0.5f*(1+(float)(x%2))));
				
				GL11.glVertex2f(x*sizeOfComb*0.75f+sizeOfComb*0.25f,
						height-(y*sizeOfComb*SIN_60
								+sizeOfComb*0.5f*(1-SIN_60+(float)(x%2))));
				
				GL11.glVertex2f(x*sizeOfComb*0.75f+sizeOfComb*0.75f,
						height-(y*sizeOfComb*SIN_60
								+sizeOfComb*0.5f*(1-SIN_60+(float)(x%2))));
				
				GL11.glVertex2f(x*sizeOfComb*0.75f+sizeOfComb,
						height-(y*sizeOfComb*SIN_60
								+sizeOfComb*0.5f*(1+(float)(x%2))));
				
				GL11.glVertex2f(x*sizeOfComb*0.75f+sizeOfComb*0.75f,
						height-(y*sizeOfComb*SIN_60
								+sizeOfComb*0.5f*(1+SIN_60+(float)(x%2))));
				
				GL11.glVertex2f(x*sizeOfComb*0.75f+sizeOfComb*0.25f,
						height-(y*sizeOfComb*SIN_60
								+sizeOfComb*0.5f*(1+SIN_60+(float)(x%2))));
				
				GL11.glEnd();
			}
		}		
	}
	
	private void renderStatics() {
		if(staticObjects == null) {
			return;
		}
		
		Color.white.bind();
			
		for(IDrawable object : staticObjects) {
			Sprite sprite = imageManager.getSprite(object.getImageID());
			sprite.texture.bind();
			
			float x = sizeOfComb*(0.75f*(float)(object.getPosition().getX())+0.5f);
			float y = height-sizeOfComb*(SIN_60*(float)(object.getPosition().getY())
					+0.5f*(1+(float)(object.getPosition().getX() % 2)));
			
			float angle = 0.0f;
			switch(object.geDirection()) {
			case SOUTH_WEST:
				angle = -ANGLE_60;
			case NORT_WEST:
				angle = -(float)(Math.PI) - ANGLE_60;
			case SOUTH_EAST:
				angle = ANGLE_60;
			case NORTH_EAST:
				angle = (float)(Math.PI) + ANGLE_60;
			case NORTH:
				angle = (float)(2.0f*Math.PI);
			}
			
			drawSprite(sprite, x, y, angle);
		}
	}
	
	private void renderDynamics() {
		if(dynamicObjects == null) {
			return;
		}
		
		Color.white.bind();
			
		for(IMovable object : dynamicObjects) {
			Sprite sprite = imageManager.getSprite(object.getImageID());
			sprite.texture.bind();
			
			float x = sizeOfComb*(0.75f*(float)(object.getPosition().getX())+0.5f);
			float y = height-sizeOfComb*(SIN_60*(float)(object.getPosition().getY())
					+0.5f*(1+(float)(object.getPosition().getX() % 2)));
			
			float angle = 0.0f;
			switch(object.geDirection()) {
			case SOUTH_WEST:
				angle = -ANGLE_60;
			case NORT_WEST:
				angle = -(float)(Math.PI) - ANGLE_60;
			case SOUTH_EAST:
				angle = ANGLE_60;
			case NORTH_EAST:
				angle = (float)(Math.PI) + ANGLE_60;
			case NORTH:
				angle = (float)(2.0f*Math.PI);
			}
			
			drawSprite(sprite, x, y, angle);
		}
	}
	
	public void draw() {
		if(imageManager == null) {
			return;
		}
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		renderBackground();
		renderField();
		renderStatics();
		renderDynamics();
	}
	
	public void setImageRenderer(ImageManager imageManager) {
		this.imageManager = imageManager;
	}
	
	public void setStaticObjects(List<IDrawable> list) {
		staticObjects = list;
	}
	
	public void setDynamicObjects(List<IMovable> list) {
		dynamicObjects = list;
	}
}
