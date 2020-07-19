package eu.petikek;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.petikek.items.FoxShine;
import eu.petikek.items.FalcoShine;

public class MeleeCraft implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "meleecraft";
    public static final String MOD_NAME = "MeleeCraft";

    public static final ItemGroup MELEECRAFT_GROUP = FabricItemGroupBuilder.build(
		new Identifier("meleecraft", "general"),
		() -> new ItemStack(MeleeCraft.FOX_SHINE));

    public static final FoxShine FOX_SHINE = new FoxShine(new Item.Settings().group(MeleeCraft.MELEECRAFT_GROUP).maxDamage(100));
    public static final FalcoShine FALCO_SHINE = new FalcoShine(new Item.Settings().group(MeleeCraft.MELEECRAFT_GROUP).maxDamage(100));
    public static final Item SHINE_PART_1 = new Item(new Item.Settings().group(MeleeCraft.MELEECRAFT_GROUP));
    public static final Item SHINE_PART_2 = new Item(new Item.Settings().group(MeleeCraft.MELEECRAFT_GROUP));
    public static final Item SHINE_PART_3 = new Item(new Item.Settings().group(MeleeCraft.MELEECRAFT_GROUP));

    public static final Identifier FOX_SHINE_BLIP_ID = new Identifier("meleecraft:fox_shine_blip");
    public static SoundEvent FOX_SHINE_BLIP = new SoundEvent(FOX_SHINE_BLIP_ID);

    public static final Identifier FALCO_SHINE_BLIP_ID = new Identifier("meleecraft:falco_shine_blip");
    public static SoundEvent FALCO_SHINE_BLIP = new SoundEvent(FALCO_SHINE_BLIP_ID);

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");

        log(Level.INFO, "Registering Items");
        Registry.register(Registry.ITEM, new Identifier("meleecraft", "shine_part_1"), SHINE_PART_1);
        Registry.register(Registry.ITEM, new Identifier("meleecraft", "shine_part_2"), SHINE_PART_2);
        Registry.register(Registry.ITEM, new Identifier("meleecraft", "shine_part_3"), SHINE_PART_3);
        Registry.register(Registry.ITEM, new Identifier("meleecraft", "fox_shine"), FOX_SHINE);
        Registry.register(Registry.ITEM, new Identifier("meleecraft", "falco_shine"), FALCO_SHINE);

        log(Level.INFO, "Registering Sounds");
        Registry.register(Registry.SOUND_EVENT, MeleeCraft.FOX_SHINE_BLIP_ID, FOX_SHINE_BLIP);
        Registry.register(Registry.SOUND_EVENT, MeleeCraft.FALCO_SHINE_BLIP_ID, FALCO_SHINE_BLIP);
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}