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
    None
}
