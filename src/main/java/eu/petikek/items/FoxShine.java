package eu.petikek.items;

import eu.petikek.MeleeCraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.util.math.Box;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FoxShine extends Item {
    
    public static Logger LOGGER = LogManager.getLogger();

    public FoxShine(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        // cooldown handling
        ItemStack stack = playerEntity.getStackInHand(hand);
        if (stack.getCooldown() > 0 || !playerEntity.isInSneakingPose()) {
            return new TypedActionResult<>(ActionResult.FAIL, stack);
        }
        stack.setCooldown(5);

        // play sound only on client for the time being, playing only on the server doesn't play anything and on both play 2 times
        if (world.isClient())
            playerEntity.playSound(MeleeCraft.FOX_SHINE_BLIP, 1.0F, 1.0F);

        if (!world.isClient()) {
            java.util.List<Entity> entityList = world.getEntities(playerEntity, new Box(playerEntity.getPos().getX() - 1, playerEntity.getPos().getY() - 1, playerEntity.getPos().getZ() - 1, playerEntity.getPos().getX() + 1, playerEntity.getPos().getY() + 1, playerEntity.getPos().getZ() + 1));
            for (Entity entity : entityList) {
                entity.damage(DamageSource.player(playerEntity), 1.0F);
                this.customPushAway(playerEntity, entity, 1.3F);
                // Creeper handling
                CreeperEntity creeperEntity = null;
                if (entity instanceof CreeperEntity)
                    creeperEntity = (CreeperEntity)entity;
                if (creeperEntity == null)
                    continue;
                creeperEntity.heal(4.5F);
                creeperEntity.onStruckByLightning(new LightningEntity(EntityType.LIGHTNING_BOLT, world));
                creeperEntity.extinguish();
                //this.log(Level.INFO, entity.getType().toString());
            }
        }
        return new TypedActionResult<>(ActionResult.SUCCESS, stack);
    }

    private void customPushAway(PlayerEntity playerEntity, Entity entity, float multiplier) {
        double vectorX = ((playerEntity.getPos().x - entity.getPos().x) * -1) * multiplier;
        double vectorY = ((playerEntity.getPos().y - entity.getPos().y) * -1) * multiplier;
        double vectorZ = ((playerEntity.getPos().z - entity.getPos().z) * -1) * multiplier;
        if (vectorY > 0)
            vectorY = vectorY * -1;
        entity.addVelocity(vectorX, vectorY, vectorZ);
        this.log(Level.INFO,"X:" + String.valueOf(vectorX));
        this.log(Level.INFO,"Y:" + String.valueOf(vectorY));
        this.log(Level.INFO,"Z:" + String.valueOf(vectorZ));
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "[MeleeCraft] " + message);
    }

}