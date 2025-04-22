package cat.itb.damv.m78.dbdemocamera

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform