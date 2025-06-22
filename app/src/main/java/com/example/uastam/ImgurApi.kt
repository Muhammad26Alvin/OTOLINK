import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImgurApi {
    @Multipart
    @POST("image")
    fun uploadImage(
        @Header("Authorization") clientId: String,
        @Part image: MultipartBody.Part
    ): Call<ImgurResponse>
}
