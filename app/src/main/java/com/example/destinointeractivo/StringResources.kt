package com.example.destinointeractivo

// Mapeo dinámico de strings por idioma
val stringMap = mapOf(
    "es" to mapOf<Int, String>(
        // Botones del menú principal
        R.string.btn_continuar to "Continuar",
        R.string.btn_nueva_partida to "Nueva Partida",
        R.string.btn_ajustes to "Ajustes",

        // Pantalla de Ajustes
        R.string.ajustes_idioma to "Idioma",
        R.string.ajustes_vibracion to "Vibración",
        R.string.ajustes_volumen_efectos to "Volumen efectos",
        R.string.ajustes_volumen_musica to "Volumen música",
        R.string.btn_volver_al_menu to "Volver al Menú",
        R.string.ajustes_salir to "Salir al menú",
        R.string.ajustes_idioma_espanol to "Español",
        R.string.ajustes_idioma_ingles to "Inglés",

        // Pantalla de Combate
        R.string.combate_atacar to "Atacar",
        R.string.combate_defender to "Defender",
        R.string.combate_pocion to "Poción",
        R.string.combate_001 to "Te encuentras frente a un goblin que bloquea tu camino; aunque no parece muy peligroso, no debes confiarte. ¿Qué decides hacer?",
        R.string.combate_002 to "Te cruzas con un sombrío individuo cuyo hacha parece tener sed se sangre; su presencia oscura te pone los pelos de punta. ¿Cómo decides actuar?",
        R.string.combate_003 to "Te enfrentas cara a cara con un poderoso dragón cuya sola respiración hace temblar el suelo. Su mirada desafía tu valentía. ¿Qué haces",
        R.string.combate_004 to "Un enemigo imponente y lleno de misterio aparece ante ti, claramente preparado para tu llegada. Su presencia es una amenaza real a tu seguridad. ¿Qué piensas hacer?",
        R.string.combate_005 to "Un ser oscuro y sobrenatural, surgido de un reino prohibido, se interpone entre tú y tu destino final. Este es el desafío definitivo. ¿Cómo piensas actuar?",
        R.string.combate_reiniciar to "Volver a empezar",

        // Mensajes de Victoria/Derrota
        R.string.victoria_mensaje to "¡Has vencido! Ahora puedes tomar nuevos caminos y probar otras opciones. ¿Te atreves a volver a comenzar?",
        R.string.derrota_mensaje to "¡Derrotado! Tu aventura termina aquí. Todo se reiniciará pero así puedes intentarlo de nuevo.",

        // Recompensas de Combate (Victoria 001)
        R.string.victoria_001_message to "¡Victoria aplastante! El torpe goblin ha caído. Reclama ahora tu merecida recompensa:",
        R.string.victoria_001_damage to "+1 Daño",
        R.string.victoria_001_shield to "+1 Escudo",
        R.string.victoria_001_potion to "+1 Poción",
        R.string.victoria_001_heal to "+15 Cura Poción",
        R.string.victoria_002_message to "El encapuchado ha sido derrotado. Su hacha yace inerte. ¡Recoge tus recompensas y sigue adelante!",
        R.string.victoria_002_damage to "+2 Daño",
        R.string.victoria_002_shield to "+1 Escudo",
        R.string.victoria_002_potion to "+1 Poción",
        R.string.victoria_002_heal to "+15 Cura Poción",
        R.string.victoria_003_message to "¡El majestuoso dragón ha sido vencido! Sus escamas ahora yacen en el suelo. Elige una recompensa por tu valentía:",
        R.string.victoria_003_damage to "+1 Daño",
        R.string.victoria_003_shield to "+1 Escudo",
        R.string.victoria_003_potion to "+1 Poción",
        R.string.victoria_003_heal to "+15 Cura Poción",
        R.string.victoria_004_message to "Has derrotado a un poderoso adversario. ¡Felicidades! Este era el desafío final, y tu victoria te abre el camino. Elige tu recompensa:",
        R.string.victoria_004_damage to "+2 Daño",
        R.string.victoria_004_shield to "+1 Escudo",
        R.string.victoria_004_potion to "+1 Poción",
        R.string.victoria_004_heal to "+15 Cura Poción",

        // Pantalla de Selección de Arma
        R.string.weapon_selection_message to "Bienvenido, aventurero. Elige el arma con la que deseas comenzar tu aventura:",
        R.string.weapon_mandoble to "Mandoble:\n+2 Daño",
        R.string.weapon_espada_escudo to "Espada y escudo:\n+1 Daño, +1 Escudo",
        R.string.weapon_escudo_pesado to "Escudo pesado:\n+2 Escudo"
    ),

    "en" to mapOf<Int, String>(
        // Main Menu Buttons
        R.string.btn_continuar to "Continue",
        R.string.btn_nueva_partida to "New Game",
        R.string.btn_ajustes to "Settings",

        // Settings Screen
        R.string.ajustes_idioma to "Language",
        R.string.ajustes_vibracion to "Vibration",
        R.string.ajustes_volumen_efectos to "Effects volume",
        R.string.ajustes_volumen_musica to "Music volume",
        R.string.btn_volver_al_menu to "Back to Menu",
        R.string.ajustes_salir to "Exit to menu",
        R.string.ajustes_idioma_espanol to "Spanish",
        R.string.ajustes_idioma_ingles to "English",

        // Combat Screen
        R.string.combate_atacar to "Attack",
        R.string.combate_defender to "Defend",
        R.string.combate_pocion to "Potion",
        R.string.combate_001 to "You find yourself face to face with a goblin blocking your path; although it doesn\'t seem very dangerous, you shouldn\'t let your guard down. What do you decide to do?",
        R.string.combate_002 to "You come across a dark figure whose axe seems thirsty for blood; their ominous presence gives you chills. How do you choose to act?",
        R.string.combate_003 to "You stand face to face with a mighty dragon whose very breath shakes the ground beneath you. Its piercing gaze challenges your courage. What do you do?",
        R.string.combate_004 to "A towering and mysterious enemy appears before you, clearly ready for your arrival. Their presence is a real threat to your safety. What do you plan to do?",
        R.string.combate_005 to "A dark and supernatural being risen from a forbidden realm stands between you and your final destiny. This is the ultimate challenge. How do you intend to act?",
        R.string.combate_reiniciar to "Try again",

        // Victory/Defeat Messages
        R.string.victoria_mensaje to "You win! Now you can take new paths and try other options. Do you dare to start over?",
        R.string.derrota_mensaje to "Defeated! Your adventure ends here. Everything will reset, but this way you can try again.",

        // Combat Rewards (Victoria 001)
        R.string.victoria_001_message to "Crushing victory! The clumsy goblin has fallen. Claim your well-deserved reward now:",
        R.string.victoria_001_damage to "+1 Damage",
        R.string.victoria_001_shield to "+1 Shield",
        R.string.victoria_001_potion to "+1 Potion",
        R.string.victoria_001_heal to "+15 Potion Heal",
        R.string.victoria_002_message to "The hooded figure has been defeated. Their axe lies inert. Collect your rewards and move on!",
        R.string.victoria_002_damage to "+2 Damage",
        R.string.victoria_002_shield to "+1 Shield",
        R.string.victoria_002_potion to "+1 Potion",
        R.string.victoria_002_heal to "+15 Potion Heal",
        R.string.victoria_003_message to "The majestic dragon has been vanquished! Its scales now lie upon the ground. Choose a reward for your bravery:",
        R.string.victoria_003_damage to "+1 Damage",
        R.string.victoria_003_shield to "+1 Shield",
        R.string.victoria_003_potion to "+1 Potion",
        R.string.victoria_003_heal to "+15 Potion Heal",
        R.string.victoria_004_message to "You have defeated a powerful adversary! Congratulations! This was the final challenge, and your victory opens the way forward. Choose your reward:",
        R.string.victoria_004_damage to "+2 Damage",
        R.string.victoria_004_shield to "+1 Shield",
        R.string.victoria_004_potion to "+1 Potion",
        R.string.victoria_004_heal to "+15 Potion Heal",

        // Weapon Selection Screen
        R.string.weapon_selection_message to "Welcome, adventurer. Choose the weapon with which you wish to begin your journey:",
        R.string.weapon_mandoble to "Greatsword:\n+2 Damage",
        R.string.weapon_espada_escudo to "Sword and shield:\n+1 Damage, +1 Shield",
        R.string.weapon_escudo_pesado to "Heavy shield:\n+2 Shield"
    )
)