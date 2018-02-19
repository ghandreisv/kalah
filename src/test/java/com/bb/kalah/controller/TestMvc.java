package com.bb.kalah.controller;

import com.bb.kalah.view.BadRequestException;
import com.bb.kalah.view.GameSessionView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestMvc {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testCreateDefault() throws Exception {
        mvc.perform(get("/newGame").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("playerA.name").value("Ann"))
                .andExpect(MockMvcResultMatchers.jsonPath("playerB.name").value("Bob"));
    }

    @Test
    public void testCreate() throws Exception {
        mvc.perform(get("/newGame?player_a=John&player_b=Diana").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("playerA.name").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("playerB.name").value("Diana"));
    }

    @Test
    public void testInvalidSession() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/move?game_id=-1&player_id=0&start_pit=0"))
                .andExpect(status().is4xxClientError())
                .andReturn();
        assertEquals(BadRequestException.class, mvcResult.getResolvedException().getClass());
    }

    @Test
    public void testCreateClose() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = mvc.perform(get("/newGame").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        GameSessionView gameSessionView = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GameSessionView.class);

        mvc.perform(get("/endGame?game_id=" + + gameSessionView.getGameId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("gameStatus").value("ENDED"));
    }

    @Test
    public void testCreateCloseNoId() throws Exception {
        mvc.perform(get("/newGame").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        MvcResult mvcResult = mvc.perform(get("/endGame").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
        assertEquals(MissingServletRequestParameterException.class, mvcResult.getResolvedException().getClass());
    }

    @Test
    public void testInvalidPlayer() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = mvc.perform(get("/newGame").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        GameSessionView gameSessionView = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GameSessionView.class);
        mvc.perform(get("/move?game_id="+ gameSessionView.getGameId() + "&player_id=-1&start_pit=0"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Error: Player id=-1 not found in game session id=" + gameSessionView.getGameId()));
    }

}
