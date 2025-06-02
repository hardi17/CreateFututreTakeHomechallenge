package com.createfuture.takehome.ui.commonui

sealed class Route(
    val name: String
) {
    object  CharacterScreen : Route("CharacterScreen")
    object  CharacterDetailScreen : Route("CharacterDetailScreen")
//    object SubBreedScreen : ScreenItems("SubBreed/{$BREED_NAME}") {
//        fun passArg(breedName: String) = "SubBreed/$breedName"
//    }
}