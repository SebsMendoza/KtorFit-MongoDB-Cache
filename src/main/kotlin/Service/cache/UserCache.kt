package Service.cache

import io.github.reactivecircus.cache4k.Cache
import models.User
import kotlin.time.Duration.Companion.seconds

class UserCache {
    val refreshTime = 20000
    val cache = Cache.Builder()
        .expireAfterAccess(30.seconds)
        .maximumCacheSize(10)
        .build<Int, User>()
}