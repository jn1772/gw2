package com.codebytes.web.pageTools;

public class PageState{
	
	public int []pageNumbers;
	public int begin;
	public int end;
	
	public PageState(int pageNo[], int begin, int end) {
		this.pageNumbers = pageNo;
		this.begin = begin;
		this.end = end;
	}
	
	public static PageState controlPagination(Integer pageRequest, int total, int elementsPerPage) {
		
		int[] pageNumbers = new int[11];
		
		int begin = 0, end;
		int totalPages = (int) Math.ceil((double)total/elementsPerPage);
		
		System.out.println("page request = "+pageRequest);
		
		
		if(pageRequest == null) {
			begin = 0;
			end = Math.min(begin + elementsPerPage, total);
			for(int i=0;i<pageNumbers.length;++i)pageNumbers[i] = i+1;
		}else {
			if(pageRequest < 1)pageRequest = 1;
			if(pageRequest > totalPages)pageRequest = totalPages;
		
			if(pageRequest < 6) for(int i=0;i<pageNumbers.length;++i)pageNumbers[i] = i+1;
			else if(pageRequest > totalPages - 6) {
				begin = (pageRequest-1)*elementsPerPage;
				end = Math.min(begin + elementsPerPage, total);
				for(int i=0;i<pageNumbers.length;++i)pageNumbers[i] = totalPages-10+i+1;
			} else {
				for(int i=0;i<pageNumbers.length;++i)pageNumbers[i] = pageRequest-5+i;
			}
			
			begin = (pageRequest-1)*elementsPerPage;
			end = Math.min(begin + elementsPerPage, total);
		}
		
		end = Math.min(begin+elementsPerPage, total);

		if(begin<0)begin=0;
		return new PageState(pageNumbers, begin, end);
	}
}