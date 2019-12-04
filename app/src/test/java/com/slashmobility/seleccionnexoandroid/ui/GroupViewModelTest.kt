package com.slashmobility.seleccionnexoandroid.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.slashmobility.seleccionnexoandroid.database.daos.GroupDao
import com.slashmobility.seleccionnexoandroid.database.daos.GroupImagesDao
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.remote.api.ApiClient
import com.slashmobility.seleccionnexoandroid.ui.main.GroupViewModel
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GroupViewModelTest{

    /**
    *   Lo que básicamente hace esta regla es permitirnos ejecutar LiveData sincrónicamente.
    *   Esta regla es del paquete de prueba central.
     **/

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    /**
     *   Las siguientes declaraciones nos permite burlarnos de las distintas clases.
     **/
    @InjectMocks
    private val groupDao  = mock(GroupDao::class.java)

    @InjectMocks
    private val groupImagesDao  = mock(GroupImagesDao::class.java)

    @InjectMocks
    private val apiClient  = mock(ApiClient::class.java)

    /**
     *   Instanciamos el ViewModel.
     **/
    private lateinit var viewModel : GroupViewModel

    /**
     *   Datos Utiles.
     **/
    private val groupTest1 : Group = Group(1, 1460282400000, "url", "Descripcion", "ShortDescripcion", "Deportes")
    private val groupTest2 : Group = Group(2, 1460282400000, "url", "Descripcion", "ShortDescripcion", "Deportes")

    private val groups = listOf(
        groupTest1,
        groupTest2
    )

    /**
     *   Inicializo ViewModel antes de que se ejcuten todos los test.
     **/
    @Before
     fun setUp(){
        MockitoAnnotations.initMocks(this)

        viewModel = GroupViewModel(groupDao, groupImagesDao, apiClient)
    }


    @Test
    fun testNull() {
        assertThat(viewModel.getGroupListResponse(), notNullValue())
        verify(groupDao, never()).getById(anyLong())
    }


    @Test
    fun dontFetchWithoutObservers() {
        viewModel.addGroupToDB(Group())
        verify(groupDao, never()).getById(anyLong())
    }


    @Test
    fun fetchWhenObserved() {
        viewModel.addGroupToDB(groupTest1)

        viewModel.getGroupListResponse().observeForever(mock())

        verify(groupDao, never()).getById(1)
    }


    @Test
    fun changeWhileObserved() {
        viewModel.addGroupToDB(groupTest1)
        viewModel.addGroupToDB(groupTest2)

        viewModel.getGroupListResponse().observeForever(mock())

        verify(groupDao, never()).getById(1)
        verify(groupDao, never()).getById(2)
    }

}