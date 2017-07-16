package enterprises.mccollum.home.media.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaSearchResultContainer {

	protected Integer page;
	protected Integer totalResults;
	protected Integer totalPages;
	protected List<MediaMetadata> results = null;
	protected Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public List<MediaMetadata> getResults() {
		return results;
	}

	public void setResults(List<MediaMetadata> results) {
		this.results = results;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
}
