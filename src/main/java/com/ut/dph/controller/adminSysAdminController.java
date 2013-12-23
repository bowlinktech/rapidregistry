package com.ut.dph.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.ut.dph.reference.USStateList;
import com.ut.dph.service.configurationManager;
import com.ut.dph.service.sysAdminManager;
import com.ut.dph.model.Macros;
import com.ut.dph.model.custom.LookUpTable;
import com.ut.dph.model.custom.TableData;
import com.ut.dph.model.lutables.lu_Counties;
import com.ut.dph.model.lutables.lu_GeneralHealths;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/administrator/sysadmin")
public class adminSysAdminController {
	
	@Autowired 
	private sysAdminManager sysAdminManager;

	@Autowired
    private configurationManager configurationmanager;

	/**
	 * The private maxResults variable will hold the number of results to show per
	 * list page.
	 */
	private static int maxResults = 20;
	private static int nonPagingMax = 999999;
	private static int startPage = 1;

	/**
	 * This shows a dashboard with info for sysadmin components.
	 * **/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView dashboard(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/administrator/sysadmin/dashboard");
        /**set totals**/
        int totalLookUpTables = sysAdminManager.findTotalLookUpTable();
        Long totalMacroRows = sysAdminManager.findTotalMacroRows();
        mav.addObject("totalLookUpTables",totalLookUpTables);
        mav.addObject("totalMacroRows",totalMacroRows);
        
        return mav;
    }
    
    
    /**
	 * MACROS 
	 * **/
	@RequestMapping(value="/macros", method = RequestMethod.GET)
	public ModelAndView listMacros(@RequestParam(value="page", required=false) Integer page) throws Exception {
		
		if(page == null){
	        page = 1;
	    }
 
		ModelAndView mav = new ModelAndView();
        mav.setViewName("/administrator/sysadmin/macros");
        
      //Return a list of available macros
        List<Macros> macroList = sysAdminManager.getMarcoList(maxResults, page, "%");
        mav.addObject("macroList", macroList);   
        
        /**need to handle paging**/
        Long totalMacroRows = sysAdminManager.findTotalMacroRows();
        Integer totalPages = (int) Math.ceil((double)totalMacroRows/maxResults);
        mav.addObject("totalPages",totalPages);
        mav.addObject("currentPage",page);
     
        return mav;
 
	}
	
	@RequestMapping(value="/macros", method = RequestMethod.POST)
	public ModelAndView listMacrosSearch(@RequestParam String searchTerm) throws Exception {
		
		ModelAndView mav = new ModelAndView();
        mav.setViewName("/administrator/sysadmin/macros");
      //Return a list of available macros
        List<Macros> macroList = sysAdminManager.getMarcoList(999999, startPage, searchTerm);
        mav.addObject("macroList", macroList);  
        mav.addObject("searchTerm",searchTerm);
        return mav;
 
	}

	@RequestMapping(value="/macros/delete", method = RequestMethod.GET)
	public ModelAndView deleteMacro(@RequestParam(value="i", required=true) int macroId, 
	RedirectAttributes redirectAttr) throws Exception {
		
	
        boolean suceeded = sysAdminManager.deleteMacro(macroId);
        String returnMessage = "deleted";
        
        if (!suceeded) {
        	returnMessage = "notDeleted";
        }
        //This variable will be used to display the message on the details form
		redirectAttr.addFlashAttribute("savedStatus", returnMessage);	
		
		ModelAndView mav = new ModelAndView(new RedirectView("../macros"));
		return mav;	
	}
	
	
	/**
	 *  The '/{urlId}/data.create' GET request will be used to create a new data for selected table
	 *  
	 */
	@RequestMapping(value="/macros/create", method = RequestMethod.GET)
	public ModelAndView newMacroForm() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/macro/details");	
		
