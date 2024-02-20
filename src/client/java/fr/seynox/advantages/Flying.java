package fr.seynox.advantages;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class Flying implements Advantage {

    private static final float FLYING_SPEED = 2.0f;
    private static final int MAX_FLYING_TICKS_BEFORE_BYPASS = 40;

    private final KeyBinding keyBinding = Advantage.createUnregisteredKeyBinding("flying", GLFW.GLFW_KEY_C);
    private boolean isFlying = false;
    private int flyingTicks = 0;

    @Override
    public KeyBinding getKeybinding() {
        return keyBinding;
    }

    @Override
    public void onClientTick(@NotNull ClientPlayerEntity player) {
        if(keyBinding.wasPressed()) {
            toggleFly(player);
        }

        if(isFlying) {
            onFlyingClientTick(player);
        }
    }

    private void toggleFly(ClientPlayerEntity player) {
        isFlying = !isFlying;
        player.setNoGravity(isFlying);

        String message = "Flying %s".formatted(isFlying ? "enabled" : "disabled");
        player.sendMessage(Text.literal(message), true);
    }

    private void onFlyingClientTick(ClientPlayerEntity player) {
        Vec3d newVelocity = getFlyingVelocity(player);

        // Kick bypass
        if(flyingTicks == MAX_FLYING_TICKS_BEFORE_BYPASS) {
            newVelocity = bypassFlyingCheck(newVelocity);
            flyingTicks = 0;
        } else {
            flyingTicks++;
        }

        player.setVelocity(newVelocity);
    }

    private Vec3d getFlyingVelocity(ClientPlayerEntity player) {
        Vec2f playerRotation = player.getRotationClient();
        double pitch = playerRotation.x;
        double yaw = playerRotation.y;

        double horizontalAngle = Math.toRadians(-yaw);
        double verticalAngle = Math.toRadians(-pitch);

        Vec3d inputDirection = getInputDirection()
                .rotateX((float) verticalAngle)
                .rotateY((float) horizontalAngle);

        return inputDirection.multiply(FLYING_SPEED);
    }

    private Vec3d getInputDirection() {
        int x = 0;
        int y = 0;
        int z = 0;

        GameOptions options = MinecraftClient.getInstance().options;

        if(options.forwardKey.isPressed()) z++; // Reversed for some reason
        if(options.backKey.isPressed()) z--;

        if(options.rightKey.isPressed()) x--; // Reversed for some reason
        if(options.leftKey.isPressed()) x++;

        if(options.jumpKey.isPressed()) y++;
        if(options.sneakKey.isPressed()) y--;

        return new Vec3d(x, y, z).normalize();
    }

    /**
     * Used to bypass server vanilla flying detection
     * @param velocity The current velocity
     * @return The modified velocity
     */
    private Vec3d bypassFlyingCheck(Vec3d velocity) {
        return new Vec3d(velocity.x, -0.4, velocity.z);
    }
}
