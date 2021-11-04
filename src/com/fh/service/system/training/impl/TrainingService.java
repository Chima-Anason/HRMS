package com.fh.service.system.training.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.Role;
import com.fh.entity.system.Training;
import com.fh.util.PageData;
import com.fh.service.system.leave.LeaveManager;
import com.fh.service.system.training.TrainingManager;

/** 用户接口类
 * @author chima
 */
@Service("trainingService")
public class TrainingService implements TrainingManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列出此组下级
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Training> listTrainingToSelect(PageData pd) throws Exception {
		return (List<Training>) dao.findForList("TrainingMapper.listTrainingToSelect", pd);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TrainingMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TrainingMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TrainingMapper.edit", pd);
	}
	
	
	/**修改状态
	 * @param pd
	 * @throws Exception
	 */
	public void editStatus(PageData pd)throws Exception{
		dao.update("TrainingMapper.editStatus", pd);
	}
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TrainingMapper.datalistPage", page);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TrainingMapper.findById", pd);
	}
	
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllT(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TrainingMapper.deleteAllT", ArrayDATA_IDS);
	}
	
	
}

