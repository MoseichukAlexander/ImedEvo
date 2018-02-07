package imedevo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * For unification which(@link Doctor) working in (@link Clinic) classes.
 */

@Entity
@Table(name = "doctor_clinics")
public class DoctorClinic {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;
  @Column(name = "doctor_id")
  private long doctorId;
  @Column(name = "clinic_id")
  private long clinicId;

  public DoctorClinic(){

  }

  public DoctorClinic(long doctorId, long clinicId) {
    this.doctorId = doctorId;
    this.clinicId = clinicId;
  }

  public long getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(long doctorId) {
    this.doctorId = doctorId;
  }

  public long getClinicId() {
    return clinicId;
  }

  public void setClinicId(long clinicId) {
    this.clinicId = clinicId;
  }

}
