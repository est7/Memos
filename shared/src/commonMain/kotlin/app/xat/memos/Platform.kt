package app.xat.memos

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform