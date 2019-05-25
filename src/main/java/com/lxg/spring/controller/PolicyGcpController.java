package com.lxg.spring.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import com.lxg.spring.dao.BarRepository;
import com.lxg.spring.dao.ClientRepository;
import com.lxg.spring.dao.FileUploadRepository;
import com.lxg.spring.dao.LineRepository;
import com.lxg.spring.dao.PolicyRepository;
import com.lxg.spring.entity.BarTable;
import com.lxg.spring.entity.ClientTable;
import com.lxg.spring.entity.FileUploadTable;
import com.lxg.spring.entity.LineTable;
import com.lxg.spring.entity.PolicyTable;
import com.lxg.spring.service.BigQueryDemo;
import com.lxg.spring.service.ReadExcel;
import com.lxg.spring.vo.AreaVO;
import com.lxg.spring.vo.BarVO;
import com.lxg.spring.vo.Donutvo;
import com.lxg.spring.vo.LineVO;
import com.lxg.spring.vo.RequestVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@RestController
public class PolicyGcpController {

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
	FileUploadRepository fileUploadRepository;
	
	
	@RequestMapping(value = "/gcp/getLine", method = RequestMethod.GET)
	@ResponseBody
    public ResponseEntity<List<LineVO>> getLine(@RequestParam("sysname") String sysname)
    {
		//BigQueryDemo.implicit()
		
		JSONObject js =new JSONObject();
		
		List<LineTable> policyList = lineRepository.findLineTableByYear("2011");
		List<LineVO> result = new ArrayList<LineVO>(); 
		for(LineTable row:policyList)
		{
			LineVO lineItem = new LineVO();
			lineItem.setKey(row.getPolicyNum());
			lineItem.setValue(row.getCount());
			result.add(lineItem);
			
		}
		
		return ResponseEntity.ok().body(result);
    }
	
	@RequestMapping(value = "/gcp/getBar", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/gcp/getArea", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/gcp/getDonut", method = RequestMethod.GET)
	@ResponseBody
    public ResponseEntity<List<Donutvo>> getDonut()
    {
		
		List<Donutvo> result = new ArrayList<Donutvo>(); 
		
		Donutvo l1 = new Donutvo();
		l1.setValue("50");
		l1.setLabel("life 1");
		l1.setFormatted("70% per");
		
		Donutvo l2 = new Donutvo();
		l2.setValue("100");
		l2.setLabel("life 2");
		l2.setFormatted("72% per");
		
		Donutvo l3 = new Donutvo();
		l3.setValue("70");
		l3.setLabel("life 3");
		l3.setFormatted("77% per");
		
		Donutvo l4 = new Donutvo();
		l4.setValue("70");
		l4.setLabel("life 3");
		l4.setFormatted("77% per");
		
		Donutvo l5 = new Donutvo();
		l5.setValue("70");
		l5.setLabel("life 3");
		l5.setFormatted("77% per");
		
		
		result.add(l1);
		result.add(l2);
		result.add(l3);
		result.add(l4);
		result.add(l5);
		
		return ResponseEntity.ok().body(result);
    }
	
	
	@PostMapping(path = "/gcp/create_info_post")
	@ResponseBody
    public ResponseEntity<String> create_info_post(@Valid @RequestBody final RequestVO parm)
    {
		return ResponseEntity.ok().body("22222");
    }
	
	@RequestMapping(value = "/gcp/findClient", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<List<ClientTable>> findClient(RequestVO req)
    {
		List<ClientTable> list = null;
		if("1".equals(req.getSearchType()))
		{
			list = clientRepository.findAll();
		}
		else
		{
			list = clientRepository.findClientTableByClientNum(req.getName());
		}

		return ResponseEntity.ok().body(list);
    }
	
	@RequestMapping(value = "/gcp/findPolicy", method = RequestMethod.POST)
	@ResponseBody
	//use @RequestBody not @RequestParam
    public ResponseEntity<List<PolicyTable>> findPolicy(@RequestBody RequestVO prama)
    {
		List<PolicyTable> list =  policyRepository.findPolicyTableByClientNumber(prama.getClientNum());
		return ResponseEntity.ok().body(list);
    }
	
	

@RequestMapping(value = "gcp/importFile", method = RequestMethod.POST)
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
@RequestMapping(value = "gcp/apk_upload" ,method = RequestMethod.POST)
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
            readExcel.excel(multipartFile);
            
      }

    return json;
}


@RequestMapping(value = "/gcp/getFilelist", method = RequestMethod.GET)
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
