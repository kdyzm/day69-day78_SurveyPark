package com.kdyzm.service;

import com.kdyzm.domain.Page;

public interface PageService {

	void savePage(Page model);

	void deletePage(Page model);

	Page getPage(Integer pageId);

	void updatePage(Page page);

	boolean isLastPage(Page page);

	boolean isFirstPage(Page page);

	Page getNextPage(Page newPage);

	Page getPrePage(Page newPage);

	void addNewPage(Page copyPage);
	
}
