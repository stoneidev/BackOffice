package com.stoneistudio.lds.member.framework.adapters.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stoneistudio.lds.member.application.usecase.MemberUseCase;
import com.stoneistudio.lds.member.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MemberAdapterTest {

    private MockMvc mockMvc;

    @Mock
    private MemberUseCase memberUseCase;

    @InjectMocks
    private MemberAdapter memberAdapter;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memberAdapter).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void addMember_ShouldReturnOk() throws Exception {
        Member member = new Member("John Doe", "john@example.com");

        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(member)))
                .andExpect(status().isOk());

        verify(memberUseCase, times(1)).addMember(any(Member.class));
    }

    @Test
    void getMemberById_ShouldReturnMember() throws Exception {
        Long memberId = 1L;
        Member member = new Member("John Doe", "john@example.com");
        when(memberUseCase.getMemberById(memberId)).thenReturn(member);

        mockMvc.perform(get("/api/members/{id}", memberId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(memberUseCase, times(1)).getMemberById(memberId);
    }

    @Test
    void getAllMembers_ShouldReturnListOfMembers() throws Exception {
        List<Member> members = Arrays.asList(
                new Member("John Doe", "john@example.com"),
                new Member("Jane Doe", "jane@example.com"));
        when(memberUseCase.getAllMembers()).thenReturn(members);

        mockMvc.perform(get("/api/members"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$[1].email").value("jane@example.com"));

        verify(memberUseCase, times(1)).getAllMembers();
    }

    @Test
    void updateMember_ShouldReturnOk() throws Exception {
        Long memberId = 1L;
        Member member = new Member("John Doe", "john@example.com");

        mockMvc.perform(put("/api/members/{id}", memberId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(member)))
                .andExpect(status().isOk());

        verify(memberUseCase, times(1)).updateMember(any(Member.class));
    }

    @Test
    void deleteMember_ShouldReturnOk() throws Exception {
        Long memberId = 1L;

        mockMvc.perform(delete("/api/members/{id}", memberId))
                .andExpect(status().isOk());

        verify(memberUseCase, times(1)).deleteMember(memberId);
    }
}
