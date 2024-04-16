package com.tutorial.lively_danmaku.item.tooltip;

import com.tutorial.lively_danmaku.util.ColorPoint;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.util.ArrayList;

public record RecordDanmakuTooltip(ArrayList<ColorPoint> pointArrayList) implements TooltipComponent {
}
