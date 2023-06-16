package com.example.musicplayercompose.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.musicmodel.MusicModel
import com.example.domain.usecases.GetSoundsUseCase
import javax.inject.Inject

class NewPagingSource @Inject constructor(
    private val getSoundsUseCase: GetSoundsUseCase,
    private val query:String
) : PagingSource<Int,MusicModel>() {

    override fun getRefreshKey(state: PagingState<Int, MusicModel>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MusicModel> {
        return try {
            val page = params.key ?: 1
            val response = getSoundsUseCase.getExecute(
                query = query,
                page = page,
                pageSize = 15
            )
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}