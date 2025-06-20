package alepando.dev.dialogapi.util

import io.papermc.paper.adventure.PaperAdventure
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextDecoration
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component as NMSComponent
import net.minecraft.network.chat.Style as NMSStyle

object ComponentTranslator {

    fun toNMS(component: Component): NMSComponent {
        return PaperAdventure.asVanilla(component)
    }
}
