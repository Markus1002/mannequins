package me.jaackson.mannequins.bridge;

import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Item;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Jackson
 */
public final class RegistryBridge {

    @ExpectPlatform
    public static <E extends Entity, T extends EntityType.Builder<E>> Supplier<EntityType<E>> registerEntity(String name, Supplier<T> object) {
        return Platform.safeAssertionError();
    }
    @ExpectPlatform
    public static <T extends SoundEvent> Supplier<T> registerSound(String name, Supplier<T> object) {
        return Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> object) {
        return Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static <T extends LivingEntity> void registerEntityAttributes(Supplier<EntityType<T>> type, Supplier<AttributeSupplier.Builder> builder) {
        Platform.safeAssertionError();
    }

    @ExpectPlatform
    @Environment(EnvType.CLIENT)
    public static <T extends Entity> void registerEntityRenderer(EntityType<T> entityType, Function<EntityRenderDispatcher, EntityRenderer<T>> factory) {
        Platform.safeAssertionError();
    }

    @ExpectPlatform
    @Environment(EnvType.CLIENT)
    public static void registerSprite(ResourceLocation sprite, ResourceLocation atlas) {
        Platform.safeAssertionError();
    }
}
