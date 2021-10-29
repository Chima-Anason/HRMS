
package com.fh.service.system.deductionCategory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.SalaryCategory;
import com.fh.service.system.deductionCategory.DeductionCategoryManager;
import com.fh.util.PageData;

/** deduction
 * @author 
 */
@Service("deductionCategoryService")
public class DeductionCategoryService implements DeductionCategoryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	
	/**Get the data through the deduction category name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategory_Name(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DeductionCategoryMapper.findByCategory_Name", pd);
	}
	
	
	
	/**List all deduction category under PID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listDeductionCategoryByParentId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DeductionCategoryMapper.listDeductionCategoryByParentId", pd);
	}
	
	
	/**deduction category list 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> deductionCategorylistPage(Page page)throws Exception{
		return (List<PageData>) dao.findForList("DeductionCategoryMapper.deductionCategorylistPage", page);
	}
	
	
	
	/**Get the data from the deduction category name
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCategoryName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DeductionCategoryMapper.findByCategoryName", pd);
	}
	
	
	
	/**Get the data by ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DeductionCategoryMapper.findById", pd);
	}
	
	/**Save the deduction category
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception{
		dao.save("DeductionCategoryMapper.saveU", pd);
	}
	 
	/**Modify the deduction category
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception{
		dao.update("DeductionCategoryMapper.editU", pd);
	}
	
	/**Delete deduction category
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception{
		dao.delete("DeductionCategoryMapper.deleteU", pd);
	}
	
	/**Batch delete deduction category
	 * @param CAT_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] CAT_IDS)throws Exception{
		dao.delete("DeductionCategoryMapper.deleteAllU", CAT_IDS);
	}
	
	/**deduction category list (all)
	 * @param USER_IDS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllDeductionCategory(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("DeductionCategoryMapper.listAllDeductionCategory", pd);
	}
	
	
	/**Lists of deduction for salary
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SalaryCategory> listAllDeductionsCategory(PageData pd) throws Exception {
		return (List<SalaryCategory>) dao.findForList("DeductionCategoryMapper.listAllDeductionsCategory", pd);
	}
	
	
	/**Get the data through the deduction category name for excel in location
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public String findByCategory_NameForExcel(String string)throws Exception{
		return (String)dao.findForObject("DeductionCategoryMapper.findByCategory_NameForExcel", string);
	}
	
}
