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
  @EventHandler(priority = EventPriority.LOWEST)
  fun onInteraction(event: PlayerInteractEntityEvent) {
    val entity = event.rightClicked

    if (entity.type == EntityType.VILLAGER) {
      val villager = entity as Villager

      if (plugin.professions.contains(villager.profession)) {
        val highestRep = Reputation()
        var highestMajorPositive = 0
        var highestMinorPositive = 0

        // Search for the highest values for reputations of types major and minor positive
        villager.reputations.forEach {
          val majorPositive = it.value.getReputation(ReputationType.MAJOR_POSITIVE)
          val minorPositive = it.value.getReputation(ReputationType.MINOR_POSITIVE)

          if (majorPositive > highestMajorPositive) {
            highestMajorPositive = majorPositive
          }

          if (minorPositive > highestMinorPositive) {
            highestMinorPositive = minorPositive
          }
        }

        // Set Reputations values. The positives ones are from the highest reps of the server
        // The negatives ones are from the user that interacted or 0
        // This is to not make villagers always love Players. They still gain negative points.
        highestRep.setReputation(
          ReputationType.MAJOR_POSITIVE,
          highestMajorPositive
        )

        highestRep.setReputation(
          ReputationType.MINOR_POSITIVE,
          highestMinorPositive
        )

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