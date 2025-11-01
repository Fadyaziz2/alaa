package com.alaa.alaagallo.view.features.pairs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alaa.alaagallo.base.helpers.Resource
import com.alaa.alaagallo.model.base.BaseResponse
import com.alaa.alaagallo.model.brands.BrandsResponse
import com.alaa.alaagallo.model.compatibalities.CompatibilitiesResponse
import com.alaa.alaagallo.model.complaints.ComplaintsModel
import com.alaa.alaagallo.model.complaints.complaints_response.ComplaintsResponse
import com.alaa.alaagallo.model.modelss.ModelsResponse
import com.alaa.alaagallo.model.newnumbers.NumberrsResponseModel
import com.alaa.alaagallo.model.show_compaitibilites.ShowCompaitabiltyResponse
import com.alaa.alaagallo.model.showbrands.ShowBrandsResponse
import com.alaa.alaagallo.model.sub_categories.SubCategoriesModelResponse
import com.alaa.alaagallo.model.suggest_compaitabilty.SuggestCompaitabiltyResponse
import com.alaa.alaagallo.model.suggest_compatibilty.SuggestCompatibiltyModel
import com.alaa.alaagallo.model.typess.TypesResponse
import com.alaa.alaagallo.view.features.suggestPair.CategoriesRepository
import com.alaa.alaagallo.view.home.search_comp.newsearchresponse.SearchRespoooo

class PairsViewModel : ViewModel() {

    private val _data =
        MutableLiveData<List<SearchRespoooo>>() // Replace DataType with your actual data type
    val data: LiveData<List<SearchRespoooo>> get() = _data

    private val _showNoData = MutableLiveData<Boolean>()
    val showNoData: LiveData<Boolean> get() = _showNoData

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun getAllCategories(token: String): LiveData<Resource<TypesResponse>> {
        return CategoriesRepository.getAllCategories(
            token
        )
    }

    fun isUserExpired(token: String): LiveData<Resource<BaseResponse>> {
        return CategoriesRepository.isUserExpired(
            token
        )
    }

    fun getAllSubCategories(
        token: String,
        id: String,
    ): LiveData<Resource<SubCategoriesModelResponse>> {
        return CategoriesRepository.getAllSubCategories(
            token, id
        )
    }


    fun getAllBrands(token: String): LiveData<Resource<BrandsResponse>> {
        return CategoriesRepository.getAllBrands(
            token
        )
    }


    fun getAllBrandsById(token: String, id: String): LiveData<Resource<ShowBrandsResponse>> {
        return CategoriesRepository.getAllCateByID(
            token, id
        )
    }


    fun getAllModels(token: String): LiveData<Resource<ModelsResponse>> {
        return CategoriesRepository.getAllModels(
            token
        )
    }

    fun getAllCompitability(token: String): LiveData<Resource<CompatibilitiesResponse>> {
        return CategoriesRepository.getAllCompitable(
            token
        )
    }

    fun getAllNumbers(token: String): LiveData<Resource<NumberrsResponseModel>> {
        return CategoriesRepository.getAllNumbers(
            token
        )
    }


    fun showCompitability(
        token: String,
        id: String,
    ): LiveData<Resource<ShowCompaitabiltyResponse>> {
        return CategoriesRepository.showCompitable(
            token, id
        )
    }


    fun suggestAnCompaitbility(
        token: String,
        suggestCompatibiltyModel: SuggestCompatibiltyModel,
    ): LiveData<Resource<SuggestCompaitabiltyResponse>> {
        return CategoriesRepository.suggestAnCompitabile(
            token, suggestCompatibiltyModel
        )
    }


    fun sendComplaints(
        token: String,
        complaintsModel: ComplaintsModel,
    ): LiveData<Resource<ComplaintsResponse>> {
        return CategoriesRepository.sendComplaints(
            token, complaintsModel
        )
    }


    fun searchInCompitabilty(token: String, brand_id: String, mod_id: String, type_id: String) {
        CategoriesRepository.searchInCompitabile(token, brand_id, mod_id, type_id)
            .observeForever { resource ->
                when (resource.status) {
                    Resource.Status.LOADING -> {
                        // Handle loading state if necessary
                        _showLoading.value = true
                        _error.value = ""
                    }

                    Resource.Status.SUCCESS -> {
                        resource.data?.let { response ->
                            // Update the full list of data
                            _showLoading.value = false
                            _data.value = response.data
                            _showNoData.value = response.data.isEmpty()
//                        loadPage() // Load the first page
                        }
                    }

                    Resource.Status.ERROR -> {
                        // Handle error state
                        _showLoading.value = false
                        _error.value = resource.message ?: ""
                    }

                    Resource.Status.EMPTY -> {
                        _showLoading.value = false
                    }
                }
            }
    }


}