package com.fh.service.fhoa.myleave;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 请假申请接口
 * 创建人：FH Q313596790
 * @version
 */
public interface MyleaveManager{

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
	
	/**修改STATUZ
	 * @param pd
	 * @throws Exception
	 */
	public void reject(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	
	/**获取总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData getUserLeaveCount(PageData pd)throws Exception;
	
}

