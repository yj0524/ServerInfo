package com.yj0524

import io.github.monun.kommand.*
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        getLogger().info("Plugin Enabled")

        loadKommand()

        server.scheduler.runTaskTimer(this, Runnable {
            server.onlinePlayers.forEach { player ->
                player.setPlayerListHeaderFooter("", "Ping : ${player.ping}ms\nServer Memory : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + "MB / " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB")
            }
        }, 0, 20)
    }

    override fun onDisable() {
        getLogger().info("Plugin Disabled")
    }

    fun loadKommand() {
        kommand {
            register("memory") {
                requires {
                    isOp
                }
                executes {
                    sender.sendMessage("§aServer Total Memory : " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB")
                    sender.sendMessage("§aServer Used Memory : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + "MB")
                }
            }
            register("ping") {
                requires {
                    isOp
                }
                executes {
                    sender.sendMessage("§aYour Ping : " + player.ping + "ms")
                }
                then("target" to player()) {
                    executes {
                        val target: Player by it
                        sender.sendMessage("§a${target.name} Ping : " + target.ping + "ms")
                    }
                }
            }
            register("userinfo") {
                requires {
                    isOp
                }
                executes {
                    sender.sendMessage("Nickname : ${player.name}")
                    sender.sendMessage("UUID : ${player.uniqueId}")
                    sender.sendMessage("Ping : ${player.ping}ms")
                }
                then("target" to player()) {
                    executes {
                        val target: Player by it
                        sender.sendMessage("Nickname : ${target.name}")
                        sender.sendMessage("UUID : ${target.uniqueId}")
                        sender.sendMessage("Ping : ${target.ping}ms")
                    }
                }
            }
        }
    }
}
