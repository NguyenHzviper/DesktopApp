package services;

public class ThoiTrangRssService extends BaseRssService {

	@Override
	public String getRssUrl() {
		return "https://vnexpress.net/rss/cuoi.rss";
	}
}
