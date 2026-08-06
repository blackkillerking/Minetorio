package net.blackkillerking.minetorio.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class EntityLootModifier extends LootModifier {
    public static final Codec<EntityLootModifier> CODEC = RecordCodecBuilder.create(inst ->
            codecStart(inst).and(ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(m -> m.item))
                    .and(Codec.INT.fieldOf("min").forGetter(m -> m.min))
                    .and(Codec.INT.fieldOf("max").forGetter(m -> m.max))
                    .apply(inst, EntityLootModifier::new));

    private final Item item;
    private final int min;
    private final int max;
    public EntityLootModifier(LootItemCondition[] conditionsIn, Item item, int min, int max) {
        super(conditionsIn);
        this.item = item;
        this.min = min;
        this.max = max;
    }
    @NotNull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (entity instanceof Cow) {
            generatedLoot.add(new ItemStack(item));
            generatedLoot.removeIf(stack -> stack.is(Items.LEATHER));

            RandomSource random = context.getRandom();
            int count = min + random.nextInt(max - min + 1);
            generatedLoot.add(new ItemStack(item, count));
        }
        return generatedLoot;
    }
    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
