package models

import org.jetbrains.compose.resources.DrawableResource

data class Module(
    val key: String,
    val name: String,
    val logo: DrawableResource,
    val tech: ModuleTech = ModuleTech.KMP,
)

enum class ModuleTech {
    KMP,
    FLUTTER,
}