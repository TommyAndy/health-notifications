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

import java.awt.*;

@Slf4j
@PluginDescriptor(
		name = "Health Notifications",
		description = "Visual Notifications for Health and Prayer",
		tags = {"health", "hitpoints", "hp", "prayer", "notifications"}
)
public class HealthNotificationsPlugin extends Plugin
{
	private boolean shouldNotifyHitpoints = true;
	private boolean shouldNotifyPrayer = true;
	private long lastHitpointNotificationTime = 0L;
	private long lastPrayerNotificationTime = 0L;

	@Inject
	private Client client;

	@Inject
	private HealthNotificationsConfig config;

	@Inject
	private HealthNotificationsOverlay hitpointOverlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private Notifier notifier;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(hitpointOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(hitpointOverlay);
	}

	@Subscribe
	public void onGameTick(GameTick event) {
		if (!isClientReady()) {
			return;
		}

		if (!config.disableHitpointNotifications() && hitpointTotalBelowThreshold()) {
			int hitpointNotifyTime = config.getHitpointNotifyTime();
			if (hitpointNotifyTime == 0) {
				if (shouldNotifyHitpoints) {
					notifier.notify("Your hitpoints are below " + config.getHitpointThreshold());
					shouldNotifyHitpoints = false;
				}
			} else {
				long currentTime = System.currentTimeMillis();
				if (lastHitpointNotificationTime == 0L || currentTime - lastHitpointNotificationTime >= hitpointNotifyTime * 1000L) {
					notifier.notify("Your hitpoints are below " + config.getHitpointThreshold());
					lastHitpointNotificationTime = currentTime;
				}
			}
		}

		if (!config.disablePrayerNotifications() && prayerTotalBelowThreshold()) {
			int prayerNotifyTime = config.getPrayerNotifyTime();
			if (prayerNotifyTime == 0) {
				if (shouldNotifyPrayer) {
					notifier.notify("Your prayer points are below " + config.getPrayerThreshold());
					shouldNotifyPrayer = false;
				}
			} else {
				long currentTime = System.currentTimeMillis();
				if (lastPrayerNotificationTime == 0L || currentTime - lastPrayerNotificationTime >= prayerNotifyTime * 1000L) {
					notifier.notify("Your prayer points are below " + config.getPrayerThreshold());
					lastPrayerNotificationTime = currentTime;
				}
			}
		}

		/* Maintain previous releases behavior around handling prayer notifications */
		if (!hitpointTotalBelowThreshold()) {
			shouldNotifyHitpoints = true;
			shouldNotifyPrayer = true;
		}
	}

	@Provides
	HealthNotificationsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(HealthNotificationsConfig.class);
	}

	public boolean shouldRenderOverlay() {
		if (!isClientReady()) {
			return false;
		}

		/* Catch the combo first */
		if (!config.disableComboOverlay() && hitpointTotalBelowThreshold() && prayerTotalBelowThreshold()) {
			return true;
		}

		/* Render for hitpoints */
		if (!config.disableHitpointOverlay() && hitpointTotalBelowThreshold()) {
			return true;
		}

		/* Render for Prayer */
		if (!config.disablePrayerOverlay() && prayerTotalBelowThreshold()) {
			return true;
		}

		return false;
	}

	public Color getOverlayColor() {
		if (!config.disableComboOverlay() && hitpointTotalBelowThreshold() && prayerTotalBelowThreshold()) {
			return config.getComboOverlayColor();
		}

		/* We'll just default to alerting on Health via the overlay
		 * if combo is off and both are breaching thresholds
		 * Maybe this could be configurable?
		 */
		if (!config.disableHitpointOverlay() && hitpointTotalBelowThreshold()) {
			return config.getHitpointOverlayColor();
		}

		if (!config.disablePrayerOverlay() && prayerTotalBelowThreshold()) {
			return config.getPrayerOverlayColor();
		}

		/* Return transparent overlay if we somehow get here */
		return new Color(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public boolean hitpointTotalBelowThreshold()  {
		return isClientReady() && client.getBoostedSkillLevel(Skill.HITPOINTS) < config.getHitpointThreshold();
	}

	public boolean prayerTotalBelowThreshold()  {
		return isClientReady() && client.getBoostedSkillLevel(Skill.PRAYER) < config.getPrayerThreshold();
	}

	public boolean isClientReady() {
		return client.getGameState() == GameState.LOGGED_IN && client.getLocalPlayer() != null;
	}
}
