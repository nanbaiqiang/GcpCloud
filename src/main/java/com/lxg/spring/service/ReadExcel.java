package com.lxg.spring.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lxg.spring.dao.ClientRepository;
import com.lxg.spring.dao.FileUploadRepository;
import com.lxg.spring.dao.PolicyRepository;
import com.lxg.spring.entity.ClientTable;
import com.lxg.spring.entity.FileUploadTable;
import com.lxg.spring.entity.PolicyTable;

@Service
public class ReadExcel {

	@Autowired
	public ClientRepository clientRepository;
	
	@Autowired
	public FileUploadRepository fileUploadRepository;
	
	@Autowired
	public PolicyRepository policyRepository;
	
	public Boolean excel(MultipartFile file) throws IOException {
		
		String fileName = file.getOriginalFilename();
		String postfixName = fileName.substring(fileName.lastIndexOf(".") + 1);
		

		if (fileName.contains("client")) {
			
			List<String[]> data = readExcel(file);
			
			if(data != null)
			{
				List<ClientTable>  clienList= toClientList(data);
				if(clienList!=null)
				{
					clientRepository.save(clienList);
					
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					String userName = authentication.getName();
					
					FileUploadTable fileUpload = new FileUploadTable();
					fileUpload.setFileName(fileName);
					fileUpload.setStatus("success");
					fileUpload.setUplaodUser(userName);
					Date date = new Date();
					SimpleDateFormat ssd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					fileUpload.setDate(ssd.format(new Date()));
					
					fileUploadRepository.save(fileUpload);
				}
			}
			
		} else if (fileName.contains("policy")) {
			
			List<String[]> data = readExcel(file);
			
			if(data != null)
			{
				List<PolicyTable>  policyList= toPolicyList(data);
				if(policyList!=null)
				{
					policyRepository.save(policyList);
					
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					String userName = authentication.getName();
					
					FileUploadTable fileUpload = new FileUploadTable();
					fileUpload.setFileName(fileName);
					fileUpload.setStatus("success");
					fileUpload.setUplaodUser(userName);
					Date date = new Date();
					SimpleDateFormat ssd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					fileUpload.setDate(ssd.format(new Date()));
					
					fileUploadRepository.save(fileUpload);
				}
			}
		} else {
			//two sheet
		}
		
		return true;
	}
	
	public static List<ClientTable>  toClientList (List<String[]> data)
	{
		List<ClientTable> result =  new ArrayList<ClientTable>();
		ClientTable client = null;
		for (int rowNum = 1; rowNum < data.size(); rowNum++) {
			
			if (data.get(rowNum) != null) {

				if(data.get(rowNum)[0] == null || data.get(rowNum)[0].trim().length()==0)
				{
					continue;
				}
				
				client = new ClientTable();
				client.setClientNum(data.get(rowNum)[0]);
				client.setName(data.get(rowNum)[1]);
				client.setIdType(data.get(rowNum)[2]);
				client.setIdNum(data.get(rowNum)[3]);
				client.setGender(data.get(rowNum)[4]);
				client.setBrith(data.get(rowNum)[5]);
				client.setCountry(data.get(rowNum)[6]);
				client.setAdress(data.get(rowNum)[7]);
				client.setMobil(data.get(rowNum)[8]);
				
				result.add(client);

			}
		}
		return result;
		
	}
	public static List<PolicyTable>  toPolicyList (List<String[]> data)
	{
		List<PolicyTable> result =  new ArrayList<PolicyTable>();
		PolicyTable policy = null;
		for (int rowNum = 1; rowNum < data.size(); rowNum++) {
			
			if (data.get(rowNum) != null) {

				if(data.get(rowNum)[0] == null || data.get(rowNum)[0].trim().length()==0)
				{
					continue;
				}
				
				policy = new PolicyTable();
				policy.setPolicyId(data.get(rowNum)[0]);
				policy.setProductType(data.get(rowNum)[1]);
				policy.setClientNumber(data.get(rowNum)[2]);
				policy.setSummary(data.get(rowNum)[3]);
				policy.setIssueDate(data.get(rowNum)[4]);
				policy.setRid("00");

				result.add(policy);

			}
		}
		return result;
		
	}
	
