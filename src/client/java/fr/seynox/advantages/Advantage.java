package fr.seynox.advantages;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.jetbrains.annotations.NotNull;

import static fr.seynox.UnfairMod.KEYBINDINGS_CATEGORY_NAME;
import static fr.seynox.UnfairMod.KEYBINDING_NAME_BASE;

public interface Advantage {
    KeyBinding getKeybinding();
    void onClientTick(@NotNull ClientPlayerEntity player);

    static KeyBinding createUnregisteredKeyBinding(String keybindingName, int keyCode) {
        String keybindingTranslationKey = KEYBINDING_NAME_BASE.formatted(keybindingName);
        return new KeyBinding(keybindingTranslationKey, InputUtil.Type.KEYSYM, keyCode, KEYBINDINGS_CATEGORY_NAME);
    }

}
