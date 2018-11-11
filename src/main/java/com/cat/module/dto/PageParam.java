package com.cat.module.dto;

import com.cat.web.BaseController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * Created by jxli on 2018/11/2.
 */
public class PageParam {

  private int pageNum;
  private int pageSize;

  public int getPageNum() {
    if (this.pageNum > 0) {
      return this.pageNum;
    }
    return Integer.valueOf(BaseController.DEFAULT_PAGE_NUM);
  }

  /**
   * @Description jpa 第一页从0开始
   * @param
   * @return int
   */
  public int getPageNumForJpa(){
    return getPageNum() -1 ;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

  public int getPageSize() {
    if (this.pageSize > 0) {
      return this.pageSize;
    }
    return Integer.valueOf(BaseController.DEFAULT_PAGE_SIZE);
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }



  @Override
  public String toString() {
    return "CatPageRequest{" +
        "pageNum=" + pageNum +
        ", pageSize=" + pageSize +
        '}';
  }
}
