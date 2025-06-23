# DialogAPI – Custom Dialog API for Minecraft 1.21+

[![GitHub release (latest by date)](https://img.shields.io/github/v/release/AlepandoCR/DialogAPI)](https://github.com/AlepandoCR/DialogAPI/releases/latest)
[![JitPack](https://jitpack.io/v/AlepandoCR/DialogAPI.svg)](https://jitpack.io/#AlepandoCR/DialogAPI)

**DialogAPI** is a developer-focused API for easily testing and extending Minecraft's new native dialogs (1.21.6), specifically leveraging the `ServerboundCustomClickActionPacket`. It offers a full Kotlin-based wrapper for creating rich, interactive dialogs with buttons, inputs, and custom actions for Paper plugins.

> ❗ **Note:** This API is primarily intended as a developer utility. While functional, ongoing maintenance for future Minecraft versions is not strictly guaranteed. Feel free to fork, adapt, and build your own features on top of it.

> ⚠️ **Important Consideration:** PaperMC is developing its own official Dialog API. While DialogAPI is useful now, developers should keep an eye on Paper's native solution for long-term projects, as it will likely become the standard.

---

##  Table of Contents

- [ Features](#-features)
- [ How to Use](#-how-to-use)
    - [ Adding the API](#-adding-the-api)
    - [ API Initialization](#-api-initialization)
- [🛠 Building a Simple Dialog](#-building-a-simple-dialog)
    - [ DialogData](#-dialog-data)
    - [ Adding Buttons with Actions](#-adding-buttons-with-actions)
    - [ Creating the Dialog](#-creating-the-dialog)
    - [ Opening a Dialog](#-opening-a-dialog)
- [ Creating Custom Actions](#creating-custom-actions)
    - [ Registering an Action](#registering-an-action)
    - [ Action Implementation](#-action-implementation)
    - [ Input Readers](#-input-readers)
    - [ Creating Input Fields](#-creating-input-fields)
- [ Looking for More Examples?](#-looking-for-more-examples)
- [ Extending the API](#-extending-the-api)
- [ Contributing](#-contributing)
- [ Troubleshooting / FAQ](#-troubleshooting--faq)
- [ License](#-license)

---

## ✨ Features

-    Kotlin-first builder pattern for intuitive dialog creation.
-    Supports all Vanilla dialog types: `MultiAction`, `List`, `Links`, `Notice`.
-    Custom actions via Mojang’s native `ServerboundCustomClickActionPacket` system.
-    Robust input reading: text, numbers (including ranges), multiline text, boolean toggles, and single-choice options.
-    Flexible dialog bodies: plain text messages and item displays.
-    Easy integration with Paper events and plugin lifecycle.

---

## 🚀 How to Use

### 📦 Adding the API

To add DialogAPI to your project, include the following in your `build.gradle.kts` (or equivalent for your build system like Maven or Gradle Groovy):

```kotlin
repositories {
    maven("https://jitpack.io") 
}

dependencies {
    implementation("com.github.AlepandoCR:DialogAPI:v1.1.1") 
}
```
> 💡 **Tip:** Always check [JitPack](https://jitpack.io/#AlepandoCR/DialogAPI) or the [GitHub Releases page](https://github.com/AlepandoCR/DialogAPI/releases) for the latest version number.

### 🔧 API Initialization

Call this function in your plugin's `onEnable` method to initialize the API internals and register the required packet listeners. This enables features like `CustomActions` and `InputReaders`, which rely on packet-level data.

> ⚠️ **Crucial Step:** If you don't call `DialogApi.initialize(this)`, dialogs will still open and render correctly, but essential features like button actions (`CustomAction`) and input field processing (`InputReader`) **will not function**.

```kotlin
// In your main plugin class
override fun onEnable() {
    // Initialize DialogAPI, passing your plugin instance
    DialogApi.initialize(this) 
    
    // Your other onEnable logic...
    logger.info("MyPlugin has been enabled and DialogAPI is initialized!")
}
```

---

## 🛠️ Building a Simple Dialog

This section guides you through creating a basic dialog with a title, body, and a button.

### 📝 Dialog Data

First, define the core data for your dialog using `DialogDataBuilder`:

```kotlin
val dialogData = DialogDataBuilder()
    .title(Component.text("Test Menu")) // The title displayed at the top of the dialog window
    .externalTitle(Component.text("Menu Test")) // Title shown in server logs when the dialog is opened (useful for debugging)
    .canCloseWithEscape(true) // Allows the player to close the dialog using the ESC key
    .afterAction(DialogAction.CLOSE) // Action after dialog closes or button is clicked (default: CLOSE, can be KEEP_OPEN)
    .addBody(
        PlainMessageDialogBody( // A simple text body part
            100, // Height of this body part
            Component.text("Hello from DialogAPI! This is a basic dialog example.")
        )
    )
    // You can add more body parts, like ItemDialogBody, if needed
    .build()
```

> 💡 **New in DialogDataBuilder:**
> *   `.afterAction(DialogAction.KEEP_OPEN)`: Instructs the dialog to attempt to remain open after a button's `CustomAction` is executed. The default is `DialogAction.CLOSE`. This is useful for dialogs that update themselves or have multiple steps.

---
### 🖼️ Dialog Body Types

Dialogs can contain different types of body content. You use `DialogDataBuilder().addBody(...)` to add them.

#### Plain Message Body

This is a simple text body, already shown in previous examples.

```kotlin
.addBody(PlainMessageDialogBody(100, Component.text("Your message here.")))
```

#### Item Display Body (`ItemDialogBody`)

You can display a Minecraft item within your dialog, complete with its name, lore, enchantments, and an optional description.

```kotlin
// --- Example: Using ItemDialogBody ---
// First, prepare your ItemStack
val diamondSword = ItemStack(Material.DIAMOND_SWORD)

// Now, create the ItemDialogBody
val itemBody = ItemDialogBodyBuilder()
    .width(200) // Width allocated for this body part in the dialog layout
    .height(100) // Height allocated for this body part
    .item(diamondSword) // The ItemStack to display
    .showDecorations(true) // Shows enchant glint, etc. (default: true)
    .showTooltip(true) // Shows item name, lore, enchants on hover (default: true)
    .description("This is a special item found deep within the Obsidian Caves. It is said to possess immense power.", 180) // Optional text description displayed near the item, and width for this description.
    .build()

// Add it to your DialogData
val itemShowcaseData = DialogDataBuilder()
    .title(Component.text("Item Showcase"))
    .addBody(itemBody) // Add the ItemDialogBody
    .addBody(PlainMessageDialogBody(50, Component.text("What will you do with it?"))) // You can mix body types
    // ... add buttons or other inputs ...
    .build()

// Then, use this DialogData with a dialog builder (e.g., MultiActionDialogBuilder)
val keyedAction = KeyedAction(ResourceLocation("key", "close_dialog_action")) // You can also add an optional DataContainer
val exitButton = Button(ButtonDataBuilder().label(Component.text("Close")).width(80).build(), Optional.of(keyedAction))
val itemShowcaseDialog = MultiActionDialogBuilder()
    .data(itemShowcaseData)
    .addButton(exitButton)
    .columns(1)
    .build()

player.openDialog(itemShowcaseDialog)
```

---

### 🔘 Adding Buttons with Actions

Next, create buttons and associate actions with them. Actions are triggered when a button is clicked. `KeyedAction` links a button to a registered `CustomAction`.

```kotlin
// Define a custom action key (namespace and path)
val customActionNamespace = "key" // Can be your plugin's name or whatever key 
val customActionPath = "custom_action"
val resourceLocation = ResourceLocation(customActionNamespace, customActionPath)

// Create a button with an associated action
val testButton = Button(
    ButtonDataBuilder()
        .label(Component.text("Click Me!"))
        .width(100) // Width of the button
        .build(),
    Optional.of(KeyedAction(resourceLocation)) // Associates the button with the custom action
)
```
> ℹ️ **Note:** For this button to work, you'll need to register a `CustomAction` with the `resourceLocation` defined above. See the [ Registering an Action](#️-registering-an-action) section for details.

### 🧱 Creating the Dialog

Now, assemble the dialog using a specific dialog type builder (e.g., `MultiActionDialogBuilder` for dialogs with multiple buttons).

```kotlin
// Assuming 'exitButton' is defined elsewhere (e.g., a button to close the dialog)
// val exitButton = ...

val dialog = MultiActionDialogBuilder() // Use the appropriate builder for your dialog type
    .data(dialogData) // Set the core dialog data
    .columns(1) // Number of columns for button layout
    .exitButton(exitButton) // Optional: A dedicated exit button
    .addButton(testButton) // Add your custom button
    .build()
```
> 💡 **Tip:** DialogAPI supports various dialog types like `ListDialog`, `LinksDialog`, and `NoticeDialog`. Choose the builder that best fits your needs.

### 🪟 Opening a Dialog

The method to open a dialog depends on whether you're using **Kotlin** or **Java**:

#### Kotlin

If you're coding in Kotlin, use the provided extension function:

```kotlin
player.openDialog(dialog)
```

#### Java

If you're using Java, use the utility method:

```java
PlayerOpener.INSTANCE.openDialog(player, dialog);
```

> ℹ️ Both methods work identically under the hood — use the one that fits your language of choice.

---

### More Dialog Types

DialogAPI offers several specialized dialog types beyond the flexible `MultiActionDialog`.

#### Notice Dialog

A `NoticeDialog` is a simple dialog used to display a message with a single acknowledgment button.

```kotlin
// --- Example: Creating a Notice Dialog ---
val noticeData = DialogDataBuilder()
    .title(Component.text("Important Notice!"))
    .addBody(PlainMessageDialogBody(100, Component.text("Server restarting in 5 minutes.")))
    .build()

val noticeOkButton = Button(
    ButtonDataBuilder().label(Component.text("OK")).width(80).build(),
    Optional.of(KeyedAction(ResourceLocation("key", "action_path")))
)

val noticeDialog = NoticeDialogBuilder()
    .data(noticeData)
    .button(noticeOkButton)
    .build()

player.openDialog(noticeDialog)
```

#### Confirmation Dialog

A `ConfirmationDialog` presents the user with a binary choice, typically "Yes" and "No" buttons, for confirming an action.

```kotlin
// --- Example: Creating a Confirmation Dialog ---
val confirmData = DialogDataBuilder()
    .title(Component.text("Confirm Purchase"))
    .addBody(PlainMessageDialogBody(120, Component.text("Are you sure you want to buy the Legendary Sword for 1000 Gems?")))
    .externalTitle(Component.text("Player Purchase Confirmation"))
    .build()


val yesButton = Button(
    ButtonDataBuilder().label(Component.text("Yes, Buy It!")).width(100).build(),
    Optional.of(KeyedAction(ResourceLocation("key", "confirm_action_path")))
)
val noButton = Button(
    ButtonDataBuilder().label(Component.text("No, Cancel")).width(100).build(),
    Optional.of(KeyedAction(ResourceLocation("key", "cancel_action_path")))
)

val confirmationDialog = ConfirmationDialogBuilder()
    .data(confirmData)
    .yesButton(yesButton) 
    .noButton(noButton)
    .build()

player.openDialog(confirmationDialog)
```

---
### Advanced Dialog Types

DialogAPI also supports more specialized dialog types for advanced use cases:

*   **`LinksDialog`**:
    *   *Usage for this dialog type is more advanced and may require careful understanding of the API and the original NMS classes.*

*   **`ListDialog`**:
    *   Allows you to display a list of *other dialogs* as selectable options. Clicking an option will open the corresponding sub-dialog.
    *   This is useful for creating nested menu structures.
    *   *This is an advanced feature requiring you to provide instances of API's `Dialog`.*

---

## Creating Custom Actions

Custom actions are the heart of interactive dialogs. They allow you to execute specific server-side logic when a player interacts with a dialog element (e.g., clicks a button).

### Registering an Action

First, you need to register your custom action with a unique `ResourceLocation` key. This key is crucial as it links the client-side dialog interaction (like a button click) to your server-side `CustomAction` implementation.

```kotlin
val killPlayerNamespace = "dialog" // Example namespace
val killPlayerPath = "damage_player" // Example path
val killPlayerKey = ResourceLocation(killPlayerNamespace, killPlayerPath)

try {
    CustomKeyRegistry.register(
        killPlayerKey,        // The unique key for this action
        KillPlayerAction,     // Your CustomAction implementation (see below)
        PlayerReturnValueReader // Your InputReader implementation, you can also register an action without the Reader, although you might not be able to register the reader to the same key later on
    )
} catch (e: IllegalStateException) {
    // Handle cases where the key might already be registered
    // This message is good for debugging during development
    player.sendMessage("Note: Kill player key was already registered, perhaps by another part of your plugin or a different plugin: ${e.message}")
}
```
> 💡 **Best Practice:** Register all your custom keys during your plugin's `onEnable` phase to ensure they are available when needed and to handle any registration conflicts early.

### ⚙️ Action Implementation

Create a class (or object for singletons) that extends `CustomAction` and implement the `task` method. This method contains the server-side logic that will be executed when the action is triggered.

```kotlin
object KillPlayerAction : CustomAction() {
    override fun task(player: Player, plugin: Plugin) {
        // Optional: Start a dynamic listener if needed for this action
        dynamicListener?.start()
        // Optional: Stop the dynamic listener after a delay or when the action is complete
        dynamicListener?.stopListenerAfter(20L) // Time is based ticks
        
        player.damage(5.0) // Example action: damage the player
    }

    // Optional: Define a Bukkit event listener specific to this action
    // Custom listeners are not required for CustomActions, but it's an option.
    // Listener also includes the player that triggered the action 
    override fun listener(dialogPlayer: Player): Listener {
        return object : Listener {
            @EventHandler
            fun onPlayerDeath(event: PlayerDeathEvent) {
                if (event.player == dialogPlayer) {
                    dialogPlayer.sendMessage("you died during your dialog")
                }
            }
        }
    }
}
```
> ℹ️ The `plugin: Plugin` parameter in `task` refers to your main plugin instance, allowing you to access plugin-specific resources or schedulers, this plugin instance is the same as the one you use to initialize DialogAPI

### 📥 Input Readers

Input readers (`InputReader`) are essential when your dialog includes input fields (like text boxes, number inputs, etc.). They are responsible for processing the data submitted by the player from these fields. Each `CustomAction` that handles a dialog submission with inputs can have an associated `InputReader`.

```kotlin
object PlayerReturnValueReader : InputReader {
    // InputValueList offers a getter based on keys.
    // Useful for getting specific values with the key set on Input creation (see below).
    override fun task(player: Player, values: InputValueList) {
        for (input in values.list) {
            player.sendMessage("Received input - Key: ${input.key}, Value: ${input.value}") // Corrected to show input.value
        }
    }
}
```

### ⌨️ Creating Input Fields

DialogAPI provides builders for various input field types, allowing you to collect different kinds of data from players. Each input field should be given a unique `key` (String) so you can identify its value in the `InputReader`.

#### Number Range Input (`NumberRangeInputBuilder`)

```kotlin
val numberRangeInput = NumberRangeInputBuilder()
    .label(Component.text("Enter a Number (1-10)"))
    .key("number_input") // Unique key for this input field
    .width(150)
    .rangeInfo(RangeInfo(1.0f, 10.0f)) // Define min and max values
    .labelFormat("") // Optional: Custom format for the label
    .build()
```

#### Text Input (`TextInputBuilder`)

```kotlin
val stringInput = TextInputBuilder()
    .label(Component.text("Enter Feedback (max 300 chars)"))
    .width(256) // Width of the input field
    .key("feedback_text") // Unique key for this input
    .initial("It was great!") // Optional: Pre-fills the input field
    .labelVisible(true) // Whether the label is shown above the input
    .maxLength(300) // Maximum allowed characters
    // For multiline: MultilineOptions(visibleLines, maxCharactersPerLineForWrapping)
    .multiline(MultilineOptions(5, 40)) // Makes it a multiline input with 5 visible lines, wrapping at 40 chars
    .build() 
// Note: .multiline() must be called with a valid MultilineOptions object before .build() for TextInput.
```

#### Number Range Input (`NumberRangeInputBuilder`)

```kotlin
val numberRangeInput = NumberRangeInputBuilder()
    .label(Component.text("Enter a Number (1.0-10.0, step 0.5)"))
    .key("quantity_input") // Unique key for this input field
    .width(150)
    // labelFormat allows specifying how the number is displayed, using printf-style formatters.
    .labelFormat("Selected: %.1f items") // Example: "Selected: 5.0 items"
    // RangeInfo(min, max, step, initialValue)
    .rangeInfo(RangeInfo(1.0f, 10.0f, 0.5f, 2.5f)) // Define min, max, step, and initial values
    .build()
// Note: .rangeInfo() must be called with a valid RangeInfo object before .build() for NumberRangeInput.
```

#### Single Option Input (`SingleOptionInputBuilder` - Dropdown/Radio style)

```kotlin
val singleOptionInput = SingleOptionInputBuilder()
    .label(Component.text("Choose Your Class"))
    .key("player_class_choice")
    .width(150) // Affects how the options are displayed if they are too long
    .labelVisible(true)
    .addEntry( // Add each choice as an entry
        EntryBuilder()
            //ℹ️ There should always be an initial entry
            .initial(true) // Sets this as the default selected option (only one 'initial(true)' allowed per input) 
            .id("warrior_class") // This ID string is the actual value returned when this option is selected
            .display(Component.text("Warrior 💪").color(NamedTextColor.RED)) // Text displayed to the player
            .build()
    )
    .addEntry(
        EntryBuilder() // 'initial' is false by default
            .id("mage_class") // This ID string is the value, the value InputReader will read from the player_class_choice key
            .display(Component.text("Mage 🧙").color(NamedTextColor.BLUE))
            .build()
    )
    .addEntry(
        EntryBuilder()
            .id("archer_class")
            .display(Component.text("Archer 🏹").color(NamedTextColor.GREEN))
            .build()
    )
    .build()
```
> ℹ️ **Important for `SingleOptionInput`:** The `id` string you provide in `EntryBuilder().id("your_id_here")` is the value that your `InputReader` will receive for this `SingleOptionInput` field when that particular option is chosen by the player.

#### Boolean Input (`BooleanInputBuilder` - Checkbox/Toggle style)

```kotlin
val booleanInput = BooleanInputBuilder()
    .label(Component.text("Enable Super Powers?"))
    .key("boolean_super_powers") // Unique key
    .initial(false) // Initial state: false (unchecked), true (checked)
    .build()
```
> ℹ️ **Key Reminder:** To retrieve values from these input fields, you must:
> 1. Assign a unique `key` to each input field during its creation.
> 2. Ensure the `CustomAction` that handles the dialog submission is registered with an `InputReader` (like `PlayerReturnValueReader` shown previously).
> 3. In your `InputReader`'s `task` method, use `values.getValue("your_input_key")` or iterate through `values.list` to access the submitted data using these keys.

---

## 📚 Looking for **MORE** examples?

Check out the official companion project: [**DialogApiTest**](https://github.com/AlepandoCR/DialogApiTest) 🧪.
It’s a fully functional sample plugin that demonstrates how to use DialogAPI in a real Paper server environment (compatible with the Minecraft versions mentioned at the top). It includes practical examples of:

-   Custom dialogs with buttons
-   Action registration
-   Input reading
-   Kotlin-based builder syntax

Perfect for learning, testing, or kickstarting your own dialog plugin! You can clone the repository and run it on your test server.

---

## 🧩 Extending the API

DialogAPI is designed with extensibility in mind. You can enhance its capabilities by:

-   **Creating More `CustomAction` Implementations:** Define new actions to handle various interactive elements.
-   **Developing Specialized `InputReader`s:** If you have complex input processing logic, create readers tailored to specific data structures.
-   **Adding New UI Component Builders:** While more advanced, you could theoretically extend the system to support new types of UI elements if Minecraft's protocol allows. This would involve understanding the underlying packet structures.
-   **Forking and Adapting:** Feel free to fork the repository to add significant features or tailor the API to very specific needs.

The core builder and registry patterns (e.g., `CustomKeyRegistry`) are your main tools for adding functionality.

---

## 🤝 Contributing

 Contributors are welcome! If you'd like to help improve DialogAPI, please follow these steps:

1.  **🍴 Fork the Repository**: Click the 'Fork' button at the top right of the GitHub page.
2.  **💻 Clone Your Fork**: `git clone https://github.com/YourUsername/DialogAPI.git` (Replace `YourUsername`)
3.  **🌿 Create a Branch**: `git checkout -b feature/YourAmazingFeature` or `fix/IssueBeingFixed`. Descriptive branch names are helpful!
4.  **✍️ Make Your Changes**: Implement your feature, bug fix, or documentation improvement.
    *   Adhere to the existing code style (primarily Kotlin conventions).
    *   Add KDoc comments for new public APIs or clarify existing ones.
    *   If adding new functionality, consider if unit tests are applicable.
5.  **🧪 Test Thoroughly**:
    *   Run the API in a development server environment (Paper/Spigot) with the relevant Minecraft version(s).
    *   Test with various dialog configurations, especially if your changes affect core functionality.
    *   Ensure no new issues or console errors are introduced.
6.  **💾 Commit Your Changes**: Use clear and descriptive commit messages. We recommend following the [Conventional Commits](https://www.conventionalcommits.org/) specification.
    *   Example: `git commit -m "feat: Add support for ItemDialogBody tooltips"`
    *   Example: `git commit -m "fix: Correctly handle null inputs in PlayerReturnValueReader"`
7.  **📤 Push to Your Branch**: `git push origin feature/YourAmazingFeature`.
8.  **📬 Open a Pull Request (PR)**:
    *   Navigate to the original [DialogAPI repository on GitHub](https://github.com/AlepandoCR/DialogAPI).
    *   Click on "Pull requests" and then "New pull request".
    *   Choose your fork and the branch containing your changes to compare with the main repository's `master` or `main` branch.
    *   Provide a clear title for your PR (e.g., "Feature: Add Item Tooltips" or "Fix: Null Pointer in Input Handling").
    *   In the PR description, detail the changes you made, the reasoning behind them, and any specific testing instructions. Link to any relevant issues.

Your contributions, big or small, are highly appreciated and help make DialogAPI better for everyone!

---

## ❓ Troubleshooting / FAQ

**Q: My dialog opens, but buttons with `CustomAction` or input fields don't work. What's wrong?**
A: ⚠️ The most common reason is forgetting to call `DialogApi.initialize(this)` in your plugin's `onEnable()` method. This step is **crucial** for registering the necessary packet listeners that handle custom actions and input data. Without it, the API won't process clicks or input submissions from the client.

**Q: I'm getting an `IllegalStateException: Key [your_key] is already registered` when trying to register a `CustomKey`.**
A: This error means the `ResourceLocation` (the key, composed of a namespace and path, e.g., `yourplugin:some_action`) you're trying to use for your `CustomAction` is already in use. This could be by another part of your plugin, or rarely, a different plugin (if namespaces aren't unique).
*   **Solution:** Ensure your `ResourceLocation` is unique.
*   **Namespace:** Use your plugin's unique ID (e.g., `myplugin`).
*   **Path:** Use a descriptive name for the action (e.g., `open_main_menu`, `submit_player_report`).
*   You can defensively check if a key is registered before attempting to register it, or wrap the registration call in a try-catch block to handle this specific exception gracefully (perhaps logging a warning).

**Q: How do I choose a good `ResourceLocation` for my custom actions?**
A: A `ResourceLocation` has two string parts: `namespace` and `path`.
*   **Namespace:** This should ideally be your plugin's unique ID (e.g., `mycoolplugin`, `superutils`). This helps prevent conflicts with other plugins. Avoid generic namespaces like `minecraft`, `dialog`, or `custom`.
*   **Path:** This should be a descriptive name for the action, typically using `snake_case` (e.g., `open_shop_menu`, `confirm_teleport_request`, `process_player_settings_form`).
*   Example: `ResourceLocation("myplugin", "open_level_selector")`

**Q: Is DialogAPI compatible with Java-based plugins?**
A: ✅ Yes, absolutely! DialogAPI is written in Kotlin but is designed to be fully interoperable with Java. You can use all its builders, classes, and methods from your Java plugin code without any issues. The README provides Java examples for crucial parts like opening dialogs, and the Kotlin builder syntax (method chaining) translates very directly and naturally to Java.

**Q: Where can I find the most up-to-date version number for the dependency?**
A: 🔗 The best places to check for the latest version are:
*   The [JitPack page for DialogAPI](https://jitpack.io/#AlepandoCR/DialogAPI) (shows all available versions).
*   The [GitHub Releases page](https://github.com/AlepandoCR/DialogAPI/releases) for official tagged releases.
*   The "📦 Adding the API" section in this README should also reflect the latest stable/recommended version.

**Q: My input fields (text, number, etc.) are not returning the values I expect in my `InputReader`. What should I check?**
A: 🕵️‍♀️ Double-check these common points:
1.  **Unique Input Keys:** Ensure that each input field within your dialog was created with a **unique `key` string** (e.g., `TextInputBuilder().key("player_name_input")`, `NumberRangeInputBuilder().key("item_quantity")`). This `key` is how you identify the input's value.
2.  **Correct `InputReader` Registration:** Verify that the `CustomAction` responsible for handling the dialog submission is correctly registered with the appropriate `InputReader` instance in the `CustomKeyRegistry.register()` call.
3.  **Accessing Values in Reader:** In your `InputReader`'s `task(player: Player, values: InputValueList)` method, make sure you are using the correct key to retrieve the value: `values.getValue("your_exact_input_key")`. You can also iterate through `values.list` and check `input.key` and `input.value` for debugging.
4.  **Data Types:** Ensure the type of value you're expecting matches what the input field provides (e.g., a `NumberRangeInput` will provide a number, a `BooleanInput` a boolean).

---

## 📜 License

This project is distributed under the terms of the **MIT License**.

You can find the full license text in the `LICENSE` file in the repository, but here's a summary:

```
MIT License

Copyright (c) [Year] [Your Name/Organization - AlepandoCR for DialogAPI]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

This means you are free to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, provided that the original copyright notice and this permission notice are included in all copies or substantial portions of the Software. The software is provided "as is", without warranty of any kind.
