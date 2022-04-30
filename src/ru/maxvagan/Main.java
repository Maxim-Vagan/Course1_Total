package ru.maxvagan;

import java.util.Locale;

public class Main {
    private static clsGeneratorEmployee makerStaff = new clsGeneratorEmployee(25_000.00f, 150_000.00f);

    private static String financeSum(float inpSum, char thousandDelim, char decimalDelim){
        String[] result = String.format("%.2f", inpSum).replace('.', ',').split(",");
        return String.format(Locale.US, "%,d", Integer.valueOf(result[0])).replace(',', thousandDelim) + decimalDelim + result[1];
    }

    private static void showListOfStaff(clsEmployee[] inpBook) {
        for (clsEmployee employee:inpBook)
            System.out.println(employee);
    }

    private static void showListOfFIO(clsEmployee[] inpBook) {
        for (clsEmployee employee:inpBook)
            System.out.println(String.join(" ", Integer.toString(employee.getEntityId())+":",
                    employee.getSurname(),
                    employee.getName(),
                    employee.getLastname()));
    }

    private static void applyStuff(clsEmployee[] inpBook) {
        for (int i=0; i < inpBook.length; i++) {
            inpBook[i] = new clsEmployee(makerStaff.giveAnEmployeeName(),
                    makerStaff.giveAnEmployeeSurName(),
                    makerStaff.giveAnEmployeeLastName(),
                    makerStaff.assignDepartment(),
                    makerStaff.assignSalary());
        }
    }

    private static float getTotalAmountCostPerTeam(clsEmployee[] inpBook) {
        int resultSum = 0;
        for (clsEmployee employee:inpBook)
            resultSum += employee.getSalary();
        return resultSum;
    }

    private static clsEmployee getMaxSalaryEmployee(clsEmployee[] inpBook) {
        float controlSum = 0;
        int winnerID = 0;
        for (int i=0; i<inpBook.length;i++) {
            if (controlSum < inpBook[i].getSalary()) {
                winnerID = i;
                controlSum = inpBook[i].getSalary();
            }
        }
        return inpBook[winnerID];
    }

    private static clsEmployee getMinSalaryEmployee(clsEmployee[] inpBook) {
        float controlSum = Float.MAX_VALUE;
        int winnerID = 0;
        for (int i=0; i<inpBook.length;i++) {
            if (controlSum > inpBook[i].getSalary()) {
                winnerID = i;
                controlSum = inpBook[i].getSalary();
            }
        }
        return inpBook[winnerID];
    }

    private static float getAvgSalary(int inpStaffCount, float inpTotal) {
        if (inpStaffCount > 0)
            return inpTotal / inpStaffCount;
        else
            return 0.00f;
    }
    //-------------------------- Medium Level ----------------------------------
    private static float getIndexingSalary(float inpSalary, float increaseProc) {
        return (float)(inpSalary * (1 + increaseProc * 0.01));
    }

    private static void reindexingSalary(clsEmployee[] inpBook, float indexProc) {
        for (int i=0; i < inpBook.length; i++) {
            inpBook[i].setSalary(getIndexingSalary(inpBook[i].getSalary(), indexProc));
        }
    }

    private static void reindexingSalaryInSubDep(clsEmployee[] inpBook, short inpSubDep, float indexProc) {
        boolean isSubDepExists = false;
        for (int i=0; i < inpBook.length; i++) {
            isSubDepExists = inpSubDep == inpBook[i].getSubDepartment();
            if (isSubDepExists)
                inpBook[i].setSalary(getIndexingSalary(inpBook[i].getSalary(), indexProc));
        }
        if (!isSubDepExists) System.out.println("WARNING! In Company there is no subDepartment " + inpSubDep);
    }

    private static clsEmployee getMaxSalaryEmployeeInSubDep(clsEmployee[] inpBook, short inpSubDep) {
        float controlSum = 0;
        int winnerID = 0;
        boolean isSubDepExists = false;
        for (int i=0; i<inpBook.length;i++) {
            isSubDepExists = inpSubDep == inpBook[i].getSubDepartment();
            if (controlSum < inpBook[i].getSalary() && isSubDepExists) {
                winnerID = i;
                controlSum = inpBook[i].getSalary();
            }
        }
        if (isSubDepExists)
            return inpBook[winnerID];
        else
            return null;
    }

    private static clsEmployee getMinSalaryEmployeeInSubDep(clsEmployee[] inpBook, short inpSubDep) {
        float controlSum = Float.MAX_VALUE;
        int winnerID = 0;
        boolean isSubDepExists = false;
        for (int i=0; i<inpBook.length;i++) {
            isSubDepExists = inpSubDep == inpBook[i].getSubDepartment();
            if (controlSum > inpBook[i].getSalary() && isSubDepExists) {
                winnerID = i;
                controlSum = inpBook[i].getSalary();
            }
        }
        if (isSubDepExists)
            return inpBook[winnerID];
        else
            return null;
    }

    private static float getTotalAmountSalaryPerSubDep(clsEmployee[] inpBook, short inpSubDep) {
        int resultSum = 0;
        for (clsEmployee employee:inpBook){
            if (inpSubDep == employee.getSubDepartment()) resultSum += employee.getSalary();
        }
        return resultSum;
    }

    private static int countStaffInSubDep(clsEmployee[] inpBook, short inpSubDep) {
        int resultCount = 0;
        for (clsEmployee employee:inpBook)
            if (employee.getSubDepartment()==inpSubDep) resultCount+=1;
        return resultCount;
    }

    private static void showListOfStaffOfSubDep(clsEmployee[] inpBook, short inpSubDep) {
        String buffStr;
        for (clsEmployee employee:inpBook)
            if (employee.getSubDepartment() == inpSubDep){
                buffStr = employee.toString().replace(String.format("subDepartment=%s, ",inpSubDep),"");
                System.out.println(buffStr);
            }
    }

