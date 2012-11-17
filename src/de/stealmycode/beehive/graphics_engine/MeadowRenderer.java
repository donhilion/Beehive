package de.stealmycode.beehive.graphics_engine;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.util.Log;

import de.stealmycode.beehive.model.map.MyPolygon;
import de.stealmycode.beehive.model.world.IDrawable;
import de.stealmycode.beehive.model.world.animals.IMovable;
import de.stealmycode.beehive.utils.Constants;
import de.stealmycode.beehive.utils.Direction;
import de.stealmycode.beehive.utils.Position;

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
	private int sizeOfComb = Constants.SIZE_OF_COMB;
	/**
	 * Count of the combs in x-direction.
	 */
	private int combCountX = 21;
	/**
	 * Count of the combs in y-direction.
	 */
	private int combCountY = 13;
	
	/**
	 * Map of polygons that representate the field map
	 */
	private MyPolygon[][] polygonMap = null;
	
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
		
		polygonMap = new MyPolygon[combCountX][combCountY];
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
		
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0.0f);
		GL11.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		GL11.glScalef(sprite.width * (absolute ? 1 : sizeOfComb), sprite.height * (absolute ? 1 : sizeOfComb), 0);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(sprite.x,sprite.y);
		GL11.glVertex2f(-0.5f, -0.5f);
		GL11.glTexCoord2f(sprite.x+sprite.rectWidth,sprite.y);
		GL11.glVertex2f(0.5f, -0.5f);
		GL11.glTexCoord2f(sprite.x+sprite.rectWidth,sprite.y+sprite.rectHeight);
		GL11.glVertex2f(0.5f, 0.5f);
		GL11.glTexCoord2f(sprite.x,sprite.y+sprite.rectHeight);
		GL11.glVertex2f(-0.5f, 0.5f);
		GL11.glEnd();
		
		GL11.glPopMatrix();
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
		
		for(int x = 0; x < combCountX; x++) {
			for(int y = 0; y < combCountY; y++) {
				
				GL11.glBegin(GL11.GL_LINE_LOOP);
				
				float x1 = Math.round(x*sizeOfComb*0.75f);
				float y1 = Math.round(height-(y*sizeOfComb*Constants.SIN_60 + sizeOfComb*0.5f*(1+Constants.SIN_60*(float)(x%2))));			
				GL11.glVertex2f(x1, y1);
				
				float x2 = Math.round(x*sizeOfComb*0.75f+sizeOfComb*0.25f);
				float y2 = Math.round(height-(y*sizeOfComb*Constants.SIN_60 + sizeOfComb*0.5f*(1-Constants.SIN_60+Constants.SIN_60*(float)(x%2))));
				GL11.glVertex2f(x2, y2);
				
				
				float x3 = Math.round(x*sizeOfComb*0.75f+sizeOfComb*0.75f);
				float y3 = Math.round(height-(y*sizeOfComb*Constants.SIN_60 + sizeOfComb*0.5f*(1-Constants.SIN_60+Constants.SIN_60*(float)(x%2))));
				GL11.glVertex2f(x3,	y3);
								
				float x4 = Math.round(x*sizeOfComb*0.75f+sizeOfComb);
				float y4 = Math.round(height-(y*sizeOfComb*Constants.SIN_60 + sizeOfComb*0.5f*(1+Constants.SIN_60*(float)(x%2))));				
				GL11.glVertex2f(x4, y4);
				
				float x5 = Math.round(x*sizeOfComb*0.75f+sizeOfComb*0.75f);
				float y5 = Math.round(height-(y*sizeOfComb*Constants.SIN_60 + sizeOfComb*0.5f*(1+Constants.SIN_60+Constants.SIN_60*(float)(x%2))));
				GL11.glVertex2f(x5, y5);
				
				float x6 = Math.round(x*sizeOfComb*0.75f+sizeOfComb*0.25f);
				float y6 = Math.round(height-(y*sizeOfComb*Constants.SIN_60 + sizeOfComb*0.5f*(1+Constants.SIN_60+Constants.SIN_60*(float)(x%2))));
				GL11.glVertex2f(x6, y6);
				
				if(polygonMap[x][y] == null)
				{
					int[] xArray = {(int) x1, (int) x2, (int) x3, (int) x4, (int) x5, (int) x6};
					int[] yArray = {(int) y1, (int) y2, (int) y3, (int) y4, (int) y5, (int) y6};
					
					polygonMap[x][y] = new MyPolygon(xArray, yArray, 6);
					
				}
				
				GL11.glEnd();
				
//			    GL11.glBegin(GL11.GL_POLYGON);
//			    GL11.glVertex3f(-60  x, -10 * y, 0);   
////		        GL11.glVertex3f(combCountX * sizeOfComb, combCountY * sizeOfComb, 0);   
//		        GL11.glVertex3f(-47.5f * x, -10 * y, 0);
//		        GL11.glVertex3f(-35f * x, 2.5f * y, 0);
//		        GL11.glVertex3f(-47.5f * x, -10 * y, 0);
//		        GL11.glVertex3f(-60 * x, -10 * y, 0);
//		        GL11.glVertex3f(-72.5f * x, -35 * y, 0);
//		    GL11.glEnd();
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
			float y = height-sizeOfComb*(Constants.SIN_60*(float)(object.getPosition().getY())
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
			
			
//			float x = Math.round(polygonMap[object.getPosition().getX()][object.getPosition().getY()].getBounds().getCenterX());
//			float y = Math.round(polygonMap[object.getPosition().getX()][object.getPosition().getY()].getBounds().getCenterY());
			float x = sizeOfComb*(0.75f*(float)(object.getPosition().getX())+0.5f);
			float y = height-sizeOfComb*(Constants.SIN_60*(float)(object.getPosition().getY())
					+0.5f*(1+(float)(object.getPosition().getX() % 2)));
			
//			float y = height-( ((float) object.getPosition().getY()) *sizeOfComb*Constants.SIN_60 + sizeOfComb*0.5f*(1+Constants.SIN_60*(float)(x%2)));
			
			float angle = getAngleForDirection(object.getDirection());
			
//			System.out.println(angle/Math.PI);
			
			x -= object.getProgress()*0.75f*sizeOfComb*Math.sin(angle);
			y += object.getProgress()*Constants.SIN_60*sizeOfComb*Math.cos(angle);
			
			
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

	@Override
	public Position getGamePosition(int x, int y) {
		
//		Log.info("Trying to find Polygon at: "+ x + " | " + y);
				
		for (int x1 = 0 ; x1 < combCountX ; ++x1)
		{
			for(int y1 = 0 ; y1 < combCountY ; ++y1)
			{
				MyPolygon p = polygonMap[x1][y1];
				if(p != null && p.collidesWith(x, y))
				{
//					Log.info("New position of movable object: " + x1 + " | " + y1);
					return new Position(x1, y1);
				}
			}
		}
				
		Log.warn("can't find position...!!");
		return null;
	}
}
