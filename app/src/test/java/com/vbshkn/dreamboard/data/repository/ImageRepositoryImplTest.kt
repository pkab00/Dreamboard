package com.vbshkn.dreamboard.data.repository

import com.google.common.truth.Truth.assertThat
import com.vbshkn.dreamboard.data.remote.model.ImageListResponse
import com.vbshkn.dreamboard.data.remote.model.ImageResponse
import com.vbshkn.dreamboard.domain.datasource.ImageDataSource
import com.vbshkn.dreamboard.util.ImageFormat
import com.vbshkn.dreamboard.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ImageRepositoryImplTest {
    private val dataSource = mockk<ImageDataSource>()
    private val repository = ImageRepositoryImpl(dataSource)

    @Test
    fun `getImage flow emits valid resource states and doesn't affect the data`() = runTest {
        val fakeId = 123L
        val fakeResponse = ImageResponse(
            id = fakeId,
            title = "mocked",
            contentType = "png",
            thumbnailUrl = "some url",
            thumbnailUrl2x = "some url",
            previewUrl = "some url"
        )
        coEvery { dataSource.getImage(any()) } returns fakeResponse

        val actual = repository.getImage(fakeId).toList()
        val expectedLoading = actual[0]
        val expectedSuccess = actual[1]
        val expectedBody = expectedSuccess.body

        assertThat(expectedLoading).isInstanceOf(Resource.Loading::class.java)
        assertThat(expectedSuccess).isInstanceOf(Resource.Success::class.java)
        assertThat(expectedBody).isNotNull()
        assertThat(expectedBody!!.id).isEqualTo(fakeId)
    }

    @Test
    fun `getImages flow emits valid resource states and doesn't affect the data`() = runTest {
        val fakeResponse = ImageListResponse(
            page = 1,
            lastPage = 5,
            perPage = 100,
            totalResources = 500,
            resources = emptyList()
        )
        coEvery { dataSource.getImages(
            contentType = any(),
            aiGenerated = any(),
            term = any(),
            page = any(),
            perPage = any()
        ) } returns fakeResponse

        val actual = repository.getImages(
            imageFormat = ImageFormat.PHOTO,
            aiGenerated = false,
            term = "gorilla",
            page = 1,
            perPage = 100
        ).toList()
        val expectedLoading = actual[0]
        val expectedSuccess = actual[1]
        val expectedBody = expectedSuccess.body

        assertThat(expectedLoading).isInstanceOf(Resource.Loading::class.java)
        assertThat(expectedSuccess).isInstanceOf(Resource.Success::class.java)
        assertThat(expectedBody).isNotNull()
        assertThat(expectedBody!!.imageEntities).hasSize(fakeResponse.resources.size)
    }

    @Test
    fun `getImages handles exceptions thrown on API limitations`() = runTest {
        val limitation = 10_000
        val expectedLimitationBreak: (Int) -> Boolean = { it >= limitation }
        coEvery {
            dataSource.getImages(
                contentType = any(),
                aiGenerated = any(),
                term = any(),
                page = match(expectedLimitationBreak),
                perPage = match(expectedLimitationBreak)
            )
        } throws Exception()

        val unsafeRepositoryCall1 = repository.getImages(
            imageFormat = ImageFormat.PHOTO,
            aiGenerated = false,
            term = "pikachu",
            page = limitation,
            perPage = 1000
        ).toList()
        val unsafeRepositoryCall2 = repository.getImages(
            imageFormat = ImageFormat.PHOTO,
            aiGenerated = false,
            term = "pikachu",
            page = 1,
            perPage = limitation
        ).toList()

        assertThat(unsafeRepositoryCall1[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(unsafeRepositoryCall2[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(unsafeRepositoryCall1[1]).isInstanceOf(Resource.Error::class.java)
        assertThat(unsafeRepositoryCall2[1]).isInstanceOf(Resource.Error::class.java)
    }
}