package ca.landonjw.gooeylibs2.mixins;

import ca.landonjw.gooeylibs2.api.container.GooeyContainer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public abstract class InventoryMixin {

    @Shadow @Final
    public Player player;

    @Shadow public abstract int getFreeSlot();

    @Inject(method = "add(ILnet/minecraft/world/item/ItemStack;)Z", at = @At("RETURN"))
    public void sync(int target, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        try {
            if (player instanceof ServerPlayer serverPlayer) {
                if (player.containerMenu instanceof GooeyContainer gooey) {
                    gooey.refresh();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
