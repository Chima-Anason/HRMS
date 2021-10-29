
package com.fh.service.system.deductionCategory;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.SalaryCategory;
import com.fh.util.PageData;


/** Deduction category interface class
 * 
 */
public interface DeductionCategoryManager {
	
	/**Get the data through the Deduction category name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategory_Name(PageData pd)throws Exception;
	
	
	
	/**List all Deduction category by parent Id 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listDeductionCategoryByParentId(PageData pd) throws Exception;

	
	/**Room Deduction list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> deductionCategorylistPage(Page page)throws Exception;
	
	
	
	/**Get the data from the Deduction category No
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategoryName(PageData pd)throws Exception;
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**Save the Deduction category
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception;
	
	/**Modify the Deduction
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception;
	
	
	
	/**Delete Deduction category
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception;
	
	/**Batch delete Deduction category
	 * @param CAT_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] CAT_IDS)throws Exception;
	
	/**Deduction category list (all)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllDeductionCategory(PageData pd)throws Exception;
	
	
	/**Lists the Deduction for salary
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<SalaryCategory> listAllDeductionsCategory(PageData pd) throws Exception;
	
	/**Get the data through the Deduction category name for excel
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public String findByCategory_NameForExcel(String string)throws Exception;
	

}
