package io.jrekvs.crawler;

/**
 * 
 * 
 * @author Tommy.z
 *
 */
@SuppressWarnings("restriction")
public class CrawlersAccessManager {
			
	private Crawler crawlers;
	
	public CrawlersAccessManager(){
		this.crawlers = new CrawlerImpl();
	}
	
	public void init_lru_crawler(){
		crawlers.init_lru_crawler();
	}
	
}
