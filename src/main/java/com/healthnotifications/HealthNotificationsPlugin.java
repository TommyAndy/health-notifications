package com.healthnotifications;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Skill;

import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.Notifier;

@Slf4j
@PluginDescriptor(
		name = "Health Notifications",
		description = "Visual Notifications for Health",
		tags = {"health", "hitpoints", "hp", "notifications"}
)
public class HealthNotificationsPlugin extends Plugin
{
	private boolean shouldNotify = true;

	@Inject
	private Client client;

	@Inject
	private HealthNotificationsConfig config;

	@Inject
	private HealthNotificationsOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private Notifier notifier;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onGameTick(GameTick event) {
		if (!isClientReady() || config.disableNotifications()) {
			return;
		}

		if (shouldNotify && hitpointTotalBelowThreshold()) {
			notifier.notify("Your hitpoints are below " + config.getHitpointThreshold());
			shouldNotify = false;
		} else if (!hitpointTotalBelowThreshold()) {
			shouldNotify = true;
		}
	}

	@Provides
	HealthNotificationsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(HealthNotificationsConfig.class);
	}

	public boolean shouldRenderOverlay() {
		return isClientReady() && !config.disableOverlay() && hitpointTotalBelowThreshold();
	}

	public boolean hitpointTotalBelowThreshold()  {
		return isClientReady() && client.getBoostedSkillLevel(Skill.HITPOINTS) < config.getHitpointThreshold();
	}

	public boolean isClientReady() {
		return client.getGameState() == GameState.LOGGED_IN && client.getLocalPlayer() != null;
	}
}
