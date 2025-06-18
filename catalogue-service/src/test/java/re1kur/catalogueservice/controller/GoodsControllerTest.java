package re1kur.catalogueservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.GoodsDto;
import dto.GoodsPageDto;
import filter.GoodsFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import payload.GoodsPayload;
import payload.GoodsUpdatePayload;
import re1kur.catalogueservice.exception.CategoryNotFoundException;
import re1kur.catalogueservice.exception.GoodsNotFoundException;
import re1kur.catalogueservice.service.impl.DefaultGoodsService;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GoodsController.class)
@AutoConfigureMockMvc(addFilters = false)
class GoodsControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private DefaultGoodsService service;

    private static final String url = "/api/goods/";

    @Test
    void testGetGoodsById__GoodsExists__ReturnsFound() throws Exception {
        Integer goodsId = 1;

        GoodsDto expected = new GoodsDto(1, "title", 1, "description", new BigDecimal("10.00"), true, "url");

        Mockito.when(service.get(goodsId)).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.get(url + "/get")
                        .param("id", goodsId.toString()))
                .andExpect(status().isFound())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @Test
    void testGetGoodsById__NotFound__ThrowsGoodsNotFoundException() throws Exception {
        Integer goodsId = 1;

        Mockito.doThrow(GoodsNotFoundException.class).when(service).get(goodsId);

        mvc.perform(MockMvcRequestBuilders.get(url + "/get")
                        .param("id", goodsId.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetGoodsList__DefaultPageParams__ReturnsOk() throws Exception {
        GoodsFilter filter = new GoodsFilter("phone", 1, new BigDecimal("10.00"));
        Pageable pageable = PageRequest.of(0, 4);
        List<GoodsDto> content = List.of(
                new GoodsDto(1, "title", 1, "desc", new BigDecimal("10.00"), true, "url"));
        Page<GoodsDto> page = new PageImpl<>(content);
        GoodsPageDto response = GoodsPageDto.of(page);

        Mockito.when(service.getPage(pageable, filter)).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.get(url + "/list")
                        .param("title", "phone")
                        .param("categoryId", "1")
                        .param("price", "10.00"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    void testGetGoodsList__CustomPageParams__UsesThem() throws Exception {
        GoodsFilter filter = new GoodsFilter("phone", 1, new BigDecimal("10.00"));
        Pageable pageable = PageRequest.of(1, 10);
        List<GoodsDto> content = List.of(
                new GoodsDto(1, "title", 1, "desc", new BigDecimal("10.00"), true, "url"));
        Page<GoodsDto> page = new PageImpl<>(content);
        GoodsPageDto response = GoodsPageDto.of(page);

        Mockito.when(service.getPage(pageable, filter)).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.get(url + "/list")
                        .param("page", "1")
                        .param("size", "10")
                        .param("categoryId", "1")
                        .param("price", "10.00")
                        .param("title", "phone"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    void testCreateGoods__ValidPayload__DoesNotThrowException() throws Exception {
        GoodsPayload payload = new GoodsPayload("title", 1, "desc", new BigDecimal("10.00"));

        Mockito.doNothing().when(service).create(payload);

        mvc.perform(MockMvcRequestBuilders.post(url + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isCreated());

        Mockito.verify(service).create(payload);
    }

    @Test
    void testCreateGoods__InvalidPayload__ThrowsMethodArgumentNotValidException() throws Exception {
        GoodsPayload invalidPayload = new GoodsPayload("", null, "", new BigDecimal("-5")); // все поля невалидны

        mvc.perform(MockMvcRequestBuilders.post(url + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalidPayload)))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(service);
    }

    @Test
    void testDeleteGoods__Exists__DoesNotThrowException() throws Exception {
        Integer goodsId = 1;

        Mockito.doNothing().when(service).delete(goodsId);

        mvc.perform(delete(url + "/delete")
                        .param("id", goodsId.toString()))
                .andExpect(status().isOk());

        Mockito.verify(service).delete(goodsId);
    }

    @Test
    void testDeleteGoods__NotFound__ThrowsGoodsNotFoundException() throws Exception {
        Integer goodsId = 1;

        Mockito.doThrow(GoodsNotFoundException.class).when(service).delete(goodsId);

        mvc.perform(delete(url + "/delete")
                        .param("id", goodsId.toString()))
                .andExpect(status().isNotFound());

        Mockito.verify(service).delete(goodsId);
    }

    @Test
    void testUpdateGoods__ValidPayload__DoesNotThrowException() throws Exception {
        GoodsUpdatePayload payload = new GoodsUpdatePayload(
                1,
                "new-title",
                true,
                1,
                "new-desc",
                new BigDecimal("20.00"));

        Mockito.doNothing().when(service).update(payload);

        mvc.perform(MockMvcRequestBuilders.put(url + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isOk());

        Mockito.verify(service).update(payload);
    }

    @Test
    void testUpdateGoods__IdIsNull__ThrowsValidationException() throws Exception {
        GoodsUpdatePayload payload = new GoodsUpdatePayload(
                null,
                "new-title",
                true,
                1,
                "new-desc",
                new BigDecimal("20.00"));

        mvc.perform(MockMvcRequestBuilders.put(url + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(service);
    }

    @Test
    void testUpdateGoods__TitleEmpty__ThrowsValidationException() throws Exception {
        GoodsUpdatePayload payload = new GoodsUpdatePayload(
                1,
                "",
                true,
                1,
                "new-desc",
                new BigDecimal("20.00"));

        mvc.perform(MockMvcRequestBuilders.put(url + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(service);
    }

    @Test
    void testUpdateGoods__CategoryNotFound__ThrowsCategoryNotFoundException() throws Exception {
        GoodsUpdatePayload payload = new GoodsUpdatePayload(
                1,
                "new-title",
                true,
                999,
                "new-desc",
                new BigDecimal("20.00"));

        Mockito.doThrow(CategoryNotFoundException.class).when(service).update(payload);

        mvc.perform(MockMvcRequestBuilders.put(url + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isNotFound());

        Mockito.verify(service).update(payload);
    }

}