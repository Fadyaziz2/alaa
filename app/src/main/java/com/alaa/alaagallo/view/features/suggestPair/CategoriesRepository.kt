package com.alaa.alaagallo.view.features.suggestPair

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alaa.Constants
import com.alaa.alaagallo.api.registration.AllCategoriesFactory
import com.alaa.alaagallo.base.helpers.Resource
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
import retrofit2.Callback
import retrofit2.Response

object CategoriesRepository {


    fun getAllCategories(
        token: String,
    ): LiveData<Resource<TypesResponse>> {
        val data = MutableLiveData<Resource<TypesResponse>>()
        data.value = Resource.loading()

        AllCategoriesFactory.getAllCategories(
            token
        ).enqueue(object : Callback<TypesResponse> {
            override fun onResponse(call: Call<TypesResponse>, response: Response<TypesResponse>) {
                if (response.isSuccessful) {
//                    Home.token = response.body()?.token!!
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message()))
                }
            }

            override fun onFailure(call: Call<TypesResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message))
            }

        })

        return data
    }

    fun logOut(
        token: String,
    ): LiveData<Resource<LogOutResponse>> {
        val data = MutableLiveData<Resource<LogOutResponse>>()
        data.value = Resource.loading()

        AllCategoriesFactory.logOut(
            token
        ).enqueue(object : Callback<LogOutResponse> {
            override fun onResponse(
                call: Call<LogOutResponse>,
                response: Response<LogOutResponse>,
            ) {
                if (response.isSuccessful) {
//                    Home.token = response.body()?.token!!
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message()))
                }
            }

            override fun onFailure(call: Call<LogOutResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message))
            }
        })

        return data
    }


    fun getAllBrands(
        token: String,
    ): LiveData<Resource<BrandsResponse>> {
        val data = MutableLiveData<Resource<BrandsResponse>>()
        data.value = Resource.loading()

        AllCategoriesFactory.getAllBrands(
            token
        ).enqueue(object : Callback<BrandsResponse> {
            override fun onResponse(
                call: Call<BrandsResponse>,
                response: Response<BrandsResponse>,
            ) {
                if (response.isSuccessful) {
//                    Home.token = response.body()?.token!!
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message()))
                }
            }

            override fun onFailure(call: Call<BrandsResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message))
            }

        })

        return data
    }


    fun getAllModels(
        token: String,
    ): LiveData<Resource<ModelsResponse>> {
        val data = MutableLiveData<Resource<ModelsResponse>>()
        data.value = Resource.loading()

        AllCategoriesFactory.getAllModels(
            token
        ).enqueue(object : Callback<ModelsResponse> {
            override fun onResponse(
                call: Call<ModelsResponse>,
                response: Response<ModelsResponse>,
            ) {
                if (response.isSuccessful) {
//                    Home.token = response.body()?.token!!
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message()))
                }
            }

            override fun onFailure(call: Call<ModelsResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message))
            }

        })

        return data
    }


    fun getAllSubCategories(
        token: String,
        id: String,
    ): LiveData<Resource<SubCategoriesModelResponse>> {
        val data = MutableLiveData<Resource<SubCategoriesModelResponse>>()
        data.value = Resource.loading()

        AllCategoriesFactory.getAllSubCategories(
            token, id
        ).enqueue(object : Callback<SubCategoriesModelResponse> {
            override fun onResponse(
                call: Call<SubCategoriesModelResponse>,
                response: Response<SubCategoriesModelResponse>,
            ) {
                if (response.isSuccessful) {
//                    Home.token = response.body()?.token!!
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message()))
                }
            }

            override fun onFailure(call: Call<SubCategoriesModelResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message))
            }

        })

        return data
    }


    fun getAllCateByID(
        token: String,
        id: String,
    ): LiveData<Resource<ShowBrandsResponse>> {
        val data = MutableLiveData<Resource<ShowBrandsResponse>>()
        data.value = Resource.loading()

        AllCategoriesFactory.getAllBrandsByID(
            token, id
        ).enqueue(object : Callback<ShowBrandsResponse> {
            override fun onResponse(
                call: Call<ShowBrandsResponse>,
                response: Response<ShowBrandsResponse>,
            ) {
                if (response.isSuccessful) {
//                    Home.token = response.body()?.token!!
//                    Log.d("getDaa",response.body()!!.data.get(0).name)
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message()))
                }
            }

            override fun onFailure(call: Call<ShowBrandsResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message))
            }

        })

        return data
    }

    fun getAllCompitable(
        token: String,

        ): LiveData<Resource<CompatibilitiesResponse>> {
        val data = MutableLiveData<Resource<CompatibilitiesResponse>>()
        data.value = Resource.loading()

        AllCategoriesFactory.getAllCompatibility(
            token
        ).enqueue(object : Callback<CompatibilitiesResponse> {
            override fun onResponse(
                call: Call<CompatibilitiesResponse>,
                response: Response<CompatibilitiesResponse>,
            ) {
                if (response.isSuccessful) {
//                    Home.token = response.body()?.token!!
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message()))
                }
            }

            override fun onFailure(call: Call<CompatibilitiesResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message))
            }

        })

        return data
    }

    fun getAllNumbers(
        token: String,

        ): LiveData<Resource<NumberrsResponseModel>> {
        val data = MutableLiveData<Resource<NumberrsResponseModel>>()
        data.value = Resource.loading()

        AllCategoriesFactory.getAllNumbers(
            token
        ).enqueue(object : Callback<NumberrsResponseModel> {
            override fun onResponse(
                call: Call<NumberrsResponseModel>,
                response: Response<NumberrsResponseModel>,
            ) {
                if (response.isSuccessful) {
//                    Home.token = response.body()?.token!!
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message()))
                }
            }

            override fun onFailure(call: Call<NumberrsResponseModel>, t: Throwable) {
                data.postValue(Resource.error(t.message))
            }

        })

        return data
    }

    fun showCompitable(
        token: String,
        id: String,
    ): LiveData<Resource<ShowCompaitabiltyResponse>> {
        val data = MutableLiveData<Resource<ShowCompaitabiltyResponse>>()
        data.value = Resource.loading()

        AllCategoriesFactory.showCompatibility(
            token, id
        ).enqueue(object : Callback<ShowCompaitabiltyResponse> {
            override fun onResponse(
                call: Call<ShowCompaitabiltyResponse>,
                response: Response<ShowCompaitabiltyResponse>,
            ) {
                if (response.isSuccessful) {
//                    Home.token = response.body()?.token!!
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message()))
                }
            }

            override fun onFailure(call: Call<ShowCompaitabiltyResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message))
            }

        })

        return data
    }

    fun isUserExpired(
        token: String,
    ): LiveData<Resource<BaseResponse>> {
        val data = MutableLiveData<Resource<BaseResponse>>()
        data.value = Resource.loading()

        AllCategoriesFactory.isUserExpired(
            token
        ).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
//                    Home.token = response.body()?.token!!
                    data.postValue(Resource.success(response.body()))
                } else {
                    if (response.code() == 400) {
                        Constants.isExpired = true
                        data.postValue(Resource.error(response.message()))
                    }
                    else
                        data.postValue(Resource.error(null))
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message))
            }

        })

        return data
    }

    suspend fun checkUserData(
        token: String,
    ): Response<CheckUserData> {

        return AllCategoriesFactory.checkUserData(
            token
        )
    }


    fun searchInCompitabile(
        token: String,
        brand_id: String,
        mod_id: String,
        type_id: String,
    ): LiveData<Resource<NewSearchResponse>> {
        val data = MutableLiveData<Resource<NewSearchResponse>>()
        data.value = Resource.loading()

        AllCategoriesFactory.searchInCompaitablite(
            token, brand_id, mod_id, type_id
        ).enqueue(object : Callback<NewSearchResponse> {
            override fun onResponse(
                call: Call<NewSearchResponse>,
                response: Response<NewSearchResponse>,
            ) {
                if (response.isSuccessful) {
//                    Home.token = response.body()?.token!!
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message()))
                }
            }

            override fun onFailure(call: Call<NewSearchResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message))
            }

        })

        return data
    }


    fun suggestAnCompitabile(
        token: String,
        suggestCompatibiltyModel: SuggestCompatibiltyModel,
    ): LiveData<Resource<SuggestCompaitabiltyResponse>> {
        val data = MutableLiveData<Resource<SuggestCompaitabiltyResponse>>()
        data.value = Resource.loading()

        AllCategoriesFactory.suggestAnCompaitbilty(
            token, suggestCompatibiltyModel
        ).enqueue(object : Callback<SuggestCompaitabiltyResponse> {
            override fun onResponse(
                call: Call<SuggestCompaitabiltyResponse>,
                response: Response<SuggestCompaitabiltyResponse>,
            ) {
                if (response.isSuccessful) {
//                    Home.token = response.body()?.token!!
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message()))
                }
            }

            override fun onFailure(call: Call<SuggestCompaitabiltyResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message))
            }

        })

        return data
    }


    fun sendComplaints(
        token: String,
        complaintsModel: ComplaintsModel,
    ): LiveData<Resource<ComplaintsResponse>> {
        val data = MutableLiveData<Resource<ComplaintsResponse>>()
        data.value = Resource.loading()

        AllCategoriesFactory.sendComplaints(
            token, complaintsModel
        ).enqueue(object : Callback<ComplaintsResponse> {
            override fun onResponse(
                call: Call<ComplaintsResponse>,
                response: Response<ComplaintsResponse>,
            ) {
                if (response.isSuccessful) {
//                    Home.token = response.body()?.token!!
                    data.postValue(Resource.success(response.body()))
                } else {
                    data.postValue(Resource.error(response.message()))
                }
            }

            override fun onFailure(call: Call<ComplaintsResponse>, t: Throwable) {
                data.postValue(Resource.error(t.message))
            }

        })

        return data
    }


}