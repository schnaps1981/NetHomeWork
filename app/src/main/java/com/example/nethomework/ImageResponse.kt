package com.example.nethomework

data class ImageResponse(
    val success: Boolean = false,
    val status: Int = 0,
    val data: UploadedImage = UploadedImage.EMPTY
) {

    fun isEmpty() = this === EMPTY

    data class UploadedImage(
        val id: String = "",
        val title: String = "",
        val description: String = "",
        val type: String = "",
        val animated: Boolean = false,
        val width: Int = 0,
        val height: Int = 0,
        val size: Int = 0,
        val views: Int = 0,
        val bandwidth: Int = 0,
        val vote: String = "",
        val favorite: Boolean = false,
        val account_url: String = "",
        val deletehash: String = "",
        val name: String = "",
        val link: String = ""
    ) {
        fun isEmpty() = this === EMPTY

        companion object {
            val EMPTY = UploadedImage()
        }
    }

    companion object {
        val EMPTY = ImageResponse()
    }
}
