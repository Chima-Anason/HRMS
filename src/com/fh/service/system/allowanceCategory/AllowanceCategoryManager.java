
package com.fh.service.system.allowanceCategory;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.SalaryCategory;
import com.fh.util.PageData;


/** allowance category interface class
 * 
 */
public interface AllowanceCategoryManager {
	
	/**Get the data through the allowance category name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategory_Name(PageData pd)throws Exception;
	
	
	/**List all allowance category by parent Id 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllowanceCategoryByParentId(PageData pd) throws Exception;

	
	/**allowance category list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> allowanceCategorylistPage(Page page)throws Exception;
	
	
	
	/**Get the data from the allowance category 
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
	
	/**Save the allowance category
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception;
	
	/**Modify the allowance
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception;
	
	
	
	/**Delete allowance category
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception;
	
	/**Batch delete allowance category
	 * @param ALLOWANCE_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] CAT_IDS)throws Exception;
	
	/**allowance category list (all)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllAllowanceCategory(PageData pd)throws Exception;
	
	
	/**Lists the allowance for salary
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<SalaryCategory> listAllAllowancesCategory(PageData pd) throws Exception;
	
	
	/**Get the data through the building category name for excel
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public String findByCategory_NameForExcel(String string)throws Exception;
	

}
