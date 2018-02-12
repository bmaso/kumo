package com.kennycason.kumo;

import com.kennycason.kumo.collide.checkers.CollisionChecker;
import com.kennycason.kumo.collide.Collidable;
import com.kennycason.kumo.image.CollisionRaster;
import com.kennycason.kumo.image.ImageRotation;
import com.kennycason.kumo.padding.Padder;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by kenny on 6/29/14.
 */
public class Word implements Collidable {

    private final CollisionChecker collisionChecker;

    private final String text;

    private final Color color;
    
    private final FontMetrics fontMetrics;

    private final Point position = new Point(0, 0);
    
    private double theta = 0.0;
    
    private int padding = 0;

    private BufferedImage bufferedImage;

    private CollisionRaster collisionRaster;

    public Word(final String word,
                final Color color,
                final FontMetrics fm,
                final CollisionChecker collisionChecker) {
        this.text = word;
        this.color = color;
        this.fontMetrics = fm;
        this.collisionChecker = collisionChecker;
        // get the height of a line of text in this font and render context
        final int maxDescent = fontMetrics.getMaxDescent();
        // get the advance of my text in this font and render context
        final int width = fontMetrics.stringWidth(word);

        this.bufferedImage = new BufferedImage(width, fontMetrics.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics = (Graphics2D) this.bufferedImage.getGraphics();
        graphics.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        graphics.setColor(color);
        graphics.setFont(fontMetrics.getFont());

        graphics.drawString(word, 0, fontMetrics.getHeight() - maxDescent);

        this.collisionRaster = new CollisionRaster(this.bufferedImage);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(final BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        this.collisionRaster = new CollisionRaster(bufferedImage);
    }

    public String getText() {
        return text;
    }
    
    public Color getColor() {
    		return this.color;
    }

    public Point getPosition() {
        return position;
    }

    public Dimension getDimension() {
        return collisionRaster.getDimension();
    }
    
    public double getTheta() {
    	  return theta;
    }
    
    public void setTheta(double newTheta) {
    	  this.theta = newTheta;
    	  this.bufferedImage = ImageRotation.rotate(this.bufferedImage, theta);
    }
    
    public void padWith(Padder padder, int newPadding) {
    	  int padDelta = newPadding - this.padding;
    	  this.padding = newPadding;
    	  padder.pad(this, padDelta);
    }
    
    public int getPadding() {
    	  return this.padding;
    }
    
    public FontMetrics getFontMetrics() {
    	  return this.fontMetrics;
    }

    @Override
    public CollisionRaster getCollisionRaster() {
        return collisionRaster;
    }

    @Override
    public boolean collide(final Collidable collidable) {
        return collisionChecker.collide(this, collidable);
    }

    public void draw(final CollisionRaster collisionRaster) {
        collisionRaster.mask(collisionRaster, position);
    }

    @Override
    public String toString() {
        return "Word {" +
                "text='" + text + '\'' +
                ", color=" + color +
                ", x=" + position.x +
                ", y=" + position.y +
                ", str_width=" + fontMetrics.stringWidth(text) +
                ", str_height=" + fontMetrics.getHeight() +
                ", theta=" + theta +
                ", padding=" + padding +
                ", width=" + bufferedImage.getWidth() +
                ", height=" + bufferedImage.getHeight() +
                ", fontMetrics=" + fontMetrics +
                '}';
    }

}
