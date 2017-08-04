package org.mercury.oschina.tweet.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
import java.util.List;

/**
 * 评论实体类
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年10月14日 下午3:29:22
 *
 */

public class Comment implements Parcelable {


	/**
	 * client_type : 0
	 * commentAuthor : 小白小霸王
	 * commentAuthorId : 569120
	 * commentPortrait : https://static.oschina.net/uploads/user/284/569120_50.jpg?t=1385024682000
	 * content : 脸大胸小
	 * id : 14884166
	 * pubDate : 2017-08-04 21:30:28
	 */

	private int client_type;
	private String commentAuthor;
	private int    commentAuthorId;
	private String commentPortrait;
	private String content;
	private int    id;
	private String pubDate;

	private List<Reply> replies;
	private List<Refer> refers;

	@SuppressWarnings("unchecked")
	public Comment(Parcel source) {
		client_type = source.readInt();
		commentAuthor = source.readString();
		commentAuthorId = source.readInt();
		commentPortrait = source.readString();
		content = source.readString();
		content = source.readString();
		id = source.readInt();
		pubDate = source.readString();


		replies = source.readArrayList(Reply.class.getClassLoader());
		refers = source.readArrayList(Refer.class.getClassLoader());
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(client_type);
		dest.writeString(commentAuthor);
		dest.writeInt(commentAuthorId);
		dest.writeString(commentPortrait);
		dest.writeString(content);
		dest.writeInt(id);
		dest.writeString(pubDate);

		dest.writeList(replies);
		dest.writeList(refers);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public int getClient_type() {
		return client_type;
	}

	public void setClient_type(int client_type) {
		this.client_type = client_type;
	}

	public String getCommentAuthor() {
		return commentAuthor;
	}

	public void setCommentAuthor(String commentAuthor) {
		this.commentAuthor = commentAuthor;
	}

	public int getCommentAuthorId() {
		return commentAuthorId;
	}

	public void setCommentAuthorId(int commentAuthorId) {
		this.commentAuthorId = commentAuthorId;
	}

	public String getCommentPortrait() {
		return commentPortrait;
	}

	public void setCommentPortrait(String commentPortrait) {
		this.commentPortrait = commentPortrait;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	public List<Refer> getRefers() {
		return refers;
	}

	public void setRefers(List<Refer> refers) {
		this.refers = refers;
	}

	public static class Reply implements Serializable, Parcelable {
		public String rauthor;
		public String rpubDate;
		public int rauthorId;
		public String rcontent;

		public int getRauthorId() {
			return rauthorId;
		}

		public void setRauthorId(int rauthorId) {
			this.rauthorId = rauthorId;
		}

		public Reply() {
		}

		public Reply(Parcel source) {
			rauthor = source.readString();
			rpubDate = source.readString();
			rauthorId = source.readInt();
			rcontent = source.readString();
		}

		public String getRauthor() {
			return rauthor;
		}
		public void setRauthor(String rauthor) {
			this.rauthor = rauthor;
		}
		public String getRpubDate() {
			return rpubDate;
		}
		public void setRpubDate(String rpubDate) {
			this.rpubDate = rpubDate;
		}
		public String getRcontent() {
			return rcontent;
		}
		public void setRcontent(String rcontent) {
			this.rcontent = rcontent;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(rauthor);
			dest.writeString(rpubDate);
			dest.writeInt(rauthorId);
			dest.writeString(rcontent);
		}

		@Override
		public int describeContents() {
			return 0;
		}

		public static final Creator<Reply> CREATOR = new Creator<Reply>() {

			@Override
			public Reply[] newArray(int size) {
				return new Reply[size];
			}

			@Override
			public Reply createFromParcel(Parcel source) {
				return new Reply(source);
			}
		};

	}

	@XStreamAlias("refer")
	public static class Refer implements Serializable, Parcelable {

		public String refertitle;
		public String referbody;

		public Refer() {
		}

		public Refer(Parcel source) {
			referbody = source.readString();
			refertitle = source.readString();
		}

		public String getRefertitle() {
			return refertitle;
		}
		public void setRefertitle(String refertitle) {
			this.refertitle = refertitle;
		}
		public String getReferbody() {
			return referbody;
		}
		public void setReferbody(String referbody) {
			this.referbody = referbody;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(referbody);
			dest.writeString(refertitle);
		}

		@Override
		public int describeContents() {
			return 0;
		}

		public static final Creator<Refer> CREATOR = new Creator<Refer>() {

			@Override
			public Refer[] newArray(int size) {
				return new Refer[size];
			}

			@Override
			public Refer createFromParcel(Parcel source) {
				return new Refer(source);
			}
		};
	}
	
	public static final Creator<Comment> CREATOR = new Creator<Comment>() {

		@Override
		public Comment[] newArray(int size) {
			return new Comment[size];
		}

		@Override
		public Comment createFromParcel(Parcel source) {
			return new Comment(source);
		}
	};
}