    private static void showWhoIsUnderLimit(clsEmployee[] inpBook, float inpLimitSum) {
        for (clsEmployee employee:inpBook)
            if (inpLimitSum > employee.getSalary())
                System.out.println(String.join(" ", Integer.toString(employee.getEntityId())+":",
                        employee.getSurname(),
                        employee.getName(),
                        employee.getLastname(),
                        "-",
                        financeSum(employee.getSalary(), '_', '.')
                        ));
    }

    private static void showWhoIsAboveLimit(clsEmployee[] inpBook, float inpLimitSum) {
        for (clsEmployee employee:inpBook)
            if (inpLimitSum <= employee.getSalary())
                System.out.println(String.join(" ", Integer.toString(employee.getEntityId())+":",
                        employee.getSurname(),
                        employee.getName(),
                        employee.getLastname(),
                        "-",
                        financeSum(employee.getSalary(), '_', '.')
                ));
    }

    public static void main(String[] args) {
        //Simple level
        System.out.println("-------------------------- Simple Level ----------------------------------");
        System.out.println("Initialize Team Staff");
        clsEmployee[] bookOfStaff = new clsEmployee[10];
        applyStuff(bookOfStaff);
        System.out.println("Show our Team Staff");
        showListOfStaff(bookOfStaff);
        float totalSumPerMonth = getTotalAmountCostPerTeam(bookOfStaff);
        clsEmployee personWithMaxSalary = getMaxSalaryEmployee(bookOfStaff);
        clsEmployee personWithMinSalary = getMinSalaryEmployee(bookOfStaff);
        float averageSalaryInTeam = getAvgSalary(bookOfStaff.length, totalSumPerMonth);
        System.out.println("Total Sum of salaries budget " + financeSum(totalSumPerMonth, '_', '.'));
        System.out.println("Worker with the Maximum Salary\r\n" + personWithMaxSalary);
        System.out.println("Worker with the Minimum Salary\r\n" + personWithMinSalary);
        System.out.println("Average Sum of salaries budget " + financeSum(averageSalaryInTeam, '_', '.'));
        System.out.println("List of FIOs");
        showListOfFIO(bookOfStaff);
        //Medium level
        System.out.println("-------------------------- Medium Level ----------------------------------");
        reindexingSalary(bookOfStaff, 5.0f);
        System.out.println("Salary of Staff after Re indexation + 5 %");
        showListOfStaff(bookOfStaff);
        short subDep = makerStaff.assignDepartment();
        totalSumPerMonth = getTotalAmountSalaryPerSubDep(bookOfStaff, subDep);
        System.out.println("Total Sum of salaries in subDepartment " + subDep + ": " + financeSum(totalSumPerMonth, '_', '.'));
        personWithMaxSalary = getMaxSalaryEmployeeInSubDep(bookOfStaff, subDep);
        personWithMinSalary = getMinSalaryEmployeeInSubDep(bookOfStaff, subDep);
        System.out.println("Worker with the Maximum Salary in subDepartment " + subDep + "\r\n" + personWithMaxSalary);
        System.out.println("Worker with the Minimum Salary in subDepartment " + subDep + "\r\n" + personWithMinSalary);
        averageSalaryInTeam = getAvgSalary(countStaffInSubDep(bookOfStaff, subDep), totalSumPerMonth);
        System.out.println("Average Sum of salaries in subDepartment " + subDep + ": " + financeSum(averageSalaryInTeam, '_', '.'));
        reindexingSalaryInSubDep(bookOfStaff, subDep, 10.0f);
        System.out.printf("Salary of Staff in subDepartment %s after Re indexation + 10 %s\r\n", subDep, "%");
        showListOfStaffOfSubDep(bookOfStaff, subDep);
        float limitSum = 45_151.87f;
        System.out.println("List of Staff Under " + financeSum(limitSum, '_', '.') + " limit:");
        showWhoIsUnderLimit(bookOfStaff, limitSum);
        System.out.println("List of Staff Above " + financeSum(limitSum, '_', '.') + " limit:");
        showWhoIsAboveLimit(bookOfStaff, limitSum);
        //High level
        System.out.println("-------------------------- High Level ----------------------------------");
        clsEmployeeBook emplBook = new clsEmployeeBook(bookOfStaff, (short)15);
        emplBook.addEmployeeToBook(makerStaff.giveAnEmployeeName(),
                makerStaff.giveAnEmployeeSurName(),
                makerStaff.giveAnEmployeeLastName(),
                makerStaff.assignDepartment(),
                makerStaff.assignSalary());
        emplBook.showListOfStaff();
        emplBook.deleteEmployeeFromBook("Иванов Петр Максимович", 10006);
        emplBook.showListOfStaff();
        emplBook.addEmployeeToBook(makerStaff.giveAnEmployeeName(),
                makerStaff.giveAnEmployeeSurName(),
                makerStaff.giveAnEmployeeLastName(),
                makerStaff.assignDepartment(),
                makerStaff.assignSalary());
        emplBook.showListOfStaff();
        int randomIndex = (int) (Math.round(Math.random() * 14));
        while (emplBook.getBookOfStaff()[randomIndex]==null) {
            randomIndex = (int) (Math.round(Math.random() * 14));
        }
        String fioForSearch = String.join( " ",
                emplBook.getBookOfStaff()[randomIndex].getSurname(),
                emplBook.getBookOfStaff()[randomIndex].getName(),
                emplBook.getBookOfStaff()[randomIndex].getLastname()
                );
        emplBook.changeEmployeeData(fioForSearch, (short)3, 200001.23f);
        emplBook.showGroupsOfSubDepsWithStaff();
    }
}
