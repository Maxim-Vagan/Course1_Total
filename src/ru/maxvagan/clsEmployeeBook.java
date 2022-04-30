package ru.maxvagan;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class clsEmployeeBook {
    private clsEmployee[] bookOfStaff;
    private Short employeeCount;
    private int lastEntityId;

    public clsEmployeeBook(clsEmployee[] bookOfStaff, Short employeeCount) {
        this.employeeCount = employeeCount;
        this.bookOfStaff = new clsEmployee[employeeCount];
        for (int i = 0; i < bookOfStaff.length; i++) {
            this.bookOfStaff[i] = bookOfStaff[i];
            lastEntityId = bookOfStaff[i].getEntityId();
        }
    }

    public clsEmployee[] getBookOfStaff() {
        return bookOfStaff;
    }

    public Short getEmployeeCount() {
        return employeeCount;
    }

    private String financeSum(float inpSum, char thousandDelim, char decimalDelim){
        String[] result = String.format("%.2f", inpSum).replace('.', ',').split(",");
        return String.format(Locale.US, "%,d", Integer.valueOf(result[0])).replace(',', thousandDelim) + decimalDelim + result[1];
    }

    public void showListOfStaff() {
        for (clsEmployee employee:bookOfStaff)
            System.out.println(employee);
    }

    public void showListOfFIO() {
        for (clsEmployee employee:bookOfStaff)
            System.out.println(String.join(" ", Integer.toString(employee.getEntityId())+":",
                    employee.getSurname(),
                    employee.getName(),
                    employee.getLastname()));
    }

    public float getTotalAmountCostPerTeam() {
        int resultSum = 0;
        for (clsEmployee employee:bookOfStaff)
            resultSum += employee.getSalary();
        return resultSum;
    }

    public clsEmployee getMaxSalaryEmployee() {
        float controlSum = 0;
        int winnerID = 0;
        for (int i=0; i<bookOfStaff.length;i++) {
            if (controlSum < bookOfStaff[i].getSalary()) {
                winnerID = i;
                controlSum = bookOfStaff[i].getSalary();
            }
        }
        return bookOfStaff[winnerID];
    }

    public clsEmployee getMinSalaryEmployee() {
        float controlSum = Float.MAX_VALUE;
        int winnerID = 0;
        for (int i=0; i<bookOfStaff.length;i++) {
            if (controlSum > bookOfStaff[i].getSalary()) {
                winnerID = i;
                controlSum = bookOfStaff[i].getSalary();
            }
        }
        return bookOfStaff[winnerID];
    }

    public float getAvgSalary(int inpStaffCount, float inpTotal) {return inpTotal / inpStaffCount;}

    private float getIndexingSalary(float inpSalary, float increaseProc) {
        return (float)(inpSalary * (1 + increaseProc * 0.01));
    }

    public void reindexingSalary(float indexProc) {
        for (int i=0; i < bookOfStaff.length; i++) {
            bookOfStaff[i].setSalary(getIndexingSalary(bookOfStaff[i].getSalary(), indexProc));
        }
    }

    public void reindexingSalaryInSubDep(short inpSubDep, float indexProc) {
        for (int i=0; i < bookOfStaff.length; i++) {
            if (inpSubDep == bookOfStaff[i].getSubDepartment())
                bookOfStaff[i].setSalary(getIndexingSalary(bookOfStaff[i].getSalary(), indexProc));
        }
    }

    public clsEmployee getMaxSalaryEmployeeInSubDep(short inpSubDep) {
        float controlSum = 0;
        int winnerID = 0;
        for (int i=0; i<bookOfStaff.length;i++) {
            if (controlSum < bookOfStaff[i].getSalary() && inpSubDep == bookOfStaff[i].getSubDepartment()) {
                winnerID = i;
                controlSum = bookOfStaff[i].getSalary();
            }
        }
        return bookOfStaff[winnerID];
    }

    public clsEmployee getMinSalaryEmployeeInSubDep(short inpSubDep) {
        float controlSum = Float.MAX_VALUE;
        int winnerID = 0;
        for (int i=0; i<bookOfStaff.length;i++) {
            if (controlSum > bookOfStaff[i].getSalary() && inpSubDep == bookOfStaff[i].getSubDepartment()) {
                winnerID = i;
                controlSum = bookOfStaff[i].getSalary();
            }
        }
        return bookOfStaff[winnerID];
    }

    public float getTotalAmountSalaryPerSubDep(short inpSubDep) {
        int resultSum = 0;
        for (clsEmployee employee:bookOfStaff)
            if (employee.getSubDepartment()==inpSubDep) resultSum += employee.getSalary();
        return resultSum;
    }

    private int countStaffInSubDep(short inpSubDep) {
        int resultCount = 0;
        for (clsEmployee employee:bookOfStaff)
            if (employee.getSubDepartment()==inpSubDep) resultCount+=1;
        return resultCount;
    }

    public void showListOfStaffOfSubDep(short inpSubDep) {
        String buffStr;
        for (clsEmployee employee:bookOfStaff)
            if (employee != null) {
                if (employee.getSubDepartment() == inpSubDep){
                    buffStr = employee.toString().replace(String.format("subDepartment=%s, ",inpSubDep),"");
                    System.out.println(buffStr);
                }
            }
    }

    public void showWhoIsUnderLimit(float inpLimitSum) {
        for (clsEmployee employee:bookOfStaff)
            if (inpLimitSum > employee.getSalary())
                System.out.println(String.join(" ", Integer.toString(employee.getEntityId())+":",
                        employee.getSurname(),
                        employee.getName(),
                        employee.getLastname(),
                        "-",
                        financeSum(employee.getSalary(), '_', '.')
                ));
    }

    public void showWhoIsAboveLimit(float inpLimitSum) {
        for (clsEmployee employee:bookOfStaff)
            if (inpLimitSum <= employee.getSalary())
                System.out.println(String.join(" ", Integer.toString(employee.getEntityId())+":",
                        employee.getSurname(),
                        employee.getName(),
                        employee.getLastname(),
                        "-",
                        financeSum(employee.getSalary(), '_', '.')
                ));
    }

    public void addEmployeeToBook(String inpName, String inpSurName, String inpLastName, short inpSubDep, float inpSalary) {
        clsEmployee worker = new clsEmployee(inpName, inpSurName, inpLastName, inpSubDep, inpSalary);
        for (int i = 0; i < bookOfStaff.length; i++) {
            if (bookOfStaff[i] == null) {
                bookOfStaff[i] = worker;
                break;
            }
        }
    }

    public void deleteEmployeeFromBook(String inpFIO, int inpEntity) {
        boolean isCandidateToFire = false;
        for (int i = 0; i < bookOfStaff.length; i++) {
            isCandidateToFire = String.join(" ", bookOfStaff[i].getSurname(),
                    bookOfStaff[i].getName(),
                    bookOfStaff[i].getLastname()).equals(inpFIO) || bookOfStaff[i].getEntityId() == inpEntity;
            if (isCandidateToFire) {
                bookOfStaff[i] = null;
                System.out.println("Employee was fired!");
                break;
            }
        }
    }

    public void changeEmployeeData(String inpFIO, Short inpSubDep, float inpSalary) {
        boolean isEmployeeFound = false;
        short subDep = 1;
        for (clsEmployee employee : bookOfStaff) {
            if (employee != null) {
                isEmployeeFound = String.join(" ", employee.getSurname(),
                        employee.getName(),
                        employee.getLastname()).equals(inpFIO);
                if (isEmployeeFound) {
                    while (subDep==employee.getSubDepartment()) {
                        subDep = (short) (1 + Math.round(Math.random() * 4));
                    }
                    employee.setSubDepartment(subDep);
                    employee.setSalary(inpSalary);
                }
            }
        }
    }

    public void showGroupsOfSubDepsWithStaff() {
        for (int i = 1; i < 6; i++) {
            System.out.println("SubDepartment № " + i);
            showListOfStaffOfSubDep((short) i);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof clsEmployeeBook)) return false;
        clsEmployeeBook that = (clsEmployeeBook) o;
        return Arrays.equals(getBookOfStaff(), that.getBookOfStaff()) && getEmployeeCount().equals(that.getEmployeeCount());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getEmployeeCount());
        result = 31 * result + Arrays.hashCode(getBookOfStaff());
        return result;
    }

    @Override
    public String toString() {
        return "clsEmployeeBook{" +
                "employeeCount=" + employeeCount +
                ", bookOfStaff=" + Arrays.toString(bookOfStaff) +
                '}';
    }
}
