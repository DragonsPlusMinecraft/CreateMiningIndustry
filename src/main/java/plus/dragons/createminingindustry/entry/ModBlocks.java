package plus.dragons.createminingindustry.entry;

import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.foundation.data.CreateRegistrate;
import plus.dragons.createminingindustry.MiningIndustry;


public class ModBlocks {

    private static final CreateRegistrate REGISTRATE = MiningIndustry.registrate()
            .creativeModeTab(() -> Create.BASE_CREATIVE_TAB).startSection(AllSections.KINETICS);

    /*public static final BlockEntry<DisenchanterBlock> DISENCHANTER = REGISTRATE
            .block("disenchanter", DisenchanterBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(ModTags.pickaxeOnly())
            .blockstate((ctx, pov) -> pov.simpleBlock(ctx.get(), AssetLookup.standardModel(ctx, pov)))
            .simpleItem()
            .register();

    public static final BlockEntry<CopierBlock> COPIER = REGISTRATE
            .block("copier", CopierBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(ModTags.pickaxeOnly())
            .blockstate((ctx, pov) -> pov.simpleBlock(ctx.get(), AssetLookup.partialBaseModel(ctx, pov)))
            .item(AssemblyOperatorBlockItem::new)
            .model(AssetLookup::customItemModel)
            .build()
            .register();*/
    
    public static void register() {
    }
}
