/**
 * 
 */
/**
 * @author Administrator
 *
 */
package com.fh.service.system.assignTraining;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.Training;
import com.fh.util.PageData;

/** 用户接口类
 * @author chima
 */
public interface AssignTrainingManager{
	

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
	public void editStatus(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> historyList(Page page)throws Exception;
	
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	
}

