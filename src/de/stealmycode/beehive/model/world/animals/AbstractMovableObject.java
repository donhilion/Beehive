package de.stealmycode.beehive.model.world.animals;

import de.stealmycode.beehive.model.world.AbstractDrawableObject;
import de.stealmycode.beehive.model.world.IAttackable;
import de.stealmycode.beehive.utils.Position;

public abstract class AbstractMovableObject extends AbstractDrawableObject implements IMovable, IAttackable {
    
    @Override
    public abstract int getAttack();

    @Override
    public abstract int getDefense();

    @Override
    public abstract int getHealth();

    private float progress = 0.0f;
    
    public AbstractMovableObject(Position position) {
        super(position);
    }
    
    @Override
    public abstract int getImageID() ;

    @Override
    public float getProgress() {
        return 0;
    }
    
    public void setProgress(float p) {
        if (p < 0 || progress > 1) {
            // TODO: Print Log Message
            if (p < 0) p = p * -1;
            if (p > 1) p = p - (int) p;
        }
        progress = p;
    }
    

}
