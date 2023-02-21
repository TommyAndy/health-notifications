package com.healthnotifications;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("health-notifications")
public interface HealthNotificationsConfig extends Config
{
	@ConfigItem(
			keyName = "getHitpointThreshold",
			name = "Hitpoint Threshold",
			description = "Set hitpoint threshold",
			position = 0
	)
	default int getHitpointThreshold() {
		return 0;
	}

	@ConfigItem(
			keyName = "overlayColor",
			name = "Overlay Color",
			description = "Set the notification overlay color",
			position = 1
	)
	default Color overlayColor() {
		return new Color(1.0f, 0.0f, 0.0f, 0.5f);
	}

	@ConfigItem(
			keyName = "disableOverlay",
			name = "Disable Overlay",
			description = "Disable overlay notifications",
			position = 2
	)
	default boolean disableOverlay() {
		return false;
	}

	@ConfigItem(
			keyName = "disableNotification",
			name = "Disable Notification",
			description = "Disable tray notifications",
			position = 3
	)
	default boolean disableNotifications() {
		return true;
	}
}
