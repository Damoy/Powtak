package core.entities.items;

import core.entities.Entity;
import core.entities.player.Player;
import core.world.Tile;
import rendering.Texture;

public abstract class Item extends Entity{

	public Item(Tile tile, int x, int y, Texture texture) {
		super(tile, x, y, texture);
	}
	
	public abstract void interact(Player player);

}
