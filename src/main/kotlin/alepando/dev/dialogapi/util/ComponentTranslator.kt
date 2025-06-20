package alepando.dev.dialogapi.util

import io.papermc.paper.adventure.PaperAdventure
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextDecoration
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component as NMSComponent
import net.minecraft.network.chat.Style as NMSStyle

/**
 * Utility object for translating Adventure [Component] instances to NMS (Net Minecraft Server) [NMSComponent] instances.
 */
object ComponentTranslator {

    /**
     * Converts an Adventure [Component] to its NMS equivalent using Paper's Adventure library.
     *
     * @param component The Adventure [Component] to convert.
     * @return The corresponding NMS [NMSComponent].
     */
    fun toNMS(component: Component): NMSComponent {
        return PaperAdventure.asVanilla(component)
    }
}
