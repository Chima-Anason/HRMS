/**
 * 
 */
/**
 * @author Administrator
 *
 */
package com.fh.service.system.payroll;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Payroll;
import com.fh.util.PageData;

/** 用户接口类
 * @author chima
 */
public interface PayrollManager{
	

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	
	/**修改STATUZ
	 * @param pd
	 * @throws Exception
	 */
	public void accept(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findValueByIdForView(PageData pd)throws Exception;
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	
	/**Lists the payroll
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Payroll> listAllPayrolls(PageData pd) throws Exception;
	
	
	/**列表(全部)
	   * @param pd
	   * @throws Exception
	   */
	  public List<PageData> listForExcel(PageData pd)throws Exception;
	
	
}

