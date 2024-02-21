package fr.seynox.advantages;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class VisualSneak implements Advantage {

    private static boolean enabled = false;

    private final KeyBinding keyBinding = Advantage.createUnregisteredKeyBinding("visual_sneak", GLFW.GLFW_KEY_V);

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
    }

    @Override
    public KeyBinding getKeybinding() {
        return keyBinding;
    }

    @Override
    public void onClientTick(@NotNull ClientPlayerEntity player) {
        if(keyBinding.wasPressed()) {
            toggleVisualSneak(player);
        }
    }

    private void toggleVisualSneak(ClientPlayerEntity player) {
        setEnabled(!enabled);
        String message = "VisualSneak %s".formatted(enabled ? "enabled" : "disabled");
        player.sendMessage(Text.literal(message), true);
    }

}
