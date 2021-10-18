package com.fh.service.system.bank.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.bank.BankManager;
import com.fh.util.PageData;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/** 用户接口类
 * @author chima
 */
@Service("bankService")
public class BankService implements BankManager {

  @Resource(name = "daoSupport")
  private DaoSupport dao;
  
  /**新增
   * @param pd
   * @throws Exception
   */
  public void save(PageData pd)throws Exception{
    dao.save("BankMapper.save", pd);
  }
  
  /**删除
   * @param pd
   * @throws Exception
   */
  public void delete(PageData pd)throws Exception{
    dao.delete("BankMapper.delete", pd);
  }
  
  /**修改
   * @param pd
   * @throws Exception
   */
  public void edit(PageData pd)throws Exception{
    dao.update("BankMapper.edit", pd);
  }
  
  /**列表
   * @param page
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public List<PageData> list(Page page)throws Exception{
    return (List<PageData>)dao.findForList("BankMapper.datalistPage", page);
  }
  
  /**列表(全部)
   * @param pd
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public List<PageData> listAll(PageData pd)throws Exception{
    return (List<PageData>)dao.findForList("BankMapper.listAll", pd);
  }
  
  /**通过id获取数据
   * @param pd
   * @throws Exception
   */
  public PageData findById(PageData pd)throws Exception{
    return (PageData)dao.findForObject("BankMapper.findById", pd);
  }
  
  /**批量删除
   * @param ArrayDATA_IDS
   * @throws Exception
   */
  public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
    dao.delete("BankMapper.deleteAll", ArrayDATA_IDS);
  }
  
  /**绑定用户
   * @param pd
   * @throws Exception
   */
  public void userBinding(PageData pd)throws Exception{
    dao.update("BankMapper.userBinding", pd);
  }
  
  /**Get the data through the Acc_No
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByAcc_No(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BankMapper.findByAcc_No", pd);
	}
  
}

