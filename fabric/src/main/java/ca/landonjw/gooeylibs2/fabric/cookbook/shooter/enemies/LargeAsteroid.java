package ca.landonjw.gooeylibs2.fabric.cookbook.shooter.enemies;

import ca.landonjw.gooeylibs2.fabric.cookbook.shooter.ShooterEnemy;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class LargeAsteroid extends ShooterEnemy
{

    public LargeAsteroid(int col) {
        super(3, col);
    }

    @Override
    public Item getDisplay() {
        return Items.PRISMARINE_SHARD;
    }

}