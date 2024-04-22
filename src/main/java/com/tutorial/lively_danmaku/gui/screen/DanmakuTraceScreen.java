package com.tutorial.lively_danmaku.gui.screen;

import com.tutorial.lively_danmaku.gui.menu.DanmakuTraceMenu;
import com.tutorial.lively_danmaku.gui.widget.ParticleEntry;
import com.tutorial.lively_danmaku.gui.widget.ParticleListWidget;
import com.tutorial.lively_danmaku.network.DanmakuNetwork;
import com.tutorial.lively_danmaku.network.ParticleTypePacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fml.loading.StringUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DanmakuTraceScreen extends AbstractSelectionScreen<DanmakuTraceMenu> {
    private final ResourceLocation DANMAKU_TRACE = new ResourceLocation("lively_danmaku", "textures/gui/fumo_table.png");
    private ParticleEntry selected = null;
    private ParticleListWidget particleListWidget;
    private List<ResourceKey<ParticleType<?>>> particles;
    private final List<ResourceKey<ParticleType<?>>> unsortedParticles;
    public int listWidth = 125;
    public DanmakuTraceScreen(DanmakuTraceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.particles = getParticle();
        this.unsortedParticles = particles;
    }

    @Override
    public void init()
    {
        super.init();
        int y = this.height - 20 - PADDING;
        this.particleListWidget = new ParticleListWidget(this, listWidth, fullButtonHeight, y - PADDING - getFontRenderer().lineHeight - PADDING);
        this.particleListWidget.setLeftPos(6);
        this.particleListWidget.setRenderTopAndBottom(false);
        this.addRenderableWidget(particleListWidget);
    }

    @Override
    protected void importEvent() {
        if (this.minecraft != null && this.minecraft.player != null && this.minecraft.gameMode != null && this.selected != null) {
            DanmakuNetwork.CHANNEL.sendToServer(new ParticleTypePacket(this.selected.getParticleType().location().toString()));
        }
    }

    @Override
    public void tick() {
        super.tick();
        search.tick();
        particleListWidget.setSelected(selected);
        if (!search.getValue().equals(lastFilterText))
        {
            particleListWidget.refreshList();
            reloadParticle();
        }
    }

    private void reloadParticle() {
        this.particles = unsortedParticles.stream()
                .filter(particleInfo -> StringUtils.toLowerCase(particleInfo.location().getPath()).contains(StringUtils.toLowerCase(search.getValue())))
                .toList();
        lastFilterText = search.getValue();
    }

    public ParticleListWidget getParticleListWidget(){
        return this.particleListWidget;
    }

    public void setSelected(ParticleEntry entry) {
        this.selected = entry;
    }

    public <T extends ObjectSelectionList.Entry<T>> void buildImageList(Consumer<T> modListViewConsumer, Function<ResourceKey<ParticleType<?>>, T> newEntry)
    {
        particles.forEach(image->modListViewConsumer.accept(newEntry.apply(image)));
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(DANMAKU_TRACE, i , j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @SuppressWarnings("deprecation")
    private List<ResourceKey<ParticleType<?>>> getParticle() {
        HolderLookup.RegistryLookup<ParticleType<?>> particle = BuiltInRegistries.PARTICLE_TYPE.asLookup();
        return particle.listElementIds().collect(Collectors.toList());
    }
}
