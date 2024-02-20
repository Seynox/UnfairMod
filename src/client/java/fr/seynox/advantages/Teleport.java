package fr.seynox.advantages;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class Teleport implements Advantage {

    private static final float TELEPORT_BLOCK_DISTANCE = 5.0f;

    private final KeyBinding keyBinding = Advantage.createUnregisteredKeyBinding("teleport", GLFW.GLFW_KEY_T);

    @Override
    public KeyBinding getKeybinding() {
        return keyBinding;
    }

    @Override
    public void onClientTick(@NotNull ClientPlayerEntity player) {
        if(keyBinding.wasPressed()) {
            teleportForward(player);
        }
    }

    private void teleportForward(ClientPlayerEntity player) {
        Vec3d rotationVector = player.getRotationVector();
        Vec3d forwardOffset = rotationVector.multiply(TELEPORT_BLOCK_DISTANCE);

        Vec3d position = player.getPos();
        Vec3d newPosition = position.add(forwardOffset);
        player.setPosition(newPosition);
    }
}
