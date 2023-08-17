package com.test.util;

import com.test.entities.FiddlerTemplate;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;

public class CreatAndReadExcel {
	/**
	 * 创建2007版Excel文件
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void creat2007Excel() throws FileNotFoundException, IOException {
		// HSSFWorkbook workBook = new HSSFWorkbook();// 创建 一个excel文档对象
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet();// 创建一个工作薄对象
		sheet.setColumnWidth(1, 10000);// 设置第二列的宽度为
		XSSFRow row = sheet.createRow(1);// 创建一个行对象
		row.setHeightInPoints(23);// 设置行高23像素
		XSSFCellStyle style = workBook.createCellStyle();// 创建样式对象

		// 设置字体

		XSSFFont font = workBook.createFont();// 创建字体对象

		font.setFontHeightInPoints((short) 15);// 设置字体大小

		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体

		font.setFontName("黑体");// 设置为黑体字

		style.setFont(font);// 将字体加入到样式对象

		// 设置对齐方式

		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中

		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中

		// 设置边框

		style.setBorderTop(HSSFCellStyle.BORDER_THICK);// 顶部边框粗线

		style.setTopBorderColor(HSSFColor.RED.index);// 设置为红色

		style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);// 底部边框双线

		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);// 左边边框

		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);// 右边边框

		// 格式化日期

		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

		XSSFCell cell = row.createCell(1);// 创建单元格

		cell.setCellValue(new Date());// 写入当前日期

		cell.setCellStyle(style);// 应用样式对象

		// 文件输出流

		FileOutputStream os = new FileOutputStream("style_2007.xlsx");

		workBook.write(os);// 将文档对象写入文件输出流

		os.close();// 关闭文件输出流
		System.out.println("创建成功 office 2007 excel");
	}

	/**
	 * 创建2003版本的Excel文件
	 */
	private static void creat2003Excel() throws FileNotFoundException, IOException {
		HSSFWorkbook workBook = new HSSFWorkbook();// 创建 一个excel文档对象

		HSSFSheet sheet = workBook.createSheet();// 创建一个工作薄对象

		sheet.setColumnWidth(1, 10000);// 设置第二列的宽度为

		HSSFRow row = sheet.createRow(1);// 创建一个行对象

		row.setHeightInPoints(23);// 设置行高23像素

		HSSFCellStyle style = workBook.createCellStyle();// 创建样式对象

		// 设置字体

		HSSFFont font = workBook.createFont();// 创建字体对象

		font.setFontHeightInPoints((short) 15);// 设置字体大小

		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体

		font.setFontName("黑体");// 设置为黑体字

		style.setFont(font);// 将字体加入到样式对象

		// 设置对齐方式

		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中

		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中

		// 设置边框

		style.setBorderTop(HSSFCellStyle.BORDER_THICK);// 顶部边框粗线

		style.setTopBorderColor(HSSFColor.RED.index);// 设置为红色

		style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);// 底部边框双线

		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);// 左边边框

		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);// 右边边框

		// 格式化日期

		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

		HSSFCell cell = row.createCell(1);// 创建单元格

		cell.setCellValue(new Date());// 写入当前日期

		cell.setCellStyle(style);// 应用样式对象

		// 文件输出流

		FileOutputStream os = new FileOutputStream("style_2003.xls");

		workBook.write(os);// 将文档对象写入文件输出流

		os.close();// 关闭文件输出流
		System.out.println("创建成功 office 2003 excel");
	}

