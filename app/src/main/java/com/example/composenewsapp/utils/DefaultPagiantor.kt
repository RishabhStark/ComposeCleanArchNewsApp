package com.example.composenewsapp.utils

class NewsPaginator<Item, Key>(
    private val initialKey: Key,
    inline val onLoadUpdated: (Boolean) -> Unit,
    inline val getNextKey: suspend (Item?) -> Key,
    inline val onRequest: suspend (nextKey: Key) -> Resource<Item>,
    inline val onError: suspend (message: String?) -> Unit,
    inline val onSuccess: suspend (item: Item, newKey: Key) -> Unit
) : Paginator<Item, Key> {
    private var isMakingRequest = false
    private var currentkey = initialKey
    override suspend fun loadNextItems() {
        if (isMakingRequest) {
            return
        }
        isMakingRequest = true
        //start loading
        onLoadUpdated(true)
        val result: Resource<Item> = onRequest(currentkey)
        isMakingRequest = false
        val item = result.data
        if (result !is Resource.Success || item == null) {
            onError((result as Resource.Error).message)
            onLoadUpdated(true)
            return
        }
        currentkey = getNextKey(item)
        onSuccess(item, currentkey)
        //finished loading
        onLoadUpdated(false)
    }

    override fun reset() {
        currentkey = initialKey
    }
}