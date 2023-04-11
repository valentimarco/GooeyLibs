package ca.landonjw.gooeylibs2.fabric.cookbook.shooter.enemies;

import ca.landonjw.gooeylibs2.fabric.cookbook.shooter.ShooterEnemy;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class SmallAsteroid extends ShooterEnemy
{

    public SmallAsteroid(int col) {
        super(1, col);
    }

    @Override
    public Item getDisplay() {
        return Items.CLAY_BALL;
    }

}