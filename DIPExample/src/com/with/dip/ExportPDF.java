package com.with.dip;

import java.io.File;
import java.util.List;

public class ExportPDF implements IExportData{

	public File exportData(List<Object[]> dataLst){
		File pdfFile = null;
		//Logic to iterate the listData and generate PDF file
        System.out.println(this.getClass().getName());
		return pdfFile;
	}
}
