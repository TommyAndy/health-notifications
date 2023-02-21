package com.healthnotifications;

import net.runelite.client.ui.overlay.Overlay;

import javax.inject.Inject;
import java.awt.*;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayLayer;

public class HealthNotificationsOverlay extends Overlay {
    private final Client client;
    private final HealthNotificationsPlugin plugin;
    private final HealthNotificationsConfig config;

    @Inject
    private HealthNotificationsOverlay(Client client, HealthNotificationsPlugin plugin, HealthNotificationsConfig config) {
        super(plugin);
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }
    @Override
    public Dimension render(Graphics2D graphics) {
        if (plugin.shouldRenderOverlay()) {
            Color color = graphics.getColor();
            graphics.setColor(config.overlayColor());
            graphics.fill(new Rectangle(client.getCanvas().getSize()));
            graphics.setColor(color);
        }
        return null;
    }
}
