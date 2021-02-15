package edu.rosehulman.schultc2.randommoviepicker

data class TrailerData (
    var kind: String,
    var etag: String,
    var nextPageToken: String,
    var regionCode: String,
    var pageInfo: PageInfo,
    var items: Array<TrailerItem>,
        ) {
    data class PageInfo(
        var totalResults: Long,
        var resultsPerPage: Int
    )
    data class TrailerItem(
        var kind: String,
        var etag: String,
        var id: TrailerID,
        var snippet: Snippet
    ){
        data class TrailerID(
            var kind: String,
            var videoId: String
        )
        data class Snippet(
            var publishedAt: String,
            var channelId: String,
            var title: String,
            var description: String,
            var thumbnails: TrailerThumbnails,
            var channelTitle: String,
            var liveBroadcastContent: String,
            var publishTime: String
        ){
            data class TrailerThumbnails(
                var default: TrailerThumbnail,
                var medium: TrailerThumbnail,
                var high: TrailerThumbnail
            ){
                data class TrailerThumbnail(
                    var url: String,
                    var width: Int,
                    var height: Int
                )
            }

        }
    }
}