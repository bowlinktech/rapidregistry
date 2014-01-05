package com.ut.dph.dao;

import java.util.List;

import com.ut.dph.model.custom.LogoInfo;
import com.ut.dph.model.custom.LookUpTable;
import com.ut.dph.model.custom.TableData;
import com.ut.dph.model.lutables.lu_Counties;
import com.ut.dph.model.lutables.lu_GeneralHealthStatuses;
import com.ut.dph.model.lutables.lu_GeneralHealths;
import com.ut.dph.model.lutables.lu_Immunizations;
import com.ut.dph.model.lutables.lu_Manufacturers;
import com.ut.dph.model.lutables.lu_MedicalConditions;
import com.ut.dph.model.lutables.lu_Medications;
import com.ut.dph.model.lutables.lu_Procedures;
import com.ut.dph.model.lutables.lu_ProcessStatus;
import com.ut.dph.model.lutables.lu_Tests;
import com.ut.dph.model.Macros;

public interface sysAdminDAO {
	
	List <LookUpTable> getLookUpTables(int page, int maxResults, String searchTerm);
	
	Integer findTotalLookUpTable();
	
	List <TableData> getDataList(int page, int maxResults, String utTableName, String searchTerm);
	
	Integer findTotalDataRows(String utTableName);
	
	LookUpTable  getTableInfo(String urlId);
	
	boolean deleteDataItem (String utTableName, int id);
	
	TableData getTableData (Integer id, String utTableName);
	
	Integer createTableData (TableData tableData, String utTableName);
	
	void createTableDataHibernate (TableData tableData, String utTableName);
	
	boolean updateTableData (TableData tableData, String utTableName);
	
	List <Macros> getMarcoList(int maxResults, int page, String searchTerm);
	
	Long findTotalMacroRows();
	
	boolean deleteMacro (int id);
	
	void createMacro(Macros macro);
	
	boolean updateMacro(Macros macro);
	
	void createCounty(lu_Counties luc);
	
	lu_Counties getCountyById (int id);
	
	void updateCounty (lu_Counties luc);
	
	void createGeneralHealth(lu_GeneralHealths lu);

	lu_GeneralHealths getGeneralHealthById (int id);
	
	void updateGeneralHealth (lu_GeneralHealths lu);
	
	void createGeneralHealthStatus(lu_GeneralHealthStatuses lu);

	lu_GeneralHealthStatuses getGeneralHealthStatusById (int id);
	
	void updateGeneralHealthStatus (lu_GeneralHealthStatuses lu);
	
	void createImmunization (lu_Immunizations lu);
	
	lu_Immunizations getImmunizationById (int id);
	
	void updateImmunization (lu_Immunizations lu);
	
	void createManufacturer(lu_Manufacturers lu);

	lu_Manufacturers getManufacturerById (int id);
	
	void updateManufacturer (lu_Manufacturers lu);
	
	void createMedicalCondition (lu_MedicalConditions lu);
	
	lu_MedicalConditions getMedicalConditionById (int id);
	
	void updateMedicalCondition(lu_MedicalConditions lu);
	
	void createMedication (lu_Medications lu);
	
	lu_Medications getMedicationById (int id);
	
	void updateMedication(lu_Medications lu);
	
	void createProcedure (lu_Procedures lu);
	
	lu_Procedures getProcedureById (int id);
	
	void updateProcedure(lu_Procedures lu);
	
	void createTest (lu_Tests lu);
	
	lu_Tests getTestById (int id);
	
	void updateTest(lu_Tests lu);
	
	void createProcessStatus(lu_ProcessStatus lu);
	
	lu_ProcessStatus getProcessStatusById (int id);
	
	void updateProcessStatus(lu_ProcessStatus lu);
	
	LogoInfo getLogoInfo();
	
	void updateLogoInfo(LogoInfo logoDetails);
}
