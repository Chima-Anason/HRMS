
package com.fh.service.system.allowanceCategory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.SalaryCategory;
import com.fh.service.system.allowanceCategory.AllowanceCategoryManager;
import com.fh.util.PageData;

/** Allowance
 * @author 
 */
@Service("allowanceCategoryService")
public class AllowanceCategoryService implements AllowanceCategoryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	
	/**Get the data through the allowance category name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategory_Name(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AllowanceCategoryMapper.findByCategory_Name", pd);
	}
	
	
	
	/**List all allowance category under PID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllowanceCategoryByParentId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AllowanceCategoryMapper.listAllowanceCategoryByParentId", pd);
	}
	
	
	/**allowance category list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> allowanceCategorylistPage(Page page)throws Exception{
		return (List<PageData>) dao.findForList("AllowanceCategoryMapper.allowanceCategorylistPage", page);
	}
	
	
	
	/**Get the data from the allowance category name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategoryName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AllowanceCategoryMapper.findByCategoryName", pd);
	}
	
	
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AllowanceCategoryMapper.findById", pd);
	}
	
	/**Save the allowance category
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception{
		dao.save("AllowanceCategoryMapper.saveU", pd);
	}
	 
	/**Modify the allowance category
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception{
		dao.update("AllowanceCategoryMapper.editU", pd);
	}
	
	/**Delete allowance category
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception{
		dao.delete("AllowanceCategoryMapper.deleteU", pd);
	}
	
	/**Batch delete allowance category
	 * @param CAT_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] CAT_IDS)throws Exception{
		dao.delete("AllowanceCategoryMapper.deleteAllU", CAT_IDS);
	}
	
	/**allowance category list (all)
	 * @param CAT_IDS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllAllowanceCategory(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("AllowanceCategoryMapper.listAllAllowanceCategory", pd);
	}
	
	
	/**Lists of allowance for salary
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SalaryCategory> listAllAllowancesCategory(PageData pd) throws Exception {
		return (List<SalaryCategory>) dao.findForList("AllowanceCategoryMapper.listAllAllowancesCategory", pd);
	}
	
	
	/**Get the data through the allowance category name for excel in salary
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public String findByCategory_NameForExcel(String string)throws Exception{
		return (String)dao.findForObject("AllowanceCategoryMapper.findByCategory_NameForExcel", string);
	}
	
	
}
