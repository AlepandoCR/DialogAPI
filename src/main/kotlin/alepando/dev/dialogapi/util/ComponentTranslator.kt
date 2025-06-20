package alepando.dev.dialogapi.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextDecoration
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component as NMSComponent
import net.minecraft.network.chat.Style as NMSStyle

object ComponentTranslator {

    fun toNMS(component: Component): NMSComponent {
        val base = when (component) {
            is TextComponent -> NMSComponent.literal(component.content())
            else -> NMSComponent.literal("N/D")
        }

        base.style = convertStyle(component)

        component.children().forEach {
            base.append(toNMS(it))
        }

        return base
    }

    private fun convertStyle(component: Component): NMSStyle {
        val style = component.style()
        var nmsStyle = NMSStyle.EMPTY


        style.color()?.let { color ->
            nmsStyle = nmsStyle.withColor(ChatFormatting.getById(color.asHexString().removePrefix("#").toInt(16)))
        }

        if (style.hasDecoration(TextDecoration.BOLD)) nmsStyle = nmsStyle.withBold(true)
        if (style.hasDecoration(TextDecoration.ITALIC)) nmsStyle = nmsStyle.withItalic(true)
        if (style.hasDecoration(TextDecoration.UNDERLINED)) nmsStyle = nmsStyle.withUnderlined(true)
        if (style.hasDecoration(TextDecoration.STRIKETHROUGH)) nmsStyle = nmsStyle.withStrikethrough(true)
        if (style.hasDecoration(TextDecoration.OBFUSCATED)) nmsStyle = nmsStyle.withObfuscated(true)

        return nmsStyle
    }
}
