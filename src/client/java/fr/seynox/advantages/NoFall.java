package fr.seynox.advantages;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class NoFall implements Advantage {

    private static boolean enabled = false;

    private final KeyBinding keyBinding = Advantage.createUnregisteredKeyBinding("nofall", GLFW.GLFW_KEY_N);

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
            toggleNoFall(player);
        }
    }

    private void toggleNoFall(ClientPlayerEntity player) {
        setEnabled(!enabled);
        String message = "NoFall %s".formatted(enabled ? "enabled" : "disabled");
        player.sendMessage(Text.literal(message), true);
    }

}
