package org.mercury.oschina.tweet.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 动弹实体类
 *
 * @author Mercury
 * @version 1.1 添加语音动弹功能
 * @changed 2014-12-1
 */

public class Tweet implements Parcelable {

    /**
     * author : happut
     * authorid : 812677
     * body : 家里摄像头图像有变化，以为进贼了，结果发现一只大耗子在镜头里。。。你晚上等着 看我跟你大战三百回合！！！！
     * commentCount : 23
     * id : 14836527
     * imgBig : https://static.oschina.net/uploads/space/2017/0801/163225_VjPt_812677.jpg
     * imgSmall : https://static.oschina.net/uploads/space/2017/0801/163225_VjPt_812677_thumb.jpg
     * portrait : https://static.oschina.net/uploads/user/406/812677_50.jpg?t=1404983903000
     * pubDate : 2017-08-01 16:31:32
     */

    private String author;
    private int    authorid;
    private String body;
    private int    commentCount;
    private int    id;
    private String imgBig;
    private String imgSmall;
    private String portrait;
    private String pubDate;

    public Tweet(Parcel dest) {
        author = dest.readString();
        authorid = dest.readInt();
        body = dest.readString();
        commentCount = dest.readInt();
        id = dest.readInt();
        imgBig = dest.readString();
        imgSmall = dest.readString();
        pubDate = dest.readString();
        portrait = dest.readString();
    }

    public Tweet() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeInt(authorid);
        dest.writeString(body);
        dest.writeInt(commentCount);
        dest.writeInt(id);
        dest.writeString(imgBig);
        dest.writeString(imgSmall);
        dest.writeString(pubDate);
        dest.writeString(portrait);
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }

        @Override
        public Tweet createFromParcel(Parcel source) {
            return new Tweet(source);
        }
    };

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgBig() {
        return imgBig;
    }

    public void setImgBig(String imgBig) {
        this.imgBig = imgBig;
    }

    public String getImgSmall() {
        return imgSmall;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    //    public void setLikeUsers(Context contet, TextView likeUser, boolean limit) {
//        // 构造多个超链接的html, 通过选中的位置来获取用户名
//        if (getLikeCount() > 0 && getLikeUser() != null
//                && !getLikeUser().isEmpty()) {
//            likeUser.setVisibility(View.VISIBLE);
//            likeUser.setMovementMethod(LinkMovementMethod.getInstance());
//            likeUser.setFocusable(false);
//            likeUser.setLongClickable(false);
//            likeUser.setText(addClickablePart(contet, limit),
//                    BufferType.SPANNABLE);
//        } else {
//            likeUser.setVisibility(View.GONE);
//            likeUser.setText("");
//        }
//    }
//
//    /**
//     * @param
//     * @return
//     */
//    private SpannableStringBuilder addClickablePart(final Context context,
//                                                    boolean limit) {
//
//        StringBuilder sbBuilder = new StringBuilder();
//        int showCunt = getLikeUser().size();
//        if (limit && showCunt > 4) {
//            showCunt = 4;
//        }
//
//        for (int i = 0; i < showCunt; i++) {
//            sbBuilder.append(getLikeUser().get(i).getName() + "、");
//        }
//
//        String likeUsersStr = sbBuilder
//                .substring(0, sbBuilder.lastIndexOf("、")).toString();
//
//        // 第一个赞图标
//        // ImageSpan span = new ImageSpan(AppContext.getInstance(),
//        // R.drawable.ic_unlike_small);
//        SpannableString spanStr = new SpannableString("");
//        // spanStr.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//
//        SpannableStringBuilder ssb = new SpannableStringBuilder(spanStr);
//        ssb.append(likeUsersStr);
//
//        String[] likeUsers = likeUsersStr.split("、");
//
//        if (likeUsers.length > 0) {
//            // 最后一个
//            for (int i = 0; i < likeUsers.length; i++) {
//                final String name = likeUsers[i];
//                final int start = likeUsersStr.indexOf(name) + spanStr.length();
//                final int index = i;
//                ssb.setSpan(new ClickableSpan() {
//
//                    @Override
//                    public void onClick(View widget) {
//                        User user = getLikeUser().get(index);
//                        Intent intent = new Intent(AppContext.context, UserHomeActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.putExtra(Constant.USER_ID, user);
//                        AppContext.context().startActivity(intent);
//                    }
//
//                    @Override
//                    public void updateDrawState(TextPaint ds) {
//                        super.updateDrawState(ds);
//                        // ds.setColor(R.color.link_color); // 设置文本颜色
//                        // 去掉下划线
//                        ds.setUnderlineText(false);
//                    }
//
//                }, start, start + name.length(), 0);
//            }
//        }
//        if (likeUsers.length < getLikeCount()) {
//            ssb.append("等");
//            int start = ssb.length();
//            String more = getLikeCount() + "人";
//            ssb.append(more);
//            ssb.setSpan(new ClickableSpan() {
//
//                @Override
//                public void onClick(View widget) {
//                    Bundle bundle = new Bundle();
//                    //                    bundle.putInt(BaseListFragment.BUNDLE_KEY_CATALOG,
//                    // getId());
//                    //                    a'sa'd.showSimpleBack(context,
//                    //                            SimpleBackPage.TWEET_LIKE_USER_LIST, bundle);
//                }
//
//                @Override
//                public void updateDrawState(TextPaint ds) {
//                    super.updateDrawState(ds);
//                    // ds.setColor(R.color.link_color); // 设置文本颜色
//                    // 去掉下划线
//                    ds.setUnderlineText(false);
//                }
//
//            }, start, start + more.length(), 0);
//            return ssb.append("觉得很赞");
//        } else {
//            return ssb.append("觉得很赞");
//        }
//    }

}
