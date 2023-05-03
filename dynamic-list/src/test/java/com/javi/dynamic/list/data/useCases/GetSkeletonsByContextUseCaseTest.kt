package com.javi.dynamic.list.data.useCases

import com.javi.dynamic.list.domain.database.AppDatabase
import com.javi.dynamic.list.domain.database.skeletons.SkeletonsDao
import com.javi.dynamic.list.domain.useCases.GetSkeletonsByContextUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetSkeletonsByContextUseCaseTest {

    @Mock
    lateinit var database: AppDatabase

    @Mock
    lateinit var skeletonsDao: SkeletonsDao

    private lateinit var getSkeletonsByContextUseCase: GetSkeletonsByContextUseCaseImpl

    @Before
    fun setUp() {
        getSkeletonsByContextUseCase = GetSkeletonsByContextUseCaseImpl(
            database
        )

        whenever(database.skeletonsDao()).thenReturn(skeletonsDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `LoadingAction should return if there are not skeletons`() = runTest {
        whenever(skeletonsDao.provideSkeletonsByContext(String())).thenReturn(null)
        assert(getSkeletonsByContextUseCase.invoke(String()).isEmpty())
    }
}
