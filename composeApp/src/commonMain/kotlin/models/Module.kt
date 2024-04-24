package models

data class Module(val name: String, val tech: ModuleTech = ModuleTech.KMP)

enum class ModuleTech {
    KMP,
    FLUTTER,
}