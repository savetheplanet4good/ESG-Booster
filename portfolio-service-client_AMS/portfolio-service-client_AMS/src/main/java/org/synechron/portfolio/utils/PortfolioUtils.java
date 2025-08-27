package org.synechron.portfolio.utils;

import org.synechron.esg.model.Company;
import org.synechron.esg.model.CompanyESGScore;
import org.synechron.esg.model.Portfolio;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * The type Portfolio utils.
 */
public class PortfolioUtils {

    private PortfolioUtils() {
        throw new IllegalStateException("PortfolioUtils class");
    }

    private static DecimalFormat df = new DecimalFormat("##.00");

    /**
     * Format double double.
     *
     * @param inputValue the input value
     * @return the double
     */
    public static Double formatDouble(Double inputValue) {
        Double outputValue = Double.parseDouble(df.format(0));
        if (inputValue != null)
            outputValue = Double.parseDouble(df.format(inputValue));

        return outputValue;
    }

    /**
     * Combination util list.
     *
     * @param arr          the arr
     * @param data         the data
     * @param start        the start
     * @param end          the end
     * @param index        the index
     * @param r            the r
     * @param responseList the response list
     * @return the list
     */
    /* arr[]  ---> Input Array
        data[] ---> Temporary array to store current combination
        start & end ---> Staring and Ending indexes in arr[]
        index  ---> Current index in data[]
        r ---> Size of a combination
        responseList  --> response (initially it is blank)
        */
    static List<List<String>> combinationUtil(String[] arr, String[] data, int start, int end, int index, int r,List<List<String>> responseList) {

        List<List<String>> response = new CopyOnWriteArrayList<>();
        List<String> listOfString = new ArrayList<>();
        if (index == r) {
            for (int j=0; j<r; j++)
                listOfString.add(data[j]);

            response.add(listOfString);

            return response;
        }

        for (int i=start; i<=end && end-i+1 >= r-index; i++) {
            data[index] = arr[i];
            List<List<String>> responseObj = combinationUtil(arr, data, i+1, end, index+1, r,responseList);
            if(responseObj != null){
                responseObj.forEach(responseList::add);
            }
        }
        return responseList;
    }

    /**
     * Get combination set.
     *
     * @param arr the arr
     * @param n   the n
     * @param r   the r
     * @return the set
     */
    // The main function that gets all combinations of size r
    public static Set<List<String>> getCombination(String[] arr, int n, int r){

        // A temporary array to store all combination one by one
        String[] data=new String[r];
        List<List<String>> response = new CopyOnWriteArrayList<>();

        // get all combination using temporary array 'data[]'
        List<List<String>> resp =combinationUtil(arr, data, 0, n-1, 0, r,response);
        Set<List<String>> setResponse = new HashSet<>();
        resp.forEach(responseObj ->setResponse.add(responseObj.stream().sorted().collect(Collectors.toList())));

        return setResponse;
    }

    /**
     * Get isin list list.
     *
     * @param companyESGScoreList the company esg score list
     * @return the list
     */
    public static List<String> getISINList(List<CompanyESGScore> companyESGScoreList){

        return companyESGScoreList.stream().map(CompanyESGScore::getIsin).collect(Collectors.toList());
    }

    /**
     * Gets isin list.
     *
     * @param portfolio the portfolio
     * @return the isin list
     */
    public static List<String> getISINList(Portfolio portfolio) {

        return portfolio.getCompanies().stream().map(Company::getIsin).collect(Collectors.toList());
    }

    /**
     * Gets names list.
     *
     * @param companies the companies
     * @return the names list
     */
    public static List<String> getNamesList(List<Company> companies) {

        return companies.stream().map(Company::getName).collect(Collectors.toList());
    }

    /**
     * Gets non matching companies.
     *
     * @param portfolio        the portfolio
     * @param isinsListToMatch the isins list to match
     * @return the non matching companies
     */
    public static List<Company> getNonMatchingCompanies(Portfolio portfolio, List<String> isinsListToMatch) {

        //Check if isin is correct or not present in the investable universe response
        List<Company> nonMatchingCompanies;
        List<String> portfolioCompaniesISIN = PortfolioUtils.getISINList(portfolio);
        List<String> nonMatchingIsin = portfolioCompaniesISIN.parallelStream().filter(searchData -> isinsListToMatch.parallelStream().noneMatch(searchData::equals)).collect(Collectors.toList());

        // for non matching isin try to get the record using the name of the company
        nonMatchingCompanies = portfolio.getCompanies().stream().filter(company -> nonMatchingIsin.contains(company.getIsin()))
                .collect(Collectors.toList());

        return nonMatchingCompanies;
    }
}
