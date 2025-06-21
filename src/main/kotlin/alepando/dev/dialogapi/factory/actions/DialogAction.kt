package alepando.dev.dialogapi.factory.actions

import net.minecraft.server.dialog.DialogAction as NMSDialogAction

/**
 * Enum that wraps the native Minecraft [DialogAction] constants
 * for cleaner usage within the API.
 */
enum class DialogAction(val nmsDialogAction: NMSDialogAction) {
    CLOSE(NMSDialogAction.CLOSE),
    NONE(NMSDialogAction.NONE),
    WAIT_FOR_RESPONSE(NMSDialogAction.WAIT_FOR_RESPONSE);
}
