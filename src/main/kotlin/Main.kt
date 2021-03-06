import listeners.PlayerInteractEntityEventHandler
import org.bukkit.entity.Villager
import org.bukkit.plugin.java.JavaPlugin

class Main() : JavaPlugin() {
  var professions: HashSet<Villager.Profession> =
    hashSetOf(
      Villager.Profession.ARMORER,
      Villager.Profession.BUTCHER,
      Villager.Profession.CARTOGRAPHER,
      Villager.Profession.CLERIC,
      Villager.Profession.FARMER,
      Villager.Profession.FISHERMAN,
      Villager.Profession.FLETCHER,
      Villager.Profession.LEATHERWORKER,
      Villager.Profession.LIBRARIAN,
      Villager.Profession.MASON,
      Villager.Profession.SHEPHERD,
      Villager.Profession.TOOLSMITH,
      Villager.Profession.WEAPONSMITH,
    )

  override fun onEnable() {
    this.server.pluginManager.registerEvents(PlayerInteractEntityEventHandler(this), this)
  }

  override fun onDisable() {
  }
}
