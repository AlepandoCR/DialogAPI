#  DialogAPI – Custom Dialog API (Minecraft 1.21.6)

**DialogAPI** is a developer-focused API for easily testing and extending Minecraft's new native dialogs via the `ServerboundCustomClickActionPacket`.  
It offers a full Kotlin-based wrapper for creating rich, interactive dialogs with buttons, inputs, and custom actions.

> ❗ **Note:** This API is intended as a dev utility – maintenance is not guaranteed.  
> Feel free to fork, adapt, and build your own features on top of it.

> ⚠️ Please consider using Paper's API (Currently on testing) 


---

> ## Features
>
> -  Kotlin-first builder pattern
> -  Support for all Vanilla dialog types (MultiAction, List, Links, Notice)
> -  Custom actions via Mojang’s native packet system
> -  Input reading (text, number, multiline)
> -  Item & message-based dialog bodies
> -  Easy integration with Bukkit events

---

## How to Use

> ### Adding the API
> ```kotlin 
> repositories {
>   maven("https://jitpack.io")
> }
>
> dependencies {
 >  implementation("com.github.AlepandoCR:DialogAPI:v1.0.5")
> }
> ```

### 🔧 API Initialization

Call this function to initialize the API internals and register the required packet listeners.

This enables features like `CustomActions` and `InputReaders`, which rely on packet-level data.
> ⚠️ If you don't call this, dialogs will still open and render correctly, but input-related features **will not work**.

```kotlin
fun onEnable() {
  DialogApi.initialize(this)
}
```

## Building a Simple Dialog

---

```kotlin
val dialogData = DialogDataBuilder()
    .title(Component.text("Test Menu"))
    .externalTitle(Component.text("Menu Test"))
    .canCloseWithEscape(true)
    .addBody(PlainMessageDialogBody(100, Component.text("Hello from Dialog!")))
    .build() 
   ```
   
### Adding Buttons with Actions

 ```kotlin
val resourceLocation = ResourceLocation(namespace, path) // Custom API's resource location
 val testButton = Button(
    ButtonDataBuilder()
        .label(Component.text("Kill Me"))
        .width(100)
        .build(),
    Optional.of(KeyedAction(resourceLocation))
) 
```
### MultiAction Dialog
 ```kotlin
val dialog = MultiActionDialogBuilder()
    .data(dialogData)
    .columns(1)
    .exitButton(exitButton)
    .addButton(testButton)
    .build() 
   ```
> ### 🪟 Opening a Dialog
> The method to open a dialog depends on whether you're using **Kotlin** or **Java**:
>
> #### Kotlin
> If you're coding in Kotlin, use the extension function:
> ```kotlin
> player.openDialog(dialog)
> ```
>
> #### Java
> If you're using Java, use the utility method provided:
> ```java
> PlayerOpener.INSTANCE.openDialog(player, dialog);
> ```
>
> ℹ Both methods work identically under the hood — use the one that fits your language of choice.


## Creating Custom Actions

---

### Registering an Action
 ```kotlin
val killPlayerNamespace = "dialog"
val killPlayerPath = "damage_player"
val killPlayerKey = ResourceLocation(killPlayerNamespace, killPlayerPath)
try {
  CustomKeyRegistry.register(
    killPlayerKey,
    KillPlayerAction,
    PlayerReturnValueReader
  )
} catch (e: IllegalStateException) {
  player.sendMessage("Note: Kill player key was already registered: ${e.message}")
}
```
###  Action Implementation
 ```kotlin
object KillPlayerAction: CustomAction() {
    override fun task(player: Player, plugin: DialogPlugin) {
        dynamicListener?.start()
        player.damage(5.0)

        object : BukkitRunnable() {
            override fun run() {
                dynamicListener?.stop()
            }
        }.runTaskLater(plugin, 20L)
    }

  // Custom listeners are not required for CustomActions, but its an option 
    override fun listener(): Listener {
        return object : Listener {
            @EventHandler
            fun onPlayerDeath(event: PlayerDeathEvent) {
                event.player.sendMessage("You died during a dialog!")
            }
        }
    }
} 
```
###  Input Readers

```kotlin
object PlayerReturnValueReader : InputReader {
    override fun task(player: Player, value: Any?) {
        player.sendMessage("Received input: $value")
    }
}
```
###  Creating Input Fields (_There are more than shown_)
```kotlin
  val numberRangeInput = NumberRangeInputBuilder()
  .label(Component.text("Input"))
  .key("number_input")
  .width(150)
  .rangeInfo(RangeInfo(1.0f,10.0f))
  .labelFormat("")
  .build()

val stringInput = TextInputBuilder()
  .label(Component.text("Input"))
  .width(256)
  .key("text_test")
  .initial("")
  .labelVisible(true)
  .maxLength(300)
  .multiline(MultilineOptions(10,20))
  .build()
```


* Extending the API
  * You can add more actions, readers, and even UI components by following the builder and registry patterns shown above.

- Contributions
  * Pull requests are welcome. 
  * Feel free to adapt the system or use it as the API for your own dialog-based plugin!
