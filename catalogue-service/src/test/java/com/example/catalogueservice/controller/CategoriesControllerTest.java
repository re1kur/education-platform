//package com.example.catalogueservice.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import dto.CategoryDto;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import payload.CategoryPayload;
//import payload.CategoryUpdatePayload;
//import com.example.exception.CategoryConflictException;
//import com.example.exception.exception.CategoryNotFoundException;
//import com.example.catalogueservice.service.CategoryService;
//
//import java.util.List;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = CategoriesController.class)
//@AutoConfigureMockMvc(addFilters = false)
//class CategoriesControllerTest {
//    @MockitoBean
//    private CategoryService service;
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    private static final String url = "/api/category";
//
//    @Test
//    void testGet__CategoryValid__DoesNotThrowException() throws Exception {
//        int categoryId = 1;
//
//        CategoryDto expected = new CategoryDto(1, "title", "desc", "url");
//
//        Mockito.when(service.get(1)).thenReturn(expected);
//
//        mvc.perform(MockMvcRequestBuilders.get(url + "/get").contentType(MediaType.APPLICATION_JSON).param("id", Integer.toString(categoryId))).andExpect(status().isFound()).andExpect(content().json(mapper.writeValueAsString(expected)));
//    }
//
//    @Test
//    void testGet__CategoryNotExist__ThrowCategoryNotFoundException() throws Exception {
//        int categoryId = 1;
//
//        Mockito.when(service.get(1)).thenThrow(CategoryNotFoundException.class);
//
//        mvc.perform(MockMvcRequestBuilders.get(url + "/get").contentType(MediaType.APPLICATION_JSON).param("id", Integer.toString(categoryId))).andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testGetCategoryList() throws Exception {
//        CategoryDto categoryDto1 = new CategoryDto(1, "title", "desc", "url");
//        CategoryDto categoryDto2 = new CategoryDto(2, "title", "desc", "url");
//        List<CategoryDto> expected = List.of(categoryDto1, categoryDto2);
//
//        Mockito.when(service.getList()).thenReturn(expected);
//
//        mvc.perform(MockMvcRequestBuilders.get(url + "/list")).andExpect(status().isOk()).andExpect(content().json(mapper.writeValueAsString(expected)));
//    }
//
//    @Test
//    void testCreate__CategoryValid__DoesNotThrowException() throws Exception {
//        CategoryPayload payload = new CategoryPayload("title", "desc");
//
//        Mockito.doNothing().when(service).create(payload);
//
//        mvc.perform(MockMvcRequestBuilders.post(url + "/create").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(payload))).andExpect(status().isCreated());
//    }
//
//    @Test
//    void testCreate__CategoryNotValid__ThrowMethodArgumentNotValidException() throws Exception {
//        CategoryPayload invalidPayload = new CategoryPayload("", "");
//
//        mvc.perform(MockMvcRequestBuilders.post(url + "/create").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(invalidPayload))).andExpect(status().isBadRequest());
//
//        Mockito.verifyNoInteractions(service);
//    }
//
//    @Test
//    void testCreate__CategoryAlreadyExist__ThrowCategoryAlreadyExistException() throws Exception {
//        CategoryPayload payload = new CategoryPayload("title", "desc");
//
//        Mockito.doThrow(CategoryConflictException.class).when(service).create(payload);
//
//        mvc.perform(MockMvcRequestBuilders.post(url + "/create").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(payload))).andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void testUpdate__ValidPayload__DoesNotThrowException() throws Exception {
//        CategoryUpdatePayload payload = new CategoryUpdatePayload(1, "title", "desc");
//
//        Mockito.doNothing().when(service).update(payload);
//
//        mvc.perform(MockMvcRequestBuilders.put(url + "/update").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(payload))).andExpect(status().isOk());
//
//        Mockito.verify(service, Mockito.times(1)).update(payload);
//    }
//
//    @Test
//    void testUpdate__CategoryInvalid__ThrowMethodArgumentNotValidException() throws Exception {
//        CategoryUpdatePayload payload = new CategoryUpdatePayload(null, "title", "desc");
//
//        mvc.perform(MockMvcRequestBuilders.put(url + "/update").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(payload))).andExpect(status().isBadRequest());
//
//        Mockito.verifyNoInteractions(service);
//    }
//
//    @Test
//    void testUpdate__CategoryNotFound__ThrowCategoryNotFoundException() throws Exception {
//        CategoryUpdatePayload payload = new CategoryUpdatePayload(1, "new-title", "new-desc");
//
//        Mockito.doThrow(CategoryNotFoundException.class).when(service).update(payload);
//
//        mvc.perform(MockMvcRequestBuilders.put(url + "/update").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(payload))).andExpect(status().isNotFound());
//
//        Mockito.verify(service, Mockito.times(1)).update(payload);
//    }
//
//    @Test
//    void testDelete__CategoryExists__DoesNotThrowException() throws Exception {
//        Integer categoryId = 1;
//
//        Mockito.doNothing().when(service).delete(categoryId);
//
//        mvc.perform(MockMvcRequestBuilders.delete(url + "/delete").param("id", categoryId.toString())).andExpect(status().isOk());
//
//        Mockito.verify(service, Mockito.times(1)).delete(categoryId);
//    }
//
//}