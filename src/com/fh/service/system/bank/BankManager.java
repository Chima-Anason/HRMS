package com.fh.service.system.bank;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/** 用户接口类
 * @author chima
 */
public interface BankManager {

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
  
  
  /**通过id获取数据
   * @param pd
   * @throws Exception
   */
  public PageData findByUserId(PageData pd)throws Exception;
  
  /**批量删除
   * @param ArrayDATA_IDS
   * @throws Exception
   */
  public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
  
  /**绑定用户
   * @param pd
   * @throws Exception
   */
  public void userBinding(PageData pd)throws Exception;
  
  
  /**Get the data through the acc number
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByAcc_No(PageData pd)throws Exception;
  
}