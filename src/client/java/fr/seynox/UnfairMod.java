package fr.seynox;

import fr.seynox.advantages.Advantage;
import fr.seynox.advantages.Flying;
import fr.seynox.advantages.NoFall;
import fr.seynox.advantages.Teleport;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UnfairMod implements ClientModInitializer {

	public static final String KEYBINDINGS_CATEGORY_NAME = "category.unfairmod.keys";
	public static final String KEYBINDING_NAME_BASE = "key.unfairmod.%s";
	private static final Logger LOGGER = LoggerFactory.getLogger("unfairmod");

	private final List<Advantage> enabledAdvantages = List.of(
		new Flying(), new Teleport(), new NoFall()
	);

	@Override
	public void onInitializeClient() {
		registerAdvantagesKeybindings();
		ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
	}

	private void registerAdvantagesKeybindings() {
		LOGGER.info("Registering keybindings..");
		for(Advantage advantage: enabledAdvantages) {
			KeyBinding keyBinding = advantage.getKeybinding();
			KeyBindingHelper.registerKeyBinding(keyBinding);
		}
	}

	private void onClientTick(MinecraftClient client) {
		ClientPlayerEntity player = client.player;
		if(client.player == null) return;

		for(Advantage advantage: enabledAdvantages) {
			advantage.onClientTick(player);
		}
	}

}