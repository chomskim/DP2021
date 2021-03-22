package com.with.dip;

import java.util.ArrayList;
import java.util.List;

public class FetchWeb implements IFetchData {
	
	public List<Object[]> fetchData(){
        List<Object[]> dataFromWebService = new ArrayList<Object[]>();
       //Logic to call Web Service and fetch the data and return it. 
        System.out.println(this.getClass().getName());
       return dataFromWebService;
    }
}
