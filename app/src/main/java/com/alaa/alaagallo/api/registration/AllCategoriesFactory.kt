package com.alaa.alaagallo.api.registration

import com.alaa.alaagallo.api.ServiceBuilder
import com.alaa.alaagallo.model.CheckUserData
import com.alaa.alaagallo.model.base.BaseResponse
import com.alaa.alaagallo.model.brands.BrandsResponse
import com.alaa.alaagallo.model.compatibalities.CompatibilitiesResponse
import com.alaa.alaagallo.model.complaints.ComplaintsModel
import com.alaa.alaagallo.model.complaints.complaints_response.ComplaintsResponse
import com.alaa.alaagallo.model.logout.LogOutResponse
import com.alaa.alaagallo.model.modelss.ModelsResponse
import com.alaa.alaagallo.model.newnumbers.NumberrsResponseModel
import com.alaa.alaagallo.model.show_compaitibilites.ShowCompaitabiltyResponse
import com.alaa.alaagallo.model.showbrands.ShowBrandsResponse
import com.alaa.alaagallo.model.sub_categories.SubCategoriesModelResponse
import com.alaa.alaagallo.model.suggest_compaitabilty.SuggestCompaitabiltyResponse
import com.alaa.alaagallo.model.suggest_compatibilty.SuggestCompatibiltyModel
import com.alaa.alaagallo.model.typess.TypesResponse
import com.alaa.alaagallo.view.home.search_comp.newsearchresponse.NewSearchResponse
import retrofit2.Call
import retrofit2.Response

object AllCategoriesFactory {


    fun getAllCategories(
        token: String,
    ): Call<TypesResponse> {
        val loginService = ServiceBuilder.buildService(AllCategories::class.java)
        return loginService.getAllCategories(token);
    }

    fun logOut(
        token: String,
    ): Call<LogOutResponse> {
        val loginService = ServiceBuilder.buildService(AllCategories::class.java)
        return loginService.logOut(token);
    }


    fun getAllSubCategories(
        token: String,
        id: String,
    ): Call<SubCategoriesModelResponse> {
        val loginService = ServiceBuilder.buildService(AllCategories::class.java)
        return loginService.getCategoryById(token = token, id = id);
    }


    fun getAllBrandsByID(
        token: String,
        id: String,
    ): Call<ShowBrandsResponse> {
        val loginService = ServiceBuilder.buildService(AllCategories::class.java)
        return loginService.getBrandsById(token = token, id = id);
    }


    fun getAllBrands(
        token: String,
    ): Call<BrandsResponse> {
        val loginService = ServiceBuilder.buildService(AllCategories::class.java)
        return loginService.getAllBrands(token = token);
    }


    fun getAllModels(
        token: String,
    ): Call<ModelsResponse> {
        val loginService = ServiceBuilder.buildService(AllCategories::class.java)
        return loginService.getAllModels(token = token);
    }

    fun getAllCompatibility(
        token: String,
    ): Call<CompatibilitiesResponse> {
        val loginService = ServiceBuilder.buildService(AllCategories::class.java)
        return loginService.getAllCompatibility(token = token);
    }


    fun getAllNumbers(
        token: String,
    ): Call<NumberrsResponseModel> {
        val loginService = ServiceBuilder.buildService(AllCategories::class.java)
        return loginService.getAllNumbers(token = token);
    }


// TODO  here we will chane all the data for those functions


    fun showCompatibility(
        token: String,
        id: String,
    ): Call<ShowCompaitabiltyResponse> {
        val loginService = ServiceBuilder.buildService(AllCategories::class.java)
        return loginService.showCompatibility(token = token, id = id);
    }


    fun suggestAnCompaitbilty(
        token: String,
        suggetsModel: SuggestCompatibiltyModel,
    ): Call<SuggestCompaitabiltyResponse> {
        val loginService = ServiceBuilder.buildService(AllCategories::class.java)
        return loginService.suggestCompatibility(token, suggetsModel);
    }


    fun sendComplaints(
        token: String,
        complaintsModel: ComplaintsModel,
    ): Call<ComplaintsResponse> {
        val loginService = ServiceBuilder.buildService(AllCategories::class.java)
        return loginService.sendComplaints(
            token,
            name = complaintsModel.name,
            mobile = complaintsModel.mobile,
            notes = complaintsModel.notes
        )
    }
    fun isUserExpired(
        token: String,
    ): Call<BaseResponse> {
        val loginService = ServiceBuilder.buildService(AllCategories::class.java)
        return loginService.isUserExpired(
            token,
        )
    }
    suspend fun checkUserData(
        token: String,
    ): Response<CheckUserData> {
        val loginService = ServiceBuilder.buildService(AllCategories::class.java)
        return loginService.checkUserData(
            token,
        )
    }


    fun searchInCompaitablite(
        token: String,
        brand_id: String,
        mod_id: String,
        type_id: String,
    ): Call<NewSearchResponse> {
        val loginService = ServiceBuilder.buildService(AllCategories::class.java)
        return loginService.searchInCompatibility(token, brand_id, mod_id, type_id)
    }


}