	 private final static String xls = "xls";  
	 private final static String xlsx = "xlsx";  
	      
	 public static List<String[]> readExcel(MultipartFile file) throws IOException{  
	        //检查文件  
	        checkFile(file);  
	        //获得Workbook工作薄对象  
	        Workbook workbook = getWorkBook(file);  
	        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回  
	        List<String[]> list = new ArrayList<String[]>();  
	        if(workbook != null){  
	            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){  
	                //获得当前sheet工作表  
	                Sheet sheet = (Sheet) workbook.getSheetAt(sheetNum);  
	                if(sheet == null){  
	                    continue;  
	                }  
	                //获得当前sheet的开始行  
	                int firstRowNum  = ((XSSFSheet) sheet).getFirstRowNum();  
	                //获得当前sheet的结束行  
	                int lastRowNum = ((XSSFSheet) sheet).getLastRowNum();  
	                //循环除了第一行的所有行  
	                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){  
	                    //获得当前行  
	                    Row row = ((XSSFSheet) sheet).getRow(rowNum);  
	                    if(row == null){  
	                        continue;  
	                    }  
	                    //获得当前行的开始列  
	                    int firstCellNum = row.getFirstCellNum();  
	                    //获得当前行的列数  
	                    int lastCellNum = row.getPhysicalNumberOfCells();  
	                    String[] cells = new String[row.getPhysicalNumberOfCells()];  
	                    //循环当前行  
	                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){  
	                        Cell cell = row.getCell(cellNum);  
	                        cells[cellNum] = getCellValue(cell);  
	                    }  
	                    list.add(cells);  
	                }  
	            }  
	            workbook.close();  
	        }  
	        return list;  
	    }  
	    public static void checkFile(MultipartFile file) throws IOException{  
	        //判断文件是否存在  
	        if(null == file){  
	            //logger.error("文件不存在！");  
	            throw new FileNotFoundException("文件不存在！");  
	        }  
	        //获得文件名  
	        String fileName = file.getOriginalFilename();  
	        //判断文件是否是excel文件  
	        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){  
	            //logger.error(fileName + "不是excel文件");  
	            throw new IOException(fileName + "不是excel文件");  
	        }  
	    }  
	    public static Workbook getWorkBook(MultipartFile file) {  
	        //获得文件名  
	        String fileName = file.getOriginalFilename();  
	        //创建Workbook工作薄对象，表示整个excel  
	        Workbook workbook = null;  
	        try {  
	            //获取excel文件的io流  
	            InputStream is = file.getInputStream();  
	            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象  
	            if(fileName.endsWith(xls)){  
	                //2003  
	                workbook = new HSSFWorkbook(is);  
	            }else if(fileName.endsWith(xlsx)){  
	                //2007  
	                workbook = new XSSFWorkbook(is);  
	            }  
	        } catch (IOException e) {  
	            // error
	        }  
	        return workbook;  
	    }  
	    public static String getCellValue(Cell cell){  
	        String cellValue = "";  
	        if(cell == null){  
	            return cellValue;  
	        }  
	        //把数字当成String来读，避免出现1读成1.0的情况  
	        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){  
	            cell.setCellType(Cell.CELL_TYPE_STRING);  
	        }  
	        //判断数据的类型  
	        switch (cell.getCellType()){  
	            case Cell.CELL_TYPE_NUMERIC: //数字  
	                cellValue = String.valueOf(cell.getNumericCellValue());  
	                break;  
	            case Cell.CELL_TYPE_STRING: //字符串  
	                cellValue = String.valueOf(cell.getStringCellValue());  
	                break;  
	            case Cell.CELL_TYPE_BOOLEAN: //Boolean  
	                cellValue = String.valueOf(cell.getBooleanCellValue());  
	                break;  
	            case Cell.CELL_TYPE_FORMULA: //公式  
	                cellValue = String.valueOf(cell.getCellFormula());  
	                break;  
	            case Cell.CELL_TYPE_BLANK: //空值   
	                cellValue = "";  
	                break;  
	            case Cell.CELL_TYPE_ERROR: //故障  
	                cellValue = "非法字符";  
	                break;  
	            default:  
	                cellValue = "未知类型";  
	                break;  
	        }  
	        return cellValue;  
	    }  
}
