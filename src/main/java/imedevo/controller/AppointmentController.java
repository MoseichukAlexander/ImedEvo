package imedevo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import imedevo.httpStatuses.AccessDeniedException;
import imedevo.model.Appointment;
import imedevo.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

  @Autowired
  private AppointmentService appointmentService;

  @GetMapping("/byusers/{id}")
  public List<Appointment> getByUserId(@PathVariable long id) {
    return appointmentService.getByUserId(id);
  }

  @GetMapping("bydoctors/{id}")
  public List<Appointment> getByDoctorId(@PathVariable long id) {
    return appointmentService.getByDoctorId(id);
  }

  @PostMapping("/addappointment")
  public Map<String, Object> addAppointment(@RequestBody Appointment appointment) {
    return appointmentService.addAppoinsment(appointment);
  }

  @PutMapping("/updateappointment")
  public Map<String, Object> updateAppointment(@RequestBody Appointment appointment)
      throws AccessDeniedException {
    return appointmentService.updateAppoinsment(appointment);
  }

  @DeleteMapping("/deleteappointment")
  public Map<String, Object> deleteAppointment(@RequestParam (name = "id") long id)
      throws AccessDeniedException {
    return appointmentService.deleteAppointment(id);
  }
}
