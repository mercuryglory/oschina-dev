package org.mercury.oschina.explorer.bean;

import java.util.List;

/**
 * Created by Mercury on 2016-08-19 12:57:58.
 */
public class FindUserBean {

    /**
     * code : 1
     * obj_list : [{"from":"宁夏 银川","gender":1,"id":101563,"name":"lion_yang","portrait":"http://static.oschina.net/uploads/user/50/101563_50.jpg","relation":3},{"from":"广东 广州","gender":1,"id":37802,"name":"lionels","portrait":"http://static.oschina.net/uploads/user/18/37802_50.jpg?t=1426155018000","relation":3},{"from":"北京 朝阳","gender":1,"id":86462,"name":"LionelShen","portrait":"http://static.oschina.net/uploads/user/43/86462_50.jpg?t=1455615630000","relation":3},{"from":"北京 朝阳","gender":1,"id":660175,"name":"lionets","portrait":"http://static.oschina.net/uploads/user/330/660175_50.jpg?t=1382499794000","relation":3},{"from":"浙江 杭州","gender":1,"id":557768,"name":"lion1985712","portrait":"http://static.oschina.net/uploads/user/278/557768_50.jpg","relation":3},{"from":"广东 深圳","gender":1,"id":1391648,"name":"LionSword","portrait":"http://static.oschina.net/uploads/user/695/1391648_50.jpg?t=1403374250000","relation":3},{"from":"上海 闸北","gender":1,"id":991,"name":"LION","portrait":"http://static.oschina.net/uploads/user/0/991_50.jpg","relation":3},{"from":"上海 上海","gender":1,"id":54063,"name":"Lionel_hb","portrait":"http://static.oschina.net/uploads/user/27/54063_50.jpg?t=1375237984000","relation":3},{"from":"北京 昌平","gender":1,"id":119140,"name":"Lionel","portrait":"","relation":3},{"from":"浙江 杭州","gender":1,"id":10920,"name":"lionking.cn","portrait":"","relation":3},{"from":"海外 美国","gender":1,"id":146659,"name":"lionvp","portrait":"","relation":3},{"from":"广东 广州","gender":1,"id":131360,"name":"LionelW","portrait":"","relation":3},{"from":"海外 英国","gender":1,"id":121268,"name":"lionfly","portrait":"","relation":3},{"from":"广东 广州","gender":1,"id":55414,"name":"lionssun","portrait":"","relation":3},{"from":"北京 北京","gender":1,"id":1031077,"name":"Lion-在路上","portrait":"http://static.oschina.net/uploads/user/515/1031077_50.jpg?t=1365737875000","relation":3},{"from":"台湾 台中","gender":1,"id":570922,"name":"LionHo","portrait":"http://static.oschina.net/uploads/user/285/570922_50.jpg?t=1413045783000","relation":3},{"from":"浙江 瑞安","gender":1,"id":1035038,"name":"lionok","portrait":"","relation":3},{"from":"","gender":0,"id":2883116,"name":"lion_2","portrait":"http://static.oschina.net/uploads/user/1441/2883116_50.png?t=1471417796000","relation":3},{"from":"山东 潍坊","gender":1,"id":2316526,"name":"lionheartism","portrait":"http://static.oschina.net/uploads/user/1158/2316526_50.jpg?t=1423384025000","relation":3},{"from":"江西 南昌","gender":1,"id":2612264,"name":"lion0108","portrait":"http://static.oschina.net/uploads/user/1306/2612264_50.jpg?t=1457204316000","relation":3}]
     */

    private int code;
    /**
     * from : 宁夏 银川
     * gender : 1
     * id : 101563
     * name : lion_yang
     * portrait : http://static.oschina.net/uploads/user/50/101563_50.jpg
     * relation : 3
     */

    private List<ObjListBean> obj_list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ObjListBean> getObj_list() {
        return obj_list;
    }

    public void setObj_list(List<ObjListBean> obj_list) {
        this.obj_list = obj_list;
    }

    public static class ObjListBean {
        private String from;
        private int gender;
        private int id;
        private String name;
        private String portrait;
        private int relation;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }
    }
}
