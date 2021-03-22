package com.with.dip;

import java.util.ArrayList;
import java.util.List;

public class FetchDB implements IFetchData{
	
	public List<Object[]> fetchData(){
        List<Object[]> dataFromDB = new ArrayList<Object[]>();
        //Logic to call database, execute a query and fetch the data
        System.out.println(this.getClass().getName());
        return dataFromDB;
    }
}
