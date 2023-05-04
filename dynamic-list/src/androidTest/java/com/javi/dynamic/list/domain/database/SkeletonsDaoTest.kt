package com.javi.dynamic.list.domain.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.javi.data.enums.ContextType
import com.javi.dynamic.list.domain.database.skeletons.SkeletonsDao
import com.javi.dynamic.list.domain.database.skeletons.SkeletonsEntity
import com.javi.render.processor.core.RenderType
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SkeletonsDaoTest {

    private lateinit var skeletonsDao: SkeletonsDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        skeletonsDao = db.skeletonsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeRecentSearchAndReadIt() {
        val skeletonsEntity = SkeletonsEntity(
            ContextType.BANNER_DETAIL.source.uppercase(),
            listOf(RenderType.TEXT, RenderType.MESSAGE)
        )

        skeletonsDao.saveSkeletonsByContext(skeletonsEntity)
        val skeletonsByContext = skeletonsDao.provideSkeletonsByContext(
            ContextType.BANNER_DETAIL.source.uppercase()
        )
        assert(skeletonsByContext == skeletonsEntity)
    }
}