		//create a macro
		Macros macro = new Macros();
		macro.setId(0);
		mav.addObject("macroDetails",macro);
		mav.addObject("btnValue", "Create");
		return mav;
	}
	
	
	@RequestMapping(value="/macros/create", method = RequestMethod.POST)
	public ModelAndView createMacro(
			@Valid @ModelAttribute(value="macroDetails") Macros macroDetails, 
			BindingResult result) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/macro/details");	
		/** check for error **/
		if(result.hasErrors()) {
			mav.addObject("macroDetails",macroDetails);
			mav.addObject("btnValue", "Create");
			return mav;
		}	
		//now we save
		sysAdminManager.createMacro(macroDetails);
		mav.addObject("success", "macroCreated");
		mav.addObject("btnValue", "Update");
		return mav;
	}
	
	/**
	 *  The '/macros/view' GET request will be used to create a new data for selected table
	 *  
	 */
	@RequestMapping(value="/macros/view", method = RequestMethod.GET)
	public ModelAndView viewMacroDetails(@RequestParam(value="i", required=false) Integer i) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/macro/details");	
		//get macro info here
		Macros macroDetails = configurationmanager.getMacroById(i);
		mav.addObject("macroDetails",macroDetails);
		mav.addObject("btnValue", "Update");
		return mav;		
	}	
	
	/**
	 * UPDATE macros
	 * **/
	@RequestMapping(value="/macros/update", method = RequestMethod.POST)
	public ModelAndView updateMacro(
			@Valid @ModelAttribute(value="macroDetails") Macros macroDetails, 
			BindingResult result) throws Exception {	
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/macro/details");	
		
		if(result.hasErrors()) {
			mav.addObject("macroDetails",macroDetails);
			mav.addObject("btnValue", "Update");
			return mav;
		} 
		
		//get macro info here
		boolean updated = sysAdminManager.updateMacro(macroDetails);
		
		if (updated) {
			mav.addObject("success", "macroUpdated");
		} else {
			mav.addObject("success", "Error!");
		}
		mav.addObject("macroDetails",macroDetails);
		mav.addObject("btnValue", "Update");
		return mav;		
	}
	
	/**
	 * END MACROS
	 * **/
    
	/**
	 *  The '/list' GET request will serve up the existing list of lu_ tables in the system
	 *  
	 * @param page			The page parameter will hold the page to view when pagination 
	 * 						is built.
	 * @return				The list of look up tables
	 * 
	 * @Objects				(1) An object containing all the found look up tables
	 * 				
	 * @throws Exception
	 */
	@RequestMapping(value="/data", method = RequestMethod.GET)
	public ModelAndView listLookUpTables(@RequestParam(value="page", required=false) Integer page) throws Exception {
		
		if(page == null){
	        page = startPage;
	    }
 
		ModelAndView mav = new ModelAndView();
        mav.setViewName("/administrator/sysadmin/data/tableList");
        
        /**
         * we query list of tables for display
         **/
         List <LookUpTable> tableList = sysAdminManager.getTableList(maxResults, page, "%");
        mav.addObject("tableList", tableList);
       
        //Return the total list of look up table
        int totalLookUpTables = sysAdminManager.findTotalLookUpTable();
        Integer totalPages = (int) Math.ceil((double)totalLookUpTables/maxResults);
        mav.addObject("totalPages",totalPages);
        mav.addObject("currentPage",page);
       
        return mav;
 
	}

	
	/**
	 * The '/data' POST request will be used to search look up tables from the search form on the
	 * list page.
	 * 
	 * @param searchTerm	The searchTerm parameter will hold the string to search on
	 * @return				The look up table page list
	 * 
	 * @Objects				(1) An object will be returned holding the requested search term used to 
	 * 							populate the search box
	 * 						(2) An object containing the found look up tables
	 * @throws Exception
	 */
	@RequestMapping(value="/data", method = RequestMethod.POST)
	public ModelAndView searchForLookUpTable(@RequestParam String searchTerm) 
			throws Exception {
		
		
		ModelAndView mav = new ModelAndView();
		 mav.setViewName("/administrator/sysadmin/data/tableList");
        List<LookUpTable> tableList = sysAdminManager.getTableList(nonPagingMax, 1, searchTerm);
        mav.addObject("searchTerm",searchTerm);
        mav.addObject("tableList", tableList);		
        return mav;
 
	}

	/**
	 *  The '/data/std/{urlId}' GET request will serve up the data for a standardize look up table
	 *  
	 * @param page			The page parameter will hold the page to view when pagination 
	 * 						is built.
	 * @param urlId			The urlId for the look up table - can't use dynamic table names in Hibernate, 
	 * 						and don't want to worry about sql injection and plain view of table name, 
	 * 						will do a look up.
	 * @param searchTerm	if a user want to look for a particular term, will be useful when we have a lot of medical services						
	 * 
	 * @return				list of TableData
	 * 
	 * @Objects				(1) An object containing all the found look up tables
	 * 				
	 * @throws Exception
	 */
	@RequestMapping(value="/data/std/{urlId}", method = RequestMethod.GET)
	public ModelAndView listTableData(@RequestParam(value="page", required=false) Integer page,  @PathVariable String urlId) throws Exception {
		
		if(page == null){
	        page = startPage;
	    }
 
		ModelAndView mav = new ModelAndView();
        mav.setViewName("/administrator/sysadmin/data/std");
        
        /**
         * we query data for look up table, this view returns all data from table, hence the search term will be %
         **/
         LookUpTable tableInfo = sysAdminManager.getTableInfo(urlId);
         List <TableData> dataList = sysAdminManager.getDataList(maxResults, page, tableInfo.getUtTableName(), "%");
         mav.addObject("dataList", dataList);
         mav.addObject("tableInfo", tableInfo);
               
        int totalDataRows = sysAdminManager.findTotalDataRows(tableInfo.getUtTableName());
        Integer totalPages = (int) Math.ceil((double)totalDataRows/maxResults);
        mav.addObject("totalPages",totalPages);
        mav.addObject("currentPage",page);
        mav.addObject("goToURL", tableInfo.getUrlId());
        mav.addObject("urlIdInfo", tableInfo.getUrlId());
        return mav;
 
	}
	
	/**
	 *  The '/data/std/{urlId}' Post request will serve up the date for a look up table
	 *  
	 * @param page			The page parameter will hold the page to view when pagination 
	 * 						is built.
	 * @param urlId			The urlId for the look up table - can't use dynamic table names in Hibernate, 
	 * 						and don't want to worry about sql injection and plain view of table name, 
	 * 						will do a look up.
	 * @param searchTerm	if a user want to look for a particular term, will be useful when we have a lot of medical services						
	 * 
	 * @return				list of TableData
	 * 
	 * @Objects				(1) An object containing all the found look up tables
	 * 				
	 * @throws Exception
	 */
	@RequestMapping(value="/data/std/{urlId}", method = RequestMethod.POST)
	public ModelAndView listSearchDataInTable(@RequestParam String searchTerm,  @PathVariable String urlId) throws Exception {
		
		ModelAndView mav = new ModelAndView();
        mav.setViewName("/administrator/sysadmin/data/std");
        
        /**
         * we query data for look up table, this view returns all data from table, hence the search term will be %
         **/
         LookUpTable tableInfo = sysAdminManager.getTableInfo(urlId);
         List <TableData> dataList = sysAdminManager.getDataList(nonPagingMax, startPage, tableInfo.getUtTableName(), searchTerm);
         mav.addObject("dataList", dataList);
         mav.addObject("tableInfo", tableInfo);
         mav.addObject("searchTerm",searchTerm);
         mav.addObject("goToURL", tableInfo.getUrlId());
         mav.addObject("urlIdInfo", tableInfo.getUrlId());
         return mav;
 
	}
	
	/**
	 *  The '/data/std/{urlId}/delete?i=' Post request will serve up the date for a look up table
	 *  
	 * @param page			The page parameter will hold the page to view when pagination 
	 * 						is built.
	 * @param urlId			The urlId for the look up table - can't use dynamic table names in Hibernate, 
	 * 						and don't want to worry about sql injection and plain view of table name, 
	 * 						will do a look up.
	 * @param i	if a user want to look for a particular term, will be useful when we have a lot of medical services						
	 * 
	 * @return				list of TableData
	 * 
	 * @Objects				(1) An object containing all the found look up tables
	 * 				
	 * @throws Exception
	 */
	@RequestMapping(value="/data/std/{urlId}/delete", method = RequestMethod.GET)
	public ModelAndView deleteTableData(@RequestParam(value="i", required=true) int dataId, 
			@PathVariable String urlId, RedirectAttributes redirectAttr) throws Exception {
		 
		LookUpTable tableInfo = sysAdminManager.getTableInfo(urlId);
        
        boolean suceeded = sysAdminManager.deleteDataItem(tableInfo.getUtTableName(), dataId);
        String returnMessage = "deleted";
        
        if (!suceeded) {
        	returnMessage = "notDeleted";
        }
        //This variable will be used to display the message on the details form
		redirectAttr.addFlashAttribute("savedStatus", returnMessage);	
		
		ModelAndView mav = new ModelAndView(new RedirectView("../" + urlId));
		return mav;	
     
	}	
	
	
	/**
	 *  The '/{urlId}/data.create' GET request will be used to create a new data for selected table
	 *  
	 */
	@RequestMapping(value="/data/std/{urlId}/create", method = RequestMethod.GET)
	public ModelAndView newTableDataForm(@PathVariable String urlId) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/data/std/details");	
		
		LookUpTable tableInfo = sysAdminManager.getTableInfo(urlId);
		//create a table data
		TableData tableData = new TableData();
		tableData.setId(0);
		tableData.setUrlId(urlId);
		mav.addObject("stdForm", "stdForm");
		mav.addObject("tableDataDetails",tableData);
		mav.addObject("tableInfo",tableInfo);
		mav.addObject("ojectType","tableData");
		mav.addObject("formId","tabledataform");
		mav.addObject("btnValue", "Create");
		mav.addObject("submitBtnValue", "Create");
		return mav;
	}
	
	/**
	 * The '/{urlId}/create' POST request will handle submitting the new provider.
	 * 
	 * @param tableDataForm		The object containing the tableData form fields
	 * @param result			The validation result
	 * @param redirectAttr		The variable that will hold values that can be read after the redirect
	 * @Objects					(1) The object containing all the information for the new data item
	 * 							(2) We will extract table from web address
	 * @throws Exception
	 */
	@RequestMapping(value="/data/std/create", method = RequestMethod.POST)
	public ModelAndView createTableData(
			@Valid @ModelAttribute(value="tableDataDetails") TableData tableData, 
			BindingResult result) throws Exception {
		
		LookUpTable tableInfo = sysAdminManager.getTableInfo(tableData.getUrlId());
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/data/std/details");
		mav.addObject("ojectType","tableData");
		mav.addObject("formId","tabledataform");
		/** check for error **/
		if(result.hasErrors()) {
			mav.addObject("stdForm", "stdForm");
			mav.addObject("tableInfo",tableInfo);
			mav.addObject("btnValue", "Create");
			mav.addObject("submitBtnValue", "Create");
			return mav;
		}
		
		//now we save
		sysAdminManager.createTableDataHibernate(tableData, tableInfo.getUtTableName());
		mav.addObject("success", "dataCreated");
		mav.addObject("btnValue", "Update");
		mav.addObject("submitBtnValue", "Update");
		return mav;
	}
	
	/**
	 *  The '/data/std/{urlId}/tableData?i=' GET request will be used to create a new data for selected table
	 *  
	 */
	@RequestMapping(value="/data/std/{urlId}/tableData", method = RequestMethod.GET)
	public ModelAndView viewTableData(@PathVariable String urlId, 
			@RequestParam(value="i", required=false) Integer i) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/data/std/details");	
		
		LookUpTable tableInfo = sysAdminManager.getTableInfo(urlId);
		TableData tableData = sysAdminManager.getTableData(i, tableInfo.getUtTableName());
		tableData.setUrlId(urlId);
		mav.addObject("tableDataDetails",tableData);
		mav.addObject("tableInfo",tableInfo);
		mav.addObject("ojectType","tableData");
		mav.addObject("stdForm", "stdForm");
		mav.addObject("formId","tabledataform");
		mav.addObject("btnValue", "Update");
		mav.addObject("submitBtnValue", "Update");
		return mav;		
	}
	
	/**
	 * The '/data/std/update' POST request will be used to update a look up data item
	 * 
	 */
	@RequestMapping(value = "/data/std/update", method = RequestMethod.POST)
	public ModelAndView updateTableData(
			@Valid @ModelAttribute(value = "tableDataDetails") TableData tableData,
			BindingResult result)
			throws Exception {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/data/std/details");
		mav.addObject("ojectType","tableData");
		mav.addObject("formId","tabledataform");
		mav.addObject("submitBtnValue", "Update");
		mav.addObject("btnValue", "Update");
		
		LookUpTable tableInfo = sysAdminManager.getTableInfo(tableData.getUrlId());

		/** check for error **/
		if(result.hasErrors()) {
			mav.addObject("tableInfo",tableInfo);
			mav.addObject("stdForm", "stdForm");
			return mav;
		}
		
		// now we update
		boolean updated = sysAdminManager.updateTableData(tableData, tableInfo.getUtTableName());
		
		// This variable will be used to display the message on the details
		if (updated) {
			mav.addObject("success", "dataUpdated");
		} else {
			mav.addObject("success", "- There is an error.");
		}

		return mav;
	}

	

	/** 
	 * here we have the views for the look up tables that do not have the standard 7 columns,
	 * these have models in UT
	 * 
	 */
	
	@RequestMapping(value="/data/nstd/{urlId}", method = RequestMethod.GET)
	public ModelAndView listTableDataNStd(@RequestParam(value="page", required=false) Integer page,  @PathVariable String urlId) throws Exception {
		
		if(page == null){
	        page = startPage;
	    }
 
		ModelAndView mav = new ModelAndView();
        mav.setViewName("/administrator/sysadmin/data/std");
        
        /**
         * we query data for look up table, this view returns all data from table, hence the search term will be %
         **/
         LookUpTable tableInfo = sysAdminManager.getTableInfo(urlId);
         List <TableData> dataList = sysAdminManager.getDataList(maxResults, page, tableInfo.getUtTableName(), "%");
         mav.addObject("dataList", dataList);
         mav.addObject("tableInfo", tableInfo);
               
        int totalDataRows = sysAdminManager.findTotalDataRows(tableInfo.getUtTableName());
        Integer totalPages = (int) Math.ceil((double)totalDataRows/maxResults);
        mav.addObject("totalPages",totalPages);
        mav.addObject("currentPage",page);
        mav.addObject("goToURL", tableInfo.getUtTableName());
        mav.addObject("urlIdInfo", tableInfo.getUrlId());
        return mav;
	}
	
	
	@RequestMapping(value="/data/nstd/{urlId}", method = RequestMethod.POST)
	public ModelAndView listSearchDataForNStd(@RequestParam String searchTerm,  @PathVariable String urlId) throws Exception {
		
		ModelAndView mav = new ModelAndView();
        mav.setViewName("/administrator/sysadmin/data/std");
        
        /**
         * we query data for look up table, this view returns all data from table, hence the search term will be %
         **/
         LookUpTable tableInfo = sysAdminManager.getTableInfo(urlId);
         List <TableData> dataList = sysAdminManager.getDataList(nonPagingMax, startPage, tableInfo.getUtTableName(), searchTerm);
         mav.addObject("dataList", dataList);
         mav.addObject("tableInfo", tableInfo);
         mav.addObject("searchTerm",searchTerm);
         mav.addObject("goToURL", tableInfo.getUtTableName());
         mav.addObject("urlIdInfo", tableInfo.getUrlId());
         
         return mav;
	}
	
	
	@RequestMapping(value="/data/nstd/{urlId}/delete", method = RequestMethod.GET)
	public ModelAndView deleteCountyData(@RequestParam(value="i", required=true) int dataId, 
			@PathVariable String urlId, RedirectAttributes redirectAttr) throws Exception {
		 
		LookUpTable tableInfo = sysAdminManager.getTableInfo(urlId);
        
        boolean suceeded = sysAdminManager.deleteDataItem(tableInfo.getUtTableName(), dataId);
        String returnMessage = "deleted";
        
        if (!suceeded) {
        	returnMessage = "notDeleted";
        }
        //This variable will be used to display the message on the details form
		redirectAttr.addFlashAttribute("savedStatus", returnMessage);	
		
		ModelAndView mav = new ModelAndView(new RedirectView("../" + urlId));
		return mav;	
     
	}	
	
	
	@RequestMapping(value="/data/nstd/lu_Counties/create", method = RequestMethod.GET)
	public ModelAndView newCountyForm() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/data/std/details");	
		
		//Get a list of states
        USStateList stateList = new USStateList();

		//create a lu_Counties
		lu_Counties luc = new lu_Counties();
		luc.setId(0);
		mav.addObject("tableDataDetails",luc);
		mav.addObject("ojectType","lu_Counties");
		mav.addObject("formId","tabledataform");
		mav.addObject("stateList", stateList.getStates());
		mav.addObject("btnValue", "lu_counties/create");
		mav.addObject("submitBtnValue", "Create");
		return mav;
	}
	
	@RequestMapping(value="/data/nstd/lu_counties/create", method = RequestMethod.POST)
	public ModelAndView createCounties(
			@Valid @ModelAttribute(value="tableDataDetails") lu_Counties luc, 
			BindingResult result) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/data/std/details");
		mav.addObject("ojectType","lu_Counties");
		mav.addObject("formId","tabledataform");
		/** check for error **/
		if(result.hasErrors()) {
			mav.addObject("btnValue", "lu_counties/create");
			 //Get a list of states
            USStateList stateList = new USStateList();
            //Get the object that will hold the states
            mav.addObject("stateList", stateList.getStates());
			mav.addObject("submitBtnValue", "Create");
			return mav;
		}
		
		//now we save
		sysAdminManager.createCounty(luc);
		mav.addObject("success", "dataCreated");
		mav.addObject("btnValue", "lu_counties/update");
		mav.addObject("submitBtnValue", "Update");
		return mav;
	}
	
	
	/**view a county's data	data/nstd/lu_Counties/tableData**/
	@RequestMapping(value="/data/nstd/lu_Counties/tableData", method = RequestMethod.GET)
	public ModelAndView viewCountyData(@RequestParam(value="i", required=false) Integer i) 
			throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/data/std/details");	
		
		USStateList stateList = new USStateList();
	
		lu_Counties luc = sysAdminManager.getCountyById(i);
		mav.addObject("tableDataDetails",luc);
        //Get the object that will hold the states
        mav.addObject("stateList", stateList.getStates());
        mav.addObject("ojectType","lu_Counties");
		mav.addObject("formId","tabledataform");
		mav.addObject("btnValue", "lu_counties/update");
		mav.addObject("submitBtnValue", "Update");
		return mav;		
	}
	

	@RequestMapping(value="/data/nstd/lu_counties/update", method = RequestMethod.POST)
	public ModelAndView updateCounty(
			@Valid @ModelAttribute(value="tableDataDetails") lu_Counties luc, 
			BindingResult result) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/data/std/details");
		mav.addObject("ojectType","lu_Counties");
		mav.addObject("formId","tabledataform");
		/** check for error **/
		if(result.hasErrors()) {
			mav.addObject("btnValue", "lu_counties/update");
			 //Get a list of states
            USStateList stateList = new USStateList();
            //Get the object that will hold the states
            mav.addObject("stateList", stateList.getStates());
			mav.addObject("submitBtnValue", "Update");
			return mav;
		}
		
		//now we save
		sysAdminManager.updateCounty(luc);
		mav.addObject("success", "dataUpdated");
		mav.addObject("btnValue", "lu_counties/update");
		mav.addObject("submitBtnValue", "Update");
		return mav;
	}
	
	/** general health **/
	@RequestMapping(value="/data/nstd/lu_GeneralHealths/create", method = RequestMethod.GET)
	public ModelAndView newGeneralHealthForm() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/data/std/details");	
		
		//create a lu_Counties
		lu_GeneralHealths lu = new lu_GeneralHealths();
		lu.setId(0);
		mav.addObject("tableDataDetails",lu);
		mav.addObject("ojectType","lu_GeneralHealths");
		mav.addObject("formId","tabledataform");
		mav.addObject("btnValue", "lu_generalhealths/create");
		mav.addObject("submitBtnValue", "Create");
		return mav;
	}
	
	

	@RequestMapping(value="/data/nstd/lu_generalhealths/create", method = RequestMethod.POST)
	public ModelAndView createGeneralHealth(
			@Valid @ModelAttribute(value="tableDataDetails") lu_GeneralHealths lu, 
			BindingResult result) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/data/std/details");
		mav.addObject("ojectType","lu_GeneralHealths");
		mav.addObject("formId","tabledataform");
		// check for error
		if(result.hasErrors()) {
			mav.addObject("btnValue", "lu_generalhealths/create");
			mav.addObject("submitBtnValue", "Create");
			return mav;
		}
		
		//now we save
		sysAdminManager.createGeneralHealth(lu);
		mav.addObject("success", "dataCreated");
		mav.addObject("btnValue", "lu_generalhealths/update");
		mav.addObject("submitBtnValue", "Update");
		return mav;
	}
	

	@RequestMapping(value="/data/nstd/lu_GeneralHealths/tableData", method = RequestMethod.GET)
	public ModelAndView viewGeneralHealth(@RequestParam(value="i", required=false) Integer i) 
			throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/data/std/details");	
		
		lu_GeneralHealths lu = sysAdminManager.getGeneralHealthById(i);
		mav.addObject("tableDataDetails",lu);
        //Get the object that will hold the states
        mav.addObject("ojectType","lu_GeneralHealths");
		mav.addObject("formId","tabledataform");
		mav.addObject("btnValue", "lu_generalhealths/update");
		mav.addObject("submitBtnValue", "Update");
		return mav;		
	}
	

	@RequestMapping(value="/data/nstd/lu_generalhealths/update", method = RequestMethod.POST)
	public ModelAndView updateGeneralHealth(
			@Valid @ModelAttribute(value="tableDataDetails") lu_GeneralHealths lu, 
			BindingResult result) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/administrator/sysadmin/data/std/details");
		mav.addObject("ojectType","lu_GeneralHealths");
		mav.addObject("formId","tabledataform");
		/** check for error **/
		if(result.hasErrors()) {
			mav.addObject("btnValue", "lu_generalhealths/update");
			mav.addObject("submitBtnValue", "Update");
			return mav;
		}
		
		//now we save
		sysAdminManager.updateGeneralHealth(lu);
		mav.addObject("success", "dataUpdated");
		mav.addObject("btnValue", "lu_generalhealths/update");
		mav.addObject("submitBtnValue", "Update");
		return mav;
	}
	
	
}

