package com.luanscn.tina.base.service;

import java.util.List;

import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.luanscn.tina.base.service.base.BaseService;

/**
 * apache poi HSSFWorkbook 服务类的借口函数
 * */
public interface HSSFWorkbookService extends BaseService{
	
	/**
	 * @param wb HSSFWorkbook
	 * @param sheet HSSFSheet
	 * @param style HSSFCellStyle
	 * @param row HSSFRow
	 * @param cell HSSFCell
	 * */
	public void createHeader(HSSFWorkbook wb ,HSSFSheet sheet ,HSSFCellStyle style ,HSSFRow row , HSSFCell cell);
	/**
	 * @param row HSSFRow
	 * @param t List<T>
	 * */
	public void createBody(HSSFRow row , List<T> t);
	
	/**
	 * @param header HSSFHeader
	 * @param left String
	 * @param center String
	 * @param right String
	 * */
	public void createHeader(HSSFHeader header,String left,String center ,String right);
	
}
