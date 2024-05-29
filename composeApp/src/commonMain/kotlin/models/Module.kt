package models

data class Module(val key: String, val name: String, val imageUrl: String, val tech: ModuleTech = ModuleTech.KMP)

enum class ModuleTech {
    KMP,
    FLUTTER,
}