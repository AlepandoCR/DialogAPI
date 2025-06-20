package alepando.dev.dialogapi.body

import alepando.dev.dialogapi.factory.Wrapper

/**
 * Represents the body of a dialog.
 * @param T The type of the dialog body.
 * @property width The width of the dialog body.
 */
abstract class DialogBody<T>(val width: Int): Wrapper<T>