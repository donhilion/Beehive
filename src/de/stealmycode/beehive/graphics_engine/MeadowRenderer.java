package de.stealmycode.beehive.graphics_engine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import de.stealmycode.beehive.model.world.IDrawable;
import de.stealmycode.beehive.model.world.animals.IMovable;
import de.stealmycode.beehive.utils.Direction;

/**
 * This renderer will render the meadow view.
 * 
 * @author donhilion
 *
 */
public class MeadowRenderer implements IRenderer {
	
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
	
	private int camX = 0;
	private int camY = 0;
	
	private int camDX = 0;
	private int camDY = 0;
	
	private int scrollSpeed = 5;
	
	private ImageManager imageManager;
	
	public boolean init(int width, int height) {
		this.width = width;
		this.height = height;
		
		setViewport();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		return true;
	}
	
	private float getAngleForDirection(Direction direction) {
		switch(direction) {
		case SOUTH_WEST:
			return ANGLE_60;
		case NORTH_WEST:
			return 2.0f * ANGLE_60;
		case NORTH:
			return 3.0f * ANGLE_60;
		case NORTH_EAST:
			return 4.0f * ANGLE_60;
		case SOUTH_EAST:
			return 5.0f * ANGLE_60;
		default:
			return 0.0f;
		}
	}
	
	private void drawSprite(Sprite sprite, float x, float y, float angle, boolean absolute) {
		Color.white.bind();
		sprite.texture.bind(); // or GL11.glBind(texture.getTextureID());
		
		angle += (float)(Math.PI / 4.0);
		float cosVal = (float) Math.cos(angle);
		float sinVal = (float) Math.sin(angle);
		
		float x1 = x - sprite.width * (absolute ? 1 : sizeOfComb) * DIV_SQRT_2 * sinVal;
		float y1 = y + sprite.height * (absolute ? 1 : sizeOfComb) * DIV_SQRT_2 * cosVal;
		
		float x2 = x + sprite.width * (absolute ? 1 : sizeOfComb) * DIV_SQRT_2 * cosVal;
		float y2 = y + sprite.height * (absolute ? 1 : sizeOfComb) * DIV_SQRT_2 * sinVal;
		
		float x3 = x + sprite.width * (absolute ? 1 : sizeOfComb) * DIV_SQRT_2 * sinVal;
		float y3 = y - sprite.height * (absolute ? 1 : sizeOfComb) * DIV_SQRT_2 * cosVal;
		
		float x4 = x - sprite.width * (absolute ? 1 : sizeOfComb) * DIV_SQRT_2 * cosVal;
		float y4 = y - sprite.height * (absolute ? 1 : sizeOfComb) * DIV_SQRT_2 * sinVal;
		

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
		for(float x=-background.width/2.0f; x<width+background.width/2.0f; x+=background.width) {
			for(float y=-background.height/2.0f; y<height+background.height/2.0f; y+=background.height) {
				drawSprite(background, x, y, 0.0f, true);
			}
		}
	}
	
	private void renderField() {
		Color.black.bind();		
		GL11.glLineWidth(2.0f);
		
		for(int x=0; x<combCountX; x++) {
			for(int y=0; y<combCountY; y++) {
				GL11.glBegin(GL11.GL_LINE_LOOP);
				GL11.glVertex2f(x*sizeOfComb*0.75f,
						height-(y*sizeOfComb*SIN_60
								+sizeOfComb*0.5f*(1+SIN_60*(float)(x%2))));
				
				GL11.glVertex2f(x*sizeOfComb*0.75f+sizeOfComb*0.25f,
						height-(y*sizeOfComb*SIN_60
								+sizeOfComb*0.5f*(1-SIN_60+SIN_60*(float)(x%2))));
				
				GL11.glVertex2f(x*sizeOfComb*0.75f+sizeOfComb*0.75f,
						height-(y*sizeOfComb*SIN_60
								+sizeOfComb*0.5f*(1-SIN_60+SIN_60*(float)(x%2))));
				
				GL11.glVertex2f(x*sizeOfComb*0.75f+sizeOfComb,
						height-(y*sizeOfComb*SIN_60
								+sizeOfComb*0.5f*(1+SIN_60*(float)(x%2))));
				
				GL11.glVertex2f(x*sizeOfComb*0.75f+sizeOfComb*0.75f,
						height-(y*sizeOfComb*SIN_60
								+sizeOfComb*0.5f*(1+SIN_60+SIN_60*(float)(x%2))));
				
				GL11.glVertex2f(x*sizeOfComb*0.75f+sizeOfComb*0.25f,
						height-(y*sizeOfComb*SIN_60
								+sizeOfComb*0.5f*(1+SIN_60+SIN_60*(float)(x%2))));
				
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
			
			float angle = getAngleForDirection(object.getDirection());
			
			drawSprite(sprite, x, y, angle, false);
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
			
			float angle = getAngleForDirection(object.getDirection());
			
			System.out.println(angle/Math.PI);
			
			x -= object.getProgress()*0.75f*sizeOfComb*Math.sin(angle);
			y += object.getProgress()*SIN_60*sizeOfComb*Math.cos(angle);
			
			
			drawSprite(sprite, x, y, angle, false);
		}
	}
	
	public void draw() {
		if(imageManager == null) {
			return;
		}
		camX += camDX;
		camY += camDY;
		setViewport();
		
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

	@Override
	public void scrollX(int x) {
		camDX = x * scrollSpeed;
	}
	
	@Override
	public void scrollY(int y) {
		camDY = y * scrollSpeed;
	}
	
	private void setViewport() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(camX, width+camX, camY, height+camY, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
}
