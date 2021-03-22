package com.with.dip;

import java.util.List;

public class Main {

	private IExportData exportObj= null;
	private IFetchData fetchObj= null;

	//Set the fetch data object from outside of this class.
	public void configFetch(IFetchData actualFetch){
		this.fetchObj = actualFetch;
	}
	//Set the export data object from outside of this class.
	public void configExport(IExportData actualExport){
		this.exportObj = actualExport;
	}

	public Object generateBalanceSheet(){
		List<Object[]> dataLst = fetchObj.fetchData();
		//
		return exportObj.exportData(dataLst);
	}
    public static void main (String[] args) {
    	Main bs = new Main();
    	bs.configFetch(new FetchDB());
    	bs.configExport(new ExportPDF());
    	bs.generateBalanceSheet();
    }

}
