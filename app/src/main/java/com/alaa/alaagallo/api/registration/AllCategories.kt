package com.alaa.alaagallo.api.registration

import com.alaa.alaagallo.model.CheckUserData
import com.alaa.alaagallo.model.base.BaseResponse
import com.alaa.alaagallo.model.brands.BrandsResponse
import com.alaa.alaagallo.model.compatibalities.CompatibilitiesResponse
import com.alaa.alaagallo.model.complaints.complaints_response.ComplaintsResponse
import com.alaa.alaagallo.model.logout.LogOutResponse
import com.alaa.alaagallo.model.modelss.ModelsResponse
import com.alaa.alaagallo.model.newnumbers.NumberrsResponseModel
import com.alaa.alaagallo.model.show_compaitibilites.ShowCompaitabiltyResponse
import com.alaa.alaagallo.model.typess.TypesResponse
import com.alaa.alaagallo.model.showbrands.ShowBrandsResponse
import com.alaa.alaagallo.model.sub_categories.SubCategoriesModelResponse
import com.alaa.alaagallo.model.suggest_compaitabilty.SuggestCompaitabiltyResponse
import com.alaa.alaagallo.model.suggest_compatibilty.SuggestCompatibiltyModel
import com.alaa.alaagallo.view.home.search_comp.newsearchresponse.NewSearchResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AllCategories {

    @Headers("Accept: application/json")
//    @GET("categories")
    @GET("types")
    fun getAllCategories(
        @Header("Authorization") token: String
    ): Call<TypesResponse>

    @Headers("Accept: application/json")
    @POST("logout")
    fun logOut(
        @Header("Authorization") token: String
    ): Call<LogOutResponse>

    @Headers("Accept: application/json")
    @GET("categories/{id}")
    fun getCategoryById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<SubCategoriesModelResponse>

    @Headers("Accept: application/json")
    @GET("brands/{id}")
    fun getBrandsById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<ShowBrandsResponse>

    @Headers("Accept: application/json")
    @GET("brands")
    fun getAllBrands(
        @Header("Authorization") token: String
    ): Call<BrandsResponse>

    @Headers("Accept: application/json")
    @GET("mods")
    fun getAllModels(
        @Header("Authorization") token: String
    ): Call<ModelsResponse>



    @Headers("Accept: application/json")
    @POST("suggestions")
    fun suggestCompatibility(
        @Header("Authorization") token: String,
        @Body suggestion: SuggestCompatibiltyModel
    ): Call<SuggestCompaitabiltyResponse>


    // has Compatibility
    @Headers("Accept: application/json")
    @GET("categories/has/compatibilities")
    fun getAllCompatibility(
        @Header("Authorization") token: String
    ): Call<CompatibilitiesResponse>


    @Headers("Accept: application/json")
    @GET("show/categories/has/compatibilities/types/{id}")
    fun showCompatibility(
        @Header("Authorization") token: String,
        @Path ("id")id :String
    ): Call<ShowCompaitabiltyResponse>


    // search
    @Headers("Accept: application/json")
    @GET("search")
    fun searchInCompatibility(
        @Header("Authorization") token: String,
        @Query("brand_id") brand_id: String,
        @Query("mod_id") mod_id: String,
        @Query("type_id") type_id: String
    ): Call<NewSearchResponse>





    @Headers("Accept: application/json")
    @GET("numbers")
    fun getAllNumbers(
        @Header("Authorization") token: String
    ): Call<NumberrsResponseModel>

    @Headers("Accept: application/json")
    @POST("complaints")
    @FormUrlEncoded
    fun sendComplaints(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("mobile") mobile: String,
        @Field("notes") notes: String,
    ): Call<ComplaintsResponse>

    @Headers("Accept: application/json")
    @GET("check/expired/auth/data")
    fun isUserExpired(
        @Header("Authorization") token: String,
    ): Call<BaseResponse>

    @Headers("Accept: application/json")
    @GET("auth/user/data")
    suspend fun checkUserData(
        @Header("Authorization") token: String,
    ): Response<CheckUserData>

}