package com.healthnotifications;

import net.runelite.client.config.*;
import net.runelite.client.config.ConfigSection;

import java.awt.*;

@ConfigGroup("health-notifications")
public interface HealthNotificationsConfig extends Config
{
	/* Hitpoint Settings */
	@ConfigSection(
			name = "Hitpoint Settings",
			description = "Hitpoint settings",
			position = 0,
			closedByDefault = false
	)
	String hitpointSettings = "hitpointSettings";

	@ConfigItem(
			keyName = "getHitpointThreshold",
			name = "Hitpoint Threshold",
			description = "Set hitpoint threshold",
			position = 1,
			section = hitpointSettings
	)
	default int getHitpointThreshold() {
		return 1;
	}

	@Alpha
	@ConfigItem(
			keyName = "overlayColor",
			name = "Overlay Color",
			description = "Set the notification overlay color",
			position = 2,
			section = hitpointSettings
	)
	default Color getHitpointOverlayColor() {
		return new Color(1.0f, 0.0f, 0.0f, 0.25f);
	}

	@ConfigItem(
			keyName = "disableOverlay",
			name = "Disable Overlay",
			description = "Disable overlay notifications",
			position = 3,
			section = hitpointSettings
	)
	default boolean disableHitpointOverlay() {
		return false;
	}

	@ConfigItem(
			keyName = "disableNotification",
			name = "Disable Notification",
			description = "Disable tray notifications",
			position = 4,
			section = hitpointSettings
	)
	default boolean disableHitpointNotifications() { return true; }

	/* Prayer Settings */
	@ConfigSection(
			name = "Prayer Settings",
			description = "Prayer settings",
			position = 100,
			closedByDefault = false
	)
	String prayerSettings = "prayerSettings";

	@ConfigItem(
			keyName = "getPrayerThreshold",
			name = "Prayer Threshold",
			description = "Set prayer threshold",
			position = 101,
			section = prayerSettings
	)
	default int getPrayerThreshold() {
		return 1;
	}

	@Alpha
	@ConfigItem(
			keyName = "prayerOverlayColor",
			name = "Overlay Color",
			description = "Set the notification overlay color",
			position = 102,
			section = prayerSettings
	)
	default Color getPrayerOverlayColor() {
		return new Color(0.0f, 0.0f, 1.0f, 0.25f);
	}

	@ConfigItem(
			keyName = "disablePrayerOverlay",
			name = "Disable Overlay",
			description = "Disable overlay notifications",
			position = 103,
			section = prayerSettings
	)
	default boolean disablePrayerOverlay() {
		return false;
	}

	@ConfigItem(
			keyName = "disablePrayerNotification",
			name = "Disable Notification",
			description = "Disable tray notifications",
			position = 104,
			section = prayerSettings
	)
	default boolean disablePrayerNotifications() {
		return true;
	}

	/* Combo Settings */
	@ConfigSection(
			name = "Combo Settings",
			description = "Combo settings",
			position = 200,
			closedByDefault = false
	)
	String comboSettings = "comboSettings";

	@Alpha
	@ConfigItem(
			keyName = "comboOverlayColor",
			name = "Overlay Color",
			description = "Set the notification overlay color",
			position = 201,
			section = comboSettings
	)
	default Color getComboOverlayColor() {
		return new Color(0.0f, 1.0f, 0.0f, 0.25f);
	}

	@ConfigItem(
			keyName = "disableComboOverlay",
			name = "Disable Overlay",
			description = "Disable overlay notifications",
			position = 202,
			section = comboSettings
	)
	default boolean disableComboOverlay() {
		return false;
	}

}
