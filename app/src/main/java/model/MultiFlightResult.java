package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rutvik on 09-09-2016 at 01:44 PM.
 */

public class MultiFlightResult
{

    private String publishedFair="";

    private final List<SingleMultiFlightResult> singleMultiFlightResultList;

    public MultiFlightResult()
    {
        singleMultiFlightResultList = new ArrayList<>();
    }

    public void addSingleMultiFlightResult(SingleMultiFlightResult singleMultiFlightResult)
    {
        singleMultiFlightResultList.add(singleMultiFlightResult);
    }

    public List<SingleMultiFlightResult> getSingleMultiFlightResultList()
    {
        return singleMultiFlightResultList;
    }

    public String getPublishedFair()
    {
        return publishedFair;
    }

    public void setPublishedFair(String publishedFair)
    {
        this.publishedFair = publishedFair;
    }
}
