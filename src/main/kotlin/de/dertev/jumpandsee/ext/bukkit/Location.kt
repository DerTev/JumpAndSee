package de.dertev.jumpandsee.ext.bukkit

import org.bukkit.Location

fun Location.isEqualsTo(other: Location) = x.toInt() == other.x.toInt() && y.toInt() == other.y.toInt() && z.toInt() == other.z.toInt()

fun Location.isEqualsToIgnoreY(other: Location) = x.toInt() == other.x.toInt() && z.toInt() == other.z.toInt()

fun Location.generateRandomBlockInJumpDistance(): Location = Location(world, x + (-2..2).drop(3).random(), y + 1, z + (-2..2).drop(3).random())