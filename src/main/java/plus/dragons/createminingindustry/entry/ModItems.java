package plus.dragons.createminingindustry.entry;

import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.foundation.data.CreateRegistrate;
import plus.dragons.createminingindustry.MiningIndustry;
import plus.dragons.createminingindustry.event.FillCreateItemGroupEvent;

public class ModItems {

    private static final CreateRegistrate REGISTRATE = MiningIndustry.registrate()
            .creativeModeTab(() -> Create.BASE_CREATIVE_TAB).startSection(AllSections.KINETICS);

    /*public static final ItemEntry<EnchantingGuideItem> ENCHANTING_GUIDE = REGISTRATE.item("enchanting_guide", EnchantingGuideItem::new)
            .properties(prop -> prop.stacksTo(1))
            .register();

    public static final ItemEntry<HyperExpBottleItem> HYPER_EXP_BOTTLE = REGISTRATE.item("bottled_hyper_experience", HyperExpBottleItem::new)
            .lang("Bottle O' Hyper Enchanting")
            .register();*/

    public static void fillCreateItemGroup(FillCreateItemGroupEvent event) {
        if (event.getItemGroup() == Create.BASE_CREATIVE_TAB) {
            /*event.addInsertion(AllBlocks.ITEM_DRAIN.get(), ModBlocks.DISENCHANTER.asStack());
            event.addInsertion(AllBlocks.SPOUT.get(), ModBlocks.COPIER.asStack());
            event.addInsertion(AllBlocks.BLAZE_BURNER.get(), ENCHANTING_GUIDE.asStack());
            event.addInsertion(AllFluids.CHOCOLATE.get().getBucket(), ModFluids.INK.get().getBucket().getDefaultInstance());
            event.addInsertion(ModFluids.INK.get().getBucket(), HYPER_EXP_BOTTLE.asStack());*/
        }
    }
    
    public static void register() {
    }
}
