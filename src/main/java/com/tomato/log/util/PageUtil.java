/**
 * 
 */
package com.tomato.log.util;

import com.tomato.framework.log.support.Paging;

/**
 * @author Hunhun
 *
 * 下午1:28:39
 */
public class PageUtil {

	public static int skip(Paging paging){
		if(paging != null){
			return (paging.getCurrentPage() - 1) * paging.getPageSize();
		}
		return 0;
	}
	
	public static int limit(Paging paging){
		if(paging != null){
			return paging.getPageSize();
		}else{
			return Paging.DEFAULT_PAGE_SIZE;
		}
	}
}
