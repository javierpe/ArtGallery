package com.javi.dynamic.list.data.useCases

import com.javi.dynamic.list.domain.database.AppDatabase
import com.javi.dynamic.list.domain.database.skeletons.SkeletonsDao
import com.javi.dynamic.list.domain.database.skeletons.SkeletonsEntity
import com.javi.dynamic.list.domain.useCases.SaveSkeletonsUseCaseImpl
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SaveSkeletonsUseCaseTest {

    @Mock
    lateinit var database: AppDatabase

    @Mock
    lateinit var skeletonsDao: SkeletonsDao

    private lateinit var saveSkeletonUseCaseImpl: SaveSkeletonsUseCaseImpl

    @Before
    fun setUp() {
        saveSkeletonUseCaseImpl = SaveSkeletonsUseCaseImpl(database)
    }

    @Test
    fun `saveSkeletonsByContext should be called when response is success`() {
        whenever(database.skeletonsDao()).thenReturn(skeletonsDao)

        saveSkeletonUseCaseImpl.invoke(
            body = emptyList(),
            header = emptyList(),
            source = String()
        )

        verify(database.skeletonsDao()).saveSkeletonsByContext(
            SkeletonsEntity(
                String(),
                emptyList()
            )
        )
    }
}
