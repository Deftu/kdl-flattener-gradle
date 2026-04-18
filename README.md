# KDL Flattener for Gradle

<sup>Commissioned by asoji</sup>

<sub>Inspired by https://github.com/FallingColors/PKPCPBP</sub>

Simple Gradle plugin which automatically converts `*.flatten.kdl` resource files into JSON files at build time.
Particularly useful for things like language or registry files.

---

[![Discord Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/social/discord-singular_64h.png)](https://s.deftu.dev/discord)
[![Ko-Fi Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/donate/kofi-singular_64h.png)](https://s.deftu.dev/kofi)

---

## Features

* **Zero Configuration:** Just apply the plugin. It automatically hooks into Gradle's `processResources` tasks.
* **Seamless Integration:** Any file ending in `.flatten.kdl` in your resources directories will be processed and renamed to `.json` in the final JAR.
* **Fast & Native:** Implemented directly as a `FilterReader`, leveraging Gradle's built-in file copying and caching mechanisms without creating messy intermediate build directories.
* **Powered by KDL:** Uses the official `kdl-org/kdl4j` parser, fully supporting KDL v2 syntax.

---

## Installation

Add the plugin to your `build.gradle.kts` (or `build.gradle`):

```kotlin
plugins {
    id("dev.deftu.kdlflattener") version "1.0.0" // Replace with actual version
}
```

That's it! As long as you have the standard `java` or `kotlin` plugins applied, the flattener is ready to go.

## Usage & Syntax

Place your localization or registry files in your standard resources folder (e.g., `src/main/resources/lang/`) and name them with the `.flatten.kdl` extension.

The plugin reads the KDL node tree and recursively joins nested node names with a . (dot) to create standard JSON key-value pairs.

### Input: `en_us.flatten.kdl`

When a node has a string argument, it is immediately assigned as the value for the current key. When it has a child block, the current key is passed down as a prefix.

```kdl
item {
    hexcasting {
        scroll_small "Small Scroll" {
            of "How did you get this item of %s"
            empty "Empty Small Scroll"
        }
    }
}
```

### Output: `en_us.json`

```json
{
  "item.hexcasting.scroll_small": "Small Scroll",
  "item.hexcasting.scroll_small.of": "How did you get this item of %s",
  "item.hexcasting.scroll_small.empty": "Empty Small Scroll"
}
```

## How it works under the hood

The plugin doesn't use a standalone Gradle Task. Instead, it finds every `ProcessResources` task in your project and registers a custom `FilterReader`. As Gradle copies your resource files from your source folders into the build directory, it streams `.flatten.kdl` files through the parser, writes the JSON out, and strips the `.flatten.kdl` extension in favor of `.json`.

---

[![BisectHosting](https://www.bisecthosting.com/partners/custom-banners/8fb6621b-811a-473b-9087-c8c42b50e74c.png)](https://bisecthosting.com/deftu)

---

**This project is licensed under [LGPL-3.0][lgpl3].**\
**&copy; 2026 Deftu**

[lgpl3]: https://www.gnu.org/licenses/lgpl-3.0.en.html
