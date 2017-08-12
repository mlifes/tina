package com.luanscn.tina.base.service.impl;

import java.util.List;

import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.luanscn.tina.base.service.HSSFWorkbookService;

public class HSSFWorkbookServiceImpl implements HSSFWorkbookService{

	@Override
	public void createHeader(HSSFWorkbook wb, HSSFSheet sheet,
			HSSFCellStyle style, HSSFRow row, HSSFCell cell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createBody(HSSFRow row, List<T> t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createHeader(HSSFHeader header, String left, String center,
			String right) {
		header.setCenter(center);
		header.setLeft(left);
		header.setRight(right);
	}

}
