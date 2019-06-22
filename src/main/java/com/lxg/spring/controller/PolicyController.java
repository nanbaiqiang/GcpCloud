package com.lxg.spring.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.io.Files;
import com.lxg.spring.dao.BarRepository;
import com.lxg.spring.dao.ClientRepository;
import com.lxg.spring.dao.DonutRepository;
import com.lxg.spring.dao.FileUploadRepository;
import com.lxg.spring.dao.LineRepository;
import com.lxg.spring.dao.PolicyRepository;
import com.lxg.spring.entity.BarTable;
import com.lxg.spring.entity.ClientTable;
import com.lxg.spring.entity.DonutTable;
import com.lxg.spring.entity.FileUploadTable;
import com.lxg.spring.entity.LineTable;
import com.lxg.spring.entity.PolicyTable;
import com.lxg.spring.service.ReadExcel;
import com.lxg.spring.service.SimpleBigquery;
import com.lxg.spring.vo.AreaVO;
import com.lxg.spring.vo.BarVO;
import com.lxg.spring.vo.Donutvo;
import com.lxg.spring.vo.LineVO;
import com.lxg.spring.vo.RequestVO;


@RestController
public class PolicyController {

	private  ByteArrayOutputStream bout;
	private  PrintStream out;

    @Autowired
    private LineRepository lineRepository;
    
    @Autowired
    private BarRepository barRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private PolicyRepository policyRepository;
    
	@Autowired
	public ReadExcel readExcel;
	
	@Autowired
	public DonutRepository donutRepository;
	
	
	@Autowired
	FileUploadRepository fileUploadRepository;
	
	private List<LineVO> getLineLocalSQl()
	{
		List<LineTable> policyList = lineRepository.findLineTableByYear("2011");
		List<LineVO> result = new ArrayList<LineVO>(); 
		for(LineTable row:policyList)
		{
			LineVO lineItem = new LineVO();
			lineItem.setKey(row.getPolicyNum());
			lineItem.setValue(row.getCount());
			result.add(lineItem);
			
		}
		return result;
	}
    
