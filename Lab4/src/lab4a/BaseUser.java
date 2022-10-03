package lab4a;

import java.util.Random;

public class BaseUser {
    private String firstName;
    private String lastName;
    private String fatherName;
    private String phoneNumber;

    public BaseUser(String firstName, String lastName, String fatherName, String phoneNumber){
        this.firstName=firstName;
        this.lastName=lastName;
        this.fatherName=fatherName;
        this.phoneNumber=phoneNumber;
    }

    public BaseUser(){
        this.firstName=generateString(97,122);
        this.lastName=generateString(97,122);
        this.fatherName=generateString(97,122);
        this.phoneNumber=generateString(48,57);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String generateString(int left,int right){
        // letter 'a' is 97
        // letter 'z' is 122
        // number '0' is 48
        // number '9' is 57

        int leftLimit = left;
        int rightLimit = right;
        int targetStringLength = 5;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    public String getFullName(){
        return (firstName+" "+fatherName+" "+lastName);
    }
}
