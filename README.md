# Core
## Installation
### Maven
```
<project>
  <repositories>
    <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io/</url>
    </repository>

    <repository>
      <id>papermc</id>
      <url>https://repo.papermc.io/repository/maven-public/</url>
    </repository>
  </repositories>

  <dependencies>
    <dependency>
      <groupId>com.github.hhappa0-code</groupId>
      <artifactId>Core</artifactId>
      <version>v1.0.0</version>
    </dependency>

    <dependency>
      <groupId>io.papermc.paper</groupId>
      <artifactId>paper-api</artifactId>
      <version>1.21.10-R0.1-SNAPSHOT</version>
      <scope>provided</scope>
    </dependency>
  </dependencies>
</project>
```
### Gradle (Groovy)
```
repositories {
  maven { url = 'https://jitpack.io/' }
  maven { url = 'https://repo.papermc.io/repository/maven-public/' }
}

dependencies {
  compileOnly 'com.github.hhappa0-code:Core:v1.0.0'
  compileOnly 'io.papermc.paper:paper-api:1.21.10-R0.1-SNAPSHOT'
}
```
### Gradle (Kotlin)
```
repositories {
  maven { url = uri("https://jitpack.io/") }
  maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
  compileOnly("com.github.hhappa0-code:Core:v1.0.0")
  compileOnly("io.papermc.paper:paper-api:1.21.10-R0.1-SNAPSHOT")
}
```
## Usage
### CorePlugin
**TestPlugin.java**
```
package core.test;

import me.hhappa0.core.plugin.CorePlugin;

public class TestPlugin extends CorePlugin {

  public TestPlugin() {
    super("core.test.commands", "core.test.listeners", "You do not have permission to execute this command!");
  }

  @Override
  public void startup() {
    // plugin startup logic
  }

  @Override
  public void shutdown() {
    // plugin shutdown logic
  }
}
```
Replace "core.test.commands" and "core.test.listeners" with your commands/listeners package, so the core auto-registers these. If you put in `null` core does not automatically register commands/listeners.
### Commands
**TestCommand.java**
```
package core.test.commands;

import me.hhappa0.core.command.BaseCommand;
import me.hhappa0.core.command.CommandInfo;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.util.List;

@CommandInfo(name = "test", permission = "core.test")
public class TestCommand extends BaseCommand {

  @Override
  public void execute(CommandSender sender, String[] args) {
    sender.sendMessage(Component.text("Test!"));
  }

  @Override
  public List<String> tab(CommandSender sender, String[] args) {
    return List.of();
  }
}
```
Commands have to be created in the commands package specified in the main class, in our case `TestPlugin.java`.
In the mandatory `@CommandInfo` annotation, you can leave out the `permission = "core.test"` if you would like the command to be executable by any player. If a permission is specified, before the logic in `execute(CommandSender, String[])` gets executed, a permission check has already happened. The no-permission message is specified in the main class, in our case `TestPlugin.java`. This message will be formatted with MiniMessage. Commands will get auto-registered.
### Listeners
Listeners have to be created in the listeners package specified in the main class, in our case `TestPlugin.java`. For them, just use the default Bukkit Listener. They will get auto-registered.
### SchedulerUtil
You can use the integrated core scheduler for scheduling synchronous and asynchronous tasks. Here is an example usage:
```
SchedulerUtil scheduler = new SchedulerUtil(TestPlugin.getInstance());

scheduler.runTaskAsync(() -> {
  // task logic here
});
```
This runs an asynchronous task once.
### Config
You can use the integrated core config. You can save defaults for more than one config with any filenames. Here is an example:
```
Config config = new Config(TestPlugin.getInstance(), "config.yml");
// Setting the boolean to true
config.set("test", true);
config.save(); // saves config
// Getting the boolean (which is true)
boolean test = config.getBoolean("test");
```
If you create a file with the name `config.yml` in the `resources` folder, the config automatically saves the defaults of the config if the values do not exist. You can do this with any filename. It just has to match with the one specified in the code, when the config was created.
### Menu
You can create your own menus with the integrated core `AbstractMenu`. Here is an example:
```
package core.test.menus;

import me.hhappa0.core.menu.AbstractMenu;
import me.hhappa0.core.menu.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TestMenu extends AbstractMenu {
    
  public TestMenu(Player player) {
    super(player);
  }

  @Override
  public String getMenuTitle() {
    return "Test Title";
  }

  @Override
  public int getRows() {
    return 3;
  }

  @Override
  public void setMenuItems() {
    setItem(new ItemBuilder(Material.DIRT).build(), 0);
    setItem(new ItemBuilder(Material.GOLD_BLOCK).build(), 1, (event -> {
      // click logic here
    }));
  }

  @Override
  public void handleMenuClose() {
    // logic executed when menu closes
  }
}
```
This is a simple menu that does nothing when the dirt block is clicked, but executes the click logic if the gold block gets clicked. Below is an example on how a player opens the menu:
```
new TestMenu(player).open();
```
This opens the menu for the player.

## Conclusion
Now you know about a few of the features in the core libaray. There are even more features like the `ItemBuilder` and the `MathUtil`, but those are self explanatory. If you still have any questions, DM me on Discord by clicking [here](https://discord.com/users/1189492690522476586).

**Thank you for using my Core library!**
