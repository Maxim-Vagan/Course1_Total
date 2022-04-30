package ru.maxvagan;

import org.w3c.dom.css.Counter;

import java.util.Locale;

public class clsEmployee {
    private final String name;
    private final String surname;
    private final String lastname;
    private short subDepartment;
    private float salary;
    private final int entityId;
    static int counter = 10000;

    public clsEmployee(String name, String surname, String lastname, short subDepartment, float salary) {
        counter++;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        setSubDepartment(subDepartment);
        setSalary(salary);
        entityId = counter;
    }

    private String financeSum(float inpSum, char thousandDelim, char decimalDelim){
        String[] result = String.format("%.2f", inpSum).replace('.', ',').split(",");
        return String.format(Locale.US, "%,d", Integer.valueOf(result[0])).replace(',', thousandDelim) + decimalDelim + result[1];
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLastname(){
        return lastname;
    }

    public short getSubDepartment() {
        return subDepartment;
    }

    public float getSalary() {
        return salary;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setSubDepartment(short subDepartment) {
        this.subDepartment = subDepartment;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "clsEmployee{" +
                "entityId=" + entityId +
                ", name=\"" + name + "\"" +
                ", surname=\"" + surname + "\"" +
                ", lastname=\"" + lastname + "\"" +
                ", subDepartment=" + subDepartment +
                ", salary=" + financeSum(salary, '_', '.') +
                "}";
    }
}
