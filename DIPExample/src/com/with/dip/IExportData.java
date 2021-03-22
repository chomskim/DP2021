package com.with.dip;

import java.io.File;
import java.util.List;

public interface IExportData {
	
	//Common interface method to export data. 
    File exportData(List<Object[]> listData);
}
