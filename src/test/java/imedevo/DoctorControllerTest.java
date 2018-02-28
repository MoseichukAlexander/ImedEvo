//package imedevo;
//
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import imedevo.controller.DoctorController;
//import imedevo.model.Doctor;
//import imedevo.repository.DoctorRepository;
//import imedevo.service.DoctorService;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = DoctorController.class, secure = false)
//public class DoctorControllerTest {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @MockBean
//  private DoctorService doctorService;
//  private DoctorRepository doctorRepository;
//  private Doctor doctor = new Doctor();
//  private List<Doctor> doctors = new ArrayList<>();
//
//  public String objectToJson(Object object) throws Exception {
//    ObjectMapper mapper = new ObjectMapper();
//    return mapper.writeValueAsString(object);
//  }
//
//  @Test
//  public void getAll() throws Exception {
//    when(doctorService.getAll()).thenReturn(Arrays.asList(doctor));
//
//    mockMvc.perform(get("/doctors/getall"))
//        .andDo(print())
//        .andExpect(content().string(objectToJson(Arrays.asList(doctor))));
//  }
//}
