package com.exercise.scregistration.db.vo;

import java.util.List;

public class PaginatedResults<T> {
	
	private long totalCount;
	
	private List<T> results = null;
	
	public PaginatedResults(long totalCount, List<T> results){
		this.results = results;
		this.totalCount = totalCount;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

}