//	/**
//	 * 对外提供读取excel 的方法
//	 *
//	 * @throws Exception
//	 */
//	public static List<TestCaseBean> readExcel(File file) throws Exception {
//		String fileName = file.getName();
//		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
//		if ("xls".equals(extension)) {
//			// return read2003Excel(file);
//			throw new IOException("不支持2003xls");
//		} else if ("xlsx".equals(extension)) {
//			return read2007Excel(file);
//		} else {
//			throw new IOException("不支持的文件类型");
//		}
//	}

	/**
	 * 对外提供读取excel 的方法
	 * 
	 * @throws Exception
	 */
	public static List<List<Object>> readExcel(File file) throws Exception {
		String fileName = file.getName();
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(extension)) {
			return read2003Excel(file);
		} else if ("xlsx".equals(extension)) {
			return read2007Excel(file);
		} else {
			throw new IOException("不支持的文件类型");
		}
	}

	/**
	 * 读取Office 2007 excel
	 */
	public static List<FiddlerTemplate> read2007ExcelWithFiddlerTemplate(File file) throws Exception {

		List<FiddlerTemplate> list = new LinkedList<FiddlerTemplate>();
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = xwb.getSheetAt(0);
		XSSFRow row = null;
		XSSFCell cell = null;
		DecimalFormat df = new DecimalFormat("0");

		for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			// List<Object> linked = new LinkedList<Object>();
			FiddlerTemplate ft = new FiddlerTemplate();
			cell = row.getCell(2);
			if (cell == null) {
				continue;
			} else if (cell.toString().equalsIgnoreCase("enable")) {

//				linked.add(row.getRowNum());
//				linked.add(row.getCell(5).toString().trim());
//				linked.add(row.getCell(7).toString().trim());
//				linked.add(df.format(row.getCell(0).getNumericCellValue()));
//				linked.add(row.getCell(10).toString().trim());
//				linked.add((int)row.getCell(4).getNumericCellValue());

			} else {
				// continue;

				// excel templage:# Result Protocol Host URL Body Caching Content-Type
				// RequestMethod head
//				linked.add(row.getCell(0).toString().trim());
//				String postUrl = "";
//				postUrl = row.getCell(2).toString().trim() + ":\\" + row.getCell(3).toString().trim() + row.getCell(4).toString().trim();
//				linked.add(postUrl);
//				linked.add(row.getCell(7).toString().trim());
//				linked.add(row.getCell(8).toString().trim());
//				linked.add(row.getCell(9).toString().trim());

				if (row.getCell(0) != null) {
					//ft.setId(row.getCell(0).toString().trim());
					ft.setId(Long.valueOf(row.getCell(0).toString().trim()));
				} else {
					//ft.setId("");
				}

				if (row.getCell(2) != null) {
					ft.setProtocol(row.getCell(2).toString().trim());
				} else {
					ft.setProtocol("");
				}
				if (row.getCell(3) != null) {
					ft.setHost(row.getCell(3).toString().trim());
				} else {
					ft.setHost("");
				}
				if (row.getCell(4) != null) {
					ft.setUrl(row.getCell(4).toString().trim());
				} else {
					ft.setUrl("");
				}
				if (row.getCell(7) != null) {
					ft.setContentType(row.getCell(7).toString().trim());
				} else {
					ft.setContentType("");
				}
				if (row.getCell(8) != null) {
					ft.setRequestMethod(row.getCell(8).toString().trim());
				} else {
					ft.setRequestMethod("");
				}
				if (row.getCell(9) != null) {
					ft.setHead(row.getCell(9).toString().trim());
				} else {
					ft.setHead("");
				}
				ft.setFullUrl(ft.getProtocol() + "://" + ft.getHost() + ft.getUrl());
			}
			list.add(ft);
		}
		xwb.close();
		return list;
	}

	/**
	 * 读取Office 2007 excel
	 */
	private static List<List<Object>> read2007Excel(File file) throws Exception {

		List<List<Object>> list = new LinkedList<List<Object>>();
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = xwb.getSheetAt(0);
		XSSFRow row = null;
		XSSFCell cell = null;
		DecimalFormat df = new DecimalFormat("0");

		for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<Object> linked = new LinkedList<Object>();

			cell = row.getCell(2);
			if (cell == null) {
				continue;
			} else if (cell.toString().equalsIgnoreCase("enable")) {

				linked.add(row.getRowNum());
				linked.add(row.getCell(5).toString().trim());
				linked.add(row.getCell(7).toString().trim());
				linked.add(df.format(row.getCell(0).getNumericCellValue()));
				linked.add(row.getCell(10).toString().trim());
				linked.add((int) row.getCell(4).getNumericCellValue());

			} else {
				// continue;

				// excel templage:# Result Protocol Host URL Body Caching Content-Type
				// RequestMethod body
				linked.add(row.getCell(0).toString().trim());
				String postUrl = "";
				postUrl = row.getCell(2).toString().trim() + ":\\" + row.getCell(3).toString().trim()
						+ row.getCell(4).toString().trim();
				linked.add(postUrl);
//				linked.add(row.getCell(2).toString().trim());
//				linked.add(row.getCell(3).toString().trim());
//				linked.add(row.getCell(4).toString().trim());
				linked.add(row.getCell(7).toString().trim());
				linked.add(row.getCell(8).toString().trim());
				linked.add(row.getCell(9).toString().trim());

			}
			list.add(linked);
		}
		xwb.close();
		return list;
	}

	/**
	 * 读取 office 2003 excel
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static List<List<Object>> read2003Excel(File file) throws IOException {
		List<List<Object>> list = new LinkedList<List<Object>>();
		HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = hwb.getSheetAt(0);
		Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		System.out.println("读取office 2003 excel内容如下：");
		for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<Object> linked = new LinkedList<Object>();
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");// 格式化 number String
				// 字符
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
				DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					// System.out.println(i + "行" + j + " 列 is String type");
					value = cell.getStringCellValue();
					System.out.print("  " + value + "  ");
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					// System.out.println(i + "行" + j
					// + " 列 is Number type ; DateFormt:"
					// + cell.getCellStyle().getDataFormatString());
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());

					} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
					}
					System.out.print("  " + value + "  ");
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					// System.out.println(i + "行" + j + " 列 is Boolean type");
					value = cell.getBooleanCellValue();
					System.out.print("  " + value + "  ");
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					// System.out.println(i + "行" + j + " 列 is Blank type");
					value = "";
					System.out.print("  " + value + "  ");
					break;
				default:
					// System.out.println(i + "行" + j + " 列 is default type");
					value = cell.toString();
					System.out.print("  " + value + "  ");
				}
				if (value == null || "".equals(value)) {
					continue;
				}
				linked.add(value);

			}
			list.add(linked);
		}
		hwb.close();
		return list;
	}

//	/**
//	 * 读取Office 2007 excel
//	 */
//	public static List<TestCaseBean> read2007Excel(File file) throws Exception {
//		List<TestCaseBean> list = new LinkedList<TestCaseBean>();
//		@SuppressWarnings("resource")
//		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
//		XSSFSheet sheet = xwb.getSheetAt(0);
//		XSSFRow row = null;
//		XSSFCell cell = null;
//		DecimalFormat df = new DecimalFormat("0");
//		TestCaseBean aeb = null;
//		for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {
//			aeb = new TestCaseBean();
//			row = sheet.getRow(i);
//			if (row == null) {
//				continue;
//			}
//
//			if (row.getCell(0) != null)
//				aeb.setA(row.getCell(0).toString());
//			else
//				aeb.setA("");
//			if (row.getCell(1) != null)
//				aeb.setB(row.getCell(1).toString());
//			else
//				aeb.setB("");
//			if (row.getCell(2) != null)
//				aeb.setC(row.getCell(2).toString());
//			else
//				aeb.setC("");
//			if (row.getCell(3) != null)
//				aeb.setD(row.getCell(3).toString());
//			else
//				aeb.setD("");
//			if (row.getCell(4) != null) {
//				aeb.setE(row.getCell(4).toString());
//				//System.out.println(aeb.getE());
//			} else {
//				aeb.setE("");
//			}
//			if (row.getCell(5) != null) {
//				aeb.setF(row.getCell(5).toString());
//			} else {
//				aeb.setF("");
//			}
//			if (row.getCell(6) != null)
//				aeb.setG(row.getCell(6).toString());
//			else
//				aeb.setG("");
//			if (row.getCell(7) != null)
//				aeb.setI(row.getCell(7).toString());
//			else
//				aeb.setI("");
//			if (row.getCell(8) != null)
//				aeb.setJ(row.getCell(8).toString());
//			else
//				aeb.setJ("");
//			if (row.getCell(9) != null)
//				aeb.setK(row.getCell(9).toString());
//			else
//				aeb.setK("");
//
//			// List<Object> linked = new LinkedList<Object>();
//			// for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum
//			// // 是获取最后一个不为空的列是第几个
//			// if (row.getCell(k) != null) { // getCell 获取单元格数据
//			// System.out.println(row.getCell(k) + "^^^^");
//			// //linked.add(row.getCell(k));
//			// }
//			// }
//
//			// list.add(linked);
//			list.add(aeb);
//		}
//		return list;
//	}

	// @Autowired
	public static void modifyExcel(File file, String value, int rowNum, int resultColumnNum) {
		try {
			// 传入的文件
			FileInputStream fileInput = new FileInputStream(file);
			// poi包下的类读取excel文件

			// 创建一个webbook，对应一个Excel文件
			XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
			// 对应Excel文件中的sheet，0代表第一个
			XSSFSheet sh = workbook.getSheetAt(0);
			// 修改excle表的第5行，从第三列开始的数据

			XSSFCell cell = sh.getRow(rowNum).getCell(resultColumnNum, Row.CREATE_NULL_AS_BLANK);
			cell.setCellValue(value);

			// 将修改后的文件写出到D:\\excel目录下
			FileOutputStream os = new FileOutputStream(file);
			// FileOutputStream os = new
			// FileOutputStream("D:\\test.xlsx");//此路径也可写修改前的路径，相当于在原来excel文档上修改
			os.flush();
			// 将Excel写出
			workbook.write(os);
			// 关闭流
			fileInput.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	/**
//	 * 修改Office 2007 excel
//	 */
//	public static void write2007Excel(String path,List<TestObjectResultData> list) throws Exception{
//		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(path));
//		XSSFSheet xSheet = xwb.getSheetAt(0);
//		XSSFCell cell = null;
//		String packagesString="";
//
//		for(int i = 0 ; i < list.size() ; i++){
//
//			XSSFRow xRow = xSheet.getRow(list.get(i).getRow());
//			cell = xRow.getCell(8);
//
//			for(String str:list.get(i).getAllPageResults()){
//				packagesString+=str;
//			}
//			cell.setCellValue(packagesString);
//			cell = xRow.getCell(9);
//			cell.setCellValue(list.get(i).getFinalResult()+"\r\n"+ list.get(i).getOrderResult());
//			cell = xRow.getCell(11);
//			cell.setCellValue(list.get(i).getExtResult());
//
//			packagesString = "";
//		}
//		FileOutputStream out = new FileOutputStream(path);
//		xwb.write(out);
//		out.close();
//	}

	public static void main(String[] args) throws IOException {
		File dd = new File("C:\\Users\\mayn\\Desktop\\集成测试接口\\test.xlsx");
		try {
			// read2007Excel(dd);
			// modifyExcel(dd,"TEST2");
			List<FiddlerTemplate> alist = read2007ExcelWithFiddlerTemplate(dd);
			// for (Iterator<FiddlerTemplate> it = alist.iterator(); it.hasNext();) {
			for (int i = 1; i < alist.size(); i++) {
				if (alist.get(i) != null && !alist.get(i).toString().equals("")) {
//				System.out.println(it.next().getID());
//				System.out.println(it.next().getFullUrl());
//				System.out.println(it.next().getContentType());
//				System.out.println(alist.get(i).getID());
//					System.out.println(alist.get(i).getFullUrl());
//				System.out.println(alist.get(i).getContentType());
					if (alist.get(i).getRequestMethod().equals("GET")) {
						HttpUtil.doGet(alist.get(i).getFullUrl(), alist.get(i).getContentType(), "");
					} else if (alist.get(i).getRequestMethod().equals("POST")) {
						HttpUtil.doPost(alist.get(i).getFullUrl(), alist.get(i).getContentType(), "",
								alist.get(i).getHead());
					} else if (alist.get(i).getRequestMethod().equals("PUT")) {
						HttpUtil.doPut(alist.get(i).getFullUrl(), alist.get(i).getContentType(), "",
								alist.get(i).getHead());
					} else if (alist.get(i).getRequestMethod().equals("DELETE")) {
						HttpUtil.doDelete(alist.get(i).getFullUrl(), alist.get(i).getContentType(), "",
								alist.get(i).getHead());
					} else {
						System.out.println("TODO method");
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
