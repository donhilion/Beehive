package de.stealmycode.beehive.model.world;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import de.stealmycode.beehive.model.world.animals.AbstractMovableObject;
import de.stealmycode.beehive.utils.Position;

public class Pathfinder {
    
    private class Item {
        public Item(Position position, int priority) {
            this.position = position;
            this.priority = priority;
        }
        int g;
        Item predecessor = null;
        Position position;
        int priority;
//        
//        private boolean equals(Item item) {
//            if (g != item.g) return false;
//            if (priority != item.priority) return false;
//            if (!predecessor.equals(item.predecessor)) return false;
//            if (!position.equals(item.position)) return false;
//            return true;
//        }
//        
//        
//        @Override
//        public boolean equals(Object obj) {
//            if (obj instanceof Item) {
//                return equals((Item)obj);
//            } else if (obj instanceof Position) {
//                return position.equals(obj);
//            }
//            return false;
//        }
    }
    
    private class ItemComparator implements Comparator<Item> {
        @Override
        public int compare(Item o1, Item o2) {
            return o1.priority - o2.priority;
        }
    }
    
    private World world;
    private AbstractMovableObject object;
    
    private PriorityQueue<Item> openList = new PriorityQueue<>(11, new ItemComparator());
    private List<Item> closedList = new LinkedList<Item>();
    
    /**
     * 
     * @param world
     * @param object
     */
    public Pathfinder(World world, AbstractMovableObject object) {
        this.world = world;
        this.object = object;
    }
    
    /**
     * Calculate the distance between source and target position and use this
     * as prediction for the path length
     * 
     * @param source	source position
     * @param target	target position
     * @return
     */
    private static int predictDistance(Position source, Position target) {
        int x = source.getX() - target.getX();
        int y = source.getY() - target.getY();
        int c = (int)Math.ceil(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        return c;
    }
    
    /**
     * 
     * @param start
     * @param end
     * @return
     */
    public List<Position> calculate(Position start, Position end) {
        openList.add(new Item(start, 0));
        do {
        	
            Item current = openList.remove();
            if (current.position.equals(end)) {
                return getPath(current); 
            }
            
            expandNode(current, end);
            
            closedList.add(current);
            if (current.g > 1000) return null;
        } while (!openList.isEmpty());
        return null;
    }

    /**
     * 
     * @param currentItem
     */
    private void expandNode(Item currentItem, Position end) {
        Field currentField = world.getField(currentItem.position);
        for (Field field : world.getNeighbourFields(currentField)) {
            if (!object.canStepOn(field)) {
                continue;
            }
            Position position = field.getPosition();
            
            if (containsPosition(closedList, position)) {
            	continue;
            }
            
            int temp_g = currentItem.g + 1;
            
            if(containsPosition(openList, position) && temp_g >= currentItem.g){
                continue;
            }
            
            int f = temp_g + predictDistance(position, end);
            Item item = new Item(position, f);
            item.predecessor   = currentItem;
            item.g             = temp_g;
            
            Item i = getItem(openList, position);
            if (i != null) {
                openList.remove(i);
                openList.add(item);
            } else {
                openList.add(item);
            }
        }
    }
    
    /**
     * Check whether a collection of items contains a position
     * 
     * @param c
     * @param p
     * @return
     */
    private boolean containsPosition(Collection<Item> c, Position p) {
        return getItem(c, p) != null;
    }
    
    /**
     * Get the item containing the specified position
     * 
     * @param c
     * @param p
     * @return
     */
    private Item getItem(Collection<Item> c, Position p) {
        for (Item i : c) {
            if (i.position.equals(p))
                return i;
        }
        return null;
    }
    
    /**
     * 
     * @param i
     * @return
     */
    private List<Position> getPath(Item i) {
        List<Position> result = new LinkedList<Position>();
        while (i.predecessor != null) {
            result.add(i.position);
            i = i.predecessor;
        }
        return result;
    }
    
}
