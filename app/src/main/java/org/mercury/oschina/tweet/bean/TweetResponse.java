package org.mercury.oschina.tweet.bean;

import org.mercury.oschina.bean.base.BaseBean;

import java.util.List;

/**
 * @author Mercury
 *
 * @date 2014年10月10日
 */
public class TweetResponse extends BaseBean{

	private List<Tweet> tweetlist;

	public List<Tweet> getTweetlist() {
		return tweetlist;
	}

	public void setTweetlist(List<Tweet> tweetlist) {
		this.tweetlist = tweetlist;
	}
}