	@RequestMapping(value = "/v1/getLine", method = RequestMethod.GET)
	@ResponseBody
    public ResponseEntity<List<LineVO>> getLine(@RequestParam("sysname") String sysname)
    {
		  bout = new ByteArrayOutputStream();
		  out = new PrintStream(bout);
		  
		TableResult resultBigquery=null; 
		String sql="select * from `luminous-night-238606.client.line`";
		try {
			resultBigquery = SimpleBigquery.query(sql);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<LineVO> result = new ArrayList<LineVO>(); 
		
	    if(resultBigquery != null)
	    {
		    for (FieldValueList row : resultBigquery.iterateAll()) {
		        String year = row.get("YEAR").getStringValue();
		      
		        if("2011".equals(year)){
				LineVO lineItem = new LineVO();
				lineItem.setKey(row.get("POLICY_NUM").getStringValue());
				lineItem.setValue(row.get("COUNT").getStringValue());
				result.add(lineItem);
		        }
		    }

	    }

		//result  = getLineLocalSQl();
//		String ss = JsonUtil.objectList2jsonArray(result);
//		//System.out.println(ss);
//		List<LineVO> result1 =  JsonUtil.json2ObjectList(ss);
		return ResponseEntity.ok().body(result);
    }
	
	@RequestMapping(value = "/v1/getBar", method = RequestMethod.GET)
	@ResponseBody
    public ResponseEntity<List<BarVO>> getBar()
    {

		List<BarVO> result = new ArrayList<BarVO>(); 
		
		List<BarTable> policyInfo= barRepository.findAll();
		Map<String,List<BarTable>> policyByYear = new HashMap<String, List<BarTable>>();
		for(BarTable item:policyInfo)
		{
			if(policyByYear.get(item.getYear())!=null)
			{
				policyByYear.get(item.getYear()).add(item);
			}
			else
			{
				List<BarTable> list =  new ArrayList<BarTable>();
				list.add(item);
				policyByYear.put(item.getYear(), list);
			}
		}
		
		for (Map.Entry<String,List<BarTable>> entry : policyByYear.entrySet()) 
		{ 
			List<BarTable> barList = entry.getValue();
			BarVO l1 = new BarVO();
			l1.setYear(entry.getKey());
			for(BarTable item:barList)
			{
				switch(item.getPolicyId())
				{ 
				case 101:
					l1.setLife1Num(item.getPolicyCount());
					l1.setLife1Label(item.getPolicyName());
					break;
				case 102:
					l1.setLife2Num(item.getPolicyCount());
					l1.setLife2Label(item.getPolicyName());
					break;
				case 103:
					l1.setLife3Num(item.getPolicyCount());
					l1.setLife3Label(item.getPolicyName());
					break;
					
				};
				
				result.add(l1);
				
			}
		}
		
		return ResponseEntity.ok().body(result);
    }
	
	@RequestMapping(value = "/v1/getArea", method = RequestMethod.GET)
	@ResponseBody
    public ResponseEntity<List<AreaVO>> getArea()
    {
		List<AreaVO> result = new ArrayList<AreaVO>(); 
		
		AreaVO l1 = new AreaVO();
		l1.setYear("2011");
		l1.setPay("150000");
		l1.setSumIns("220000");
		
		AreaVO l2 = new AreaVO();
		l2.setYear("2012");
		l2.setPay("7000");
		l2.setSumIns("320000");
		
		AreaVO l3 = new AreaVO();
		l3.setYear("2013");
		l3.setPay("45000");
		l3.setSumIns("380000");
		
		result.add(l1);
		result.add(l2);
		result.add(l3);
		
		return ResponseEntity.ok().body(result);
    }
	
	@RequestMapping(value = "/v1/getDonut", method = RequestMethod.GET)
	@ResponseBody
    public ResponseEntity<List<Donutvo>> getDonut()
    {
		bout = new ByteArrayOutputStream();
		out = new PrintStream(bout);
		  
		//List<Donutvo> result = getDonutLocalSql();
		List<Donutvo> result = new ArrayList<Donutvo>(); 
		
		TableResult resultBigquery=null; 
		String sql="select * from `luminous-night-238606.client.donut`";
		try {
			resultBigquery = SimpleBigquery.query(sql);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	    if(resultBigquery != null)
	    {
	    	double sum = 0.0;
		    for (FieldValueList row : resultBigquery.iterateAll()) {
				if(row != null)
				{
					sum+= Double.valueOf(row.get("value").getStringValue());
				}
		    }
		    
		    for (FieldValueList row : resultBigquery.iterateAll()) 
			{
				if(row != null)
				{
					Donutvo donutvo = new Donutvo();
					donutvo.setLabel(row.get("label").getStringValue());
					donutvo.setValue(row.get("value").getStringValue());
					String per  =row.get("value").getStringValue() + 
							" " + getNumberDecimalDigits(Double.valueOf(row.get("value").getStringValue())/sum * 100) + "% Per";
					
					donutvo.setFormatted(per);
					result.add(donutvo);
				}
			}

	    }
		    
		return ResponseEntity.ok().body(result);
    }

	private List<Donutvo> getDonutLocalSql() {
		List<Donutvo> result = new ArrayList<Donutvo>(); 
		
		List<DonutTable> donutList = donutRepository.findAll();
		double sum = 0.0;
		for(DonutTable item:donutList)
		{
			if(item != null)
			{
				sum+= Double.valueOf(item.getValue());
			}
		}
		
		for(DonutTable item:donutList)
		{
			if(item!= null)
			{
				Donutvo donutvo = new Donutvo();
				donutvo.setLabel(item.getLabel());
				donutvo.setValue(item.getValue());
				String per  =item.getValue() + " " + getNumberDecimalDigits(Double.valueOf(item.getValue())/sum * 100) + "% Per";
				
				donutvo.setFormatted(per);
				result.add(donutvo);
			}
		}
		return result;
	}
	
	 public static String getNumberDecimalDigits(double number) {
		 DecimalFormat df = new DecimalFormat( "0.00");  
		 return df.format(number);
	    }
	
	@PostMapping(path = "/v1/create_info_post")
	@ResponseBody
    public ResponseEntity<String> create_info_post(@Valid @RequestBody final RequestVO parm)
    {
		return ResponseEntity.ok().body("22222");
    }
	
	@RequestMapping(value = "/v1/findClient", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<List<ClientTable>> findClient(RequestVO req)
    {
		//List<ClientTable> list = findClientLocalSQl(req);
		bout = new ByteArrayOutputStream();
		out = new PrintStream(bout);
		  
		TableResult resultBigquery=null; 
		String sql="select * from `luminous-night-238606.client.client`";
		try {
			resultBigquery = SimpleBigquery.query(sql);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<ClientTable> list = new ArrayList<ClientTable>(); 
		
	    if(resultBigquery != null)
	    {
		    for (FieldValueList row : resultBigquery.iterateAll()) {

		        	ClientTable lineItem = new ClientTable();

					lineItem.setClientNum(row.get("client_number").getStringValue());
					lineItem.setCountry(row.get("country").getStringValue());
					lineItem.setBrith(row.get("birthday").getStringValue());
					lineItem.setAdress(row.get("adress").getStringValue());
					lineItem.setGender(row.get("gender").getStringValue());
					lineItem.setIdNum(row.get("id_number").getStringValue());
					lineItem.setIdType(row.get("id_type").getStringValue());
					lineItem.setMobil(row.get("mobil").getStringValue());
					lineItem.setName(row.get("client_name").getStringValue());
					lineItem.setRemark("there is nothing");
					list.add(lineItem);
		    }

	    }
	    
		return ResponseEntity.ok().body(list);
    }

	private List<ClientTable> findClientLocalSQl(RequestVO req) {
		List<ClientTable> list = null;
		if("1".equals(req.getSearchType()))
		{
			list = clientRepository.findAll();
		}
		else
		{
			list = clientRepository.findClientTableByClientNum(req.getName());
		}
		return list;
	}
	
	@RequestMapping(value = "/v1/findPolicy", method = RequestMethod.POST)
	@ResponseBody
	//use @RequestBody not @RequestParam
    public ResponseEntity<List<PolicyTable>> findPolicy(@RequestBody RequestVO prama)
    {
		//List<PolicyTable> list =  policyRepository.findPolicyTableByClientNumber(prama.getClientNum());
		List<PolicyTable> list =  new ArrayList<PolicyTable>(); 
		
		bout = new ByteArrayOutputStream();
		out = new PrintStream(bout);
		  
		TableResult resultBigquery=null; 
		String sql="select * from `luminous-night-238606.client.policy`";
		try {
			resultBigquery = SimpleBigquery.query(sql);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    if(resultBigquery != null)
	    {
		    for (FieldValueList row : resultBigquery.iterateAll()) {

		    		PolicyTable lineItem = new PolicyTable();
					lineItem.setClientNumber(row.get("client_number").getStringValue());
					lineItem.setCurrency(row.get("currency").getStringValue());
					lineItem.setIssueDate(row.get("issue_date").getStringValue());
					lineItem.setPolicyId(row.get("policy_number").getStringValue());
					lineItem.setPolicyName(row.get("policy_name").getStringValue());
					lineItem.setProductType(row.get("product_type").getStringValue());
					lineItem.setRid(row.get("rid").getStringValue());
					lineItem.setStatus(row.get("status").getStringValue());
					lineItem.setSummary(row.get("installed_premium").getStringValue());

					list.add(lineItem);
		    }

	    }
	    
		return ResponseEntity.ok().body(list);
    }
	
	

@RequestMapping(value = "v1/importFile", method = RequestMethod.POST)
@ResponseBody
public ResponseEntity<String> importFile(@RequestParam("file") MultipartFile[] file){
	String result ="";
	int count = 0;
	if (file != null && file.length > 0) {
		try {
			for(MultipartFile multipartFile : file){
				//count += scsScheduleConfigService.importSchedule(multipartFile);
				readExcel.excel(multipartFile);
			}
			result ="Success";
		} catch (IOException e) {
			result ="Error";

		}
	}

	return ResponseEntity.ok().body(result);
}

@ResponseBody
@RequestMapping(value = "v1/apk_upload" ,method = RequestMethod.POST)
public Map<String, Object> uploadApkFile(HttpServletRequest request,HttpServletResponse response)
        throws Exception {
    request.setCharacterEncoding("UTF-8");

    Map<String, Object> json = new HashMap<String, Object>();
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    
    MultipartFile multipartFile = null;
    Map map =multipartRequest.getFileMap();
     for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            Object obj = i.next();
            multipartFile=(MultipartFile) map.get(obj);
            

            uploadfileToStorage(multipartFile.getBytes(),multipartFile.getInputStream(),multipartFile.getOriginalFilename());
            readExcel.excel(multipartFile);
            
      }

    return json;
}

@SuppressWarnings("deprecation")
public void uploadfileToStorage(byte[] fileByte,InputStream input,String fileName)
{

    Storage storage = StorageOptions.getDefaultInstance().getService();

    // The name for the new bucket
    String bucketName = "Policy_list";  // "my-new-bucket";

    // Creates the new bucket
//    Bucket bucket = storage.create(BucketInfo.of(bucketName));
//    BlobId blobId = BlobId.of(bucketName, "my_blob_name");
//    Blob blob = bucket.create("my_blob_name", fileByte, "text/plain");

    //storage.delete("vendor-bucket3");
    
    BlobInfo blobInfo =
            storage.create(
                BlobInfo
                    .newBuilder(bucketName, fileName)
                    // Modify access list to allow all users with link to read file
                    .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
                    .build(),
                    input);
        // return the public download link
        String path =  blobInfo.getMediaLink();
}

@RequestMapping(value = "/v1/getFilelist", method = RequestMethod.GET)
@ResponseBody
public ResponseEntity<List<FileUploadTable>> getFilelist()
{
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	String userName = authentication.getName();
	System.out.println(userName);
	List<FileUploadTable> fileList = fileUploadRepository.findFileUploadTableByUplaodUser(userName);
	
	return ResponseEntity.ok().body(fileList);
}

}
