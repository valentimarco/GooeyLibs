package ca.landonjw.gooeylibs2.fabric.cookbook.shooter.enemies;

import ca.landonjw.gooeylibs2.fabric.cookbook.shooter.ShooterEnemy;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class MediumAsteroid extends ShooterEnemy
{

    public MediumAsteroid(int col) {
        super(2, col);
    }

    @Override
    public Item getDisplay() {
        return Items.COAL;
    }

}