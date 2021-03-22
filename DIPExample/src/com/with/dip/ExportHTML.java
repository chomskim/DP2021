package com.with.dip;

import java.io.File;
import java.util.List;

public class ExportHTML implements IExportData {

	public File exportData(List<Object[]> listData){
		File outputHTML = null;
		//Logic to iterate the listData and generate HTML File
        System.out.println(this.getClass().getName());
		return outputHTML;
	}
}
