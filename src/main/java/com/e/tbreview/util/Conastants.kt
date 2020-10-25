package com.e.tbreview.util

class Constants {

    companion object {
        const val TESTING_NETWORK_DELAY = 1000L

        const val BASE_URL: String = "https://s3-us-west-2.amazonaws.com/"

        const val NETWORK_TIMEOUT = 3000L
        const val CACHE_TIMEOUT = 2000L
        const val TESTING_CACHE_DELAY = 3000L // fake cache delay for testing


        const val PAGINATION_PAGE_SIZE = 10

        const val GALLERY_REQUEST_CODE = 201
        const val PERMISSIONS_REQUEST_READ_STORAGE: Int = 301
        const val CROP_IMAGE_INTENT_CODE: Int = 401
    }
}