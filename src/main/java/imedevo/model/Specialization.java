package imedevo.model;

/**
 * Specialization for (@link Doctor) class.
 */

public enum Specialization {

  OBSTETRICIAN(1, "Акушер"),
  OBSTETRICIAN_GYNECOLOGIST(2, "Акушер - гинеколог"),
  ALLERGIST(3,"Аллерголог");

  private int specializationNumber;
  private String specializationName;


  Specialization(int specializationNumber, String specializationName) {
    this.specializationName = specializationName;
    Specialization.setSpecializationNumber(specializationNumber);
  }

  public String getSpecializationName() {
    return specializationName;
  }

  public void setSpecializationName(String specializationName) {
    this.specializationName = specializationName;
  }

  public int getSpecializationNumber() {
    return specializationNumber;
  }

  public static void setSpecializationNumber(int specializationNumber) {
    specializationNumber = specializationNumber;
  }

//  public Specialization createSpecialization(String s) {
//    switch (s) {
//      case (specializationName.equals("Акушер")):
//        return OBSTETRICIAN;
//      case 2:
//        return OBSTETRICIAN_GYNECOLOGIST;
//      default:
//        return ALLERGIST;
//    }
//  }
}