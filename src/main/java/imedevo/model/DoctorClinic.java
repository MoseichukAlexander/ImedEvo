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
  private long doctorID;
  @Column(name = "clinic_id")
  private long clinicId;

  public DoctorClinic(){

  }

  public DoctorClinic(long doctorID, long clinicId) {
    this.doctorID = doctorID;
    this.clinicId = clinicId;
  }

  public long getDoctorID() {
    return doctorID;
  }

  public void setDoctorID(long doctorID) {
    this.doctorID = doctorID;
  }

  public long getClinicId() {
    return clinicId;
  }

  public void setClinicId(long clinicId) {
    this.clinicId = clinicId;
  }

}
