package com.test.Test.Array;
import java.util.Arrays;
import java.util.Comparator;

public class CompanyArray {
	public static void main(String[] args) {
        Company[] array = {
                new Company("Oneplus", "China"),
                new Company("Samsung", "South Korea"),
                new Company("Nothing", "India")
        };

        Arrays.sort(array, Comparator.comparing(Company::getCompany));

        for (Company company : array) {
            System.out.println(company);
        }
    }
}

class Company {
    private String company;
    private String manufacturing;

    public Company(String company, String manufacturing) {
        this.company = company;
        this.manufacturing = manufacturing;
    }

    public String getCompany() {
        return company;
    }

    public String getManufacturing() {
        return manufacturing;
    }

    @Override
    public String toString() {
        return "Company{" +
                "company='" + company + '\'' +
                ", manufacturing='" + manufacturing + '\'' +
                '}';
    }
}
