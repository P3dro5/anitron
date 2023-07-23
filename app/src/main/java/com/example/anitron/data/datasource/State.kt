package com.example.anitron.data.datasource

enum class State {
    Success,
    Failed,
    Loading,
    Searched
}
enum class SearchWidgetState {
    OPENED,
    CLOSED
}

enum class CategoryEntry {
    PopularMovies,
    PopularTvShows,
    OnTheatres,
    UpcomingMovies,
    ShowsCurrentlyAiring,
    SearchedMovies,
    SearchedTvShows,
    SearchedPeople,
    None
}

enum class BottomNavigationState{
    Home,
    Profile,
    About
}

enum class ProfileState{
    Edit,
    Default
}
