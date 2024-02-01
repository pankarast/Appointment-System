package com.pankarast.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pankarast.Dto.DoctorDTO;
import com.pankarast.Service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllDoctors() throws Exception {
        DoctorDTO doctor = new DoctorDTO();
        doctor.setId(1L);
        doctor.setName("Dr. Smith");
        doctor.setSpecialty("Cardiology");

        given(doctorService.findAllDoctors()).willReturn(Arrays.asList(doctor));

        mockMvc.perform(get("/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(doctor.getName())));
    }

    @Test
    void getDoctorById() throws Exception {
        Long doctorId = 1L;
        DoctorDTO doctor = new DoctorDTO();
        doctor.setId(doctorId);
        doctor.setName("Dr. Smith");
        doctor.setSpecialty("Cardiology");

        given(doctorService.findDoctorById(doctorId)).willReturn(doctor);

        mockMvc.perform(get("/doctors/{id}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(doctor.getName())));
    }

    @Test
    void createDoctor() throws Exception {
        DoctorDTO doctor = new DoctorDTO();
        doctor.setName("Dr. New");
        doctor.setSpecialty("Neurology");

        DoctorDTO savedDoctor = new DoctorDTO();
        savedDoctor.setId(1L);
        savedDoctor.setName(doctor.getName());
        savedDoctor.setSpecialty(doctor.getSpecialty());

        given(doctorService.updateDoctor(doctor)).willReturn(savedDoctor);

        mockMvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void updateDoctor() throws Exception {
        Long doctorId = 1L;
        DoctorDTO doctor = new DoctorDTO();
        doctor.setId(doctorId);
        doctor.setName("Dr. Updated");
        doctor.setSpecialty("Updated Specialty");

        given(doctorService.updateDoctor(doctor)).willReturn(doctor);

        mockMvc.perform(put("/doctors/{id}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(doctor.getName())));
    }

    @Test
    void deleteDoctor() throws Exception {
        Long doctorId = 1L;

        mockMvc.perform(delete("/doctors/{id}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

