package listeners

import Main
import com.destroystokyo.paper.entity.villager.Reputation
import com.destroystokyo.paper.entity.villager.ReputationType
import org.bukkit.entity.EntityType
import org.bukkit.entity.Villager
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent

class PlayerInteractEntityEventHandler(private var plugin: Main) : Listener {
  @EventHandler(priority = EventPriority.HIGHEST)
  fun onInteraction(event: PlayerInteractEntityEvent) {
    val entity = event.rightClicked

    if (entity.type == EntityType.VILLAGER) {
      val villager = entity as Villager

      if (plugin.professions.contains(villager.profession)) {
        val highestRep = Reputation()

        villager.reputations.forEach {
          if (it.value.getReputation(ReputationType.MAJOR_POSITIVE) > highestRep.getReputation(ReputationType.MAJOR_POSITIVE)) {
            highestRep.setReputation(
              ReputationType.MAJOR_POSITIVE,
              it.value.getReputation(ReputationType.MAJOR_POSITIVE)
            )
          }

          if (it.value.getReputation(ReputationType.MINOR_POSITIVE) > highestRep.getReputation(ReputationType.MINOR_POSITIVE)) {
            highestRep.setReputation(
              ReputationType.MINOR_POSITIVE,
              it.value.getReputation(ReputationType.MINOR_POSITIVE)
            )
          }
        }

        val currentRep = villager.reputations[event.player.uniqueId]
        highestRep.setReputation(
          ReputationType.MAJOR_NEGATIVE,
          currentRep?.getReputation(ReputationType.MAJOR_NEGATIVE) ?: 0
        )
        highestRep.setReputation(
          ReputationType.MINOR_NEGATIVE,
          currentRep?.getReputation(ReputationType.MINOR_NEGATIVE) ?: 0
        )

        villager.setReputation(event.player.uniqueId, highestRep)
      }
    }

  }
}