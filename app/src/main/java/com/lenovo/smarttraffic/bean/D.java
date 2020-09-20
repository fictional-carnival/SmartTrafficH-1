package com.lenovo.smarttraffic.bean;

import java.util.List;

public class D {

    /**
     * ERRMSG : 成功
     * ROWS_DETAIL : [{"id":0,"metro_code":"1号线","start_place":"苹果园","end_place":"四惠东","img":"images/metro_001.jpg","Start_place_start_time":"05:10:00","Start_place_end_time":"23:30:00","End_place_start_time":"05:05:00","End_place_end_time":"23:30:00","route":["苹果园","古城","八角游乐园","八宝山","玉泉路","天安门西","天安门东","四惠东","复兴门","建国门"]},{"id":1,"metro_code":"2号线","start_place":"西直门","end_place":"积水潭","img":"images/metro_002.jpg","Start_place_start_time":"05:10:00","Start_place_end_time":"22:15:00","End_place_start_time":"05:34:00","End_place_end_time":"23:00:00","route":["西直门","车公庄","阜成门","复兴门","长椿街","宣武门","积水潭","鼓楼大街"]},{"id":2,"metro_code":"4号线","start_place":"安河桥北","end_place":"天宫院","img":"images/metro_004.jpg","Start_place_start_time":"05:00:00","Start_place_end_time":"22:20:00","End_place_start_time":"05:30:00","End_place_end_time":"22:38:00","route":["安河桥北","北宫门","西苑","圆明园","北京大学东门","中关村","北京南站","天宫院"]},{"id":3,"metro_code":"5号线","start_place":"宋家庄","end_place":"天通苑北","img":"images/metro_005.jpg","Start_place_start_time":"05:00:00","Start_place_end_time":"22:48:00","End_place_start_time":"05:20:00","End_place_end_time":"23:11:00","route":["宋家庄","天坛东门","磁器口","东单","雍和宫","天通苑","天通苑北"]},{"id":4,"metro_code":"6号线","start_place":"海淀五路居","end_place":"潞城","img":"images/metro_006.jpg","Start_place_start_time":"05:08:00","Start_place_end_time":"22:25:00","End_place_start_time":"05:42:00","End_place_end_time":"22:49:00","route":["海淀五路居","慈寿寺","花园桥","白石桥南","车公庄西","车公庄","潞城"]},{"id":5,"metro_code":"7号线","start_place":"北京西","end_place":"焦化厂","img":"images/metro_007.jpg","Start_place_start_time":"05:30:00","Start_place_end_time":"23:15:00","End_place_start_time":"05:10:00","End_place_end_time":"22:25:00","route":["北京西","菜市口","珠市口","九龙山","化工","焦化厂"]},{"id":6,"metro_code":"8号线北段","start_place":"朱辛庄 ","end_place":"中国美术馆","img":"images/metro_008_north.jpg","Start_place_start_time":"05:10:00","Start_place_end_time":"22:05:00","End_place_start_time":"05:27:00","End_place_end_time":"23:05:00","route":["朱辛庄","育知路","回龙观","奥林匹克公园","中国美术馆"]},{"id":7,"metro_code":"8号线南段","start_place":"珠市口","end_place":"瀛海","img":"images/metro_008_south.jpg","Start_place_start_time":"05:43:00","Start_place_end_time":"23:28:00","End_place_start_time":"05:15:00","End_place_end_time":"23:00:00","route":["珠市口","奥体中心","安华桥","鼓楼大街","瀛海"]}]
     * RESULT : S
     */

    private String ERRMSG;
    private String RESULT;
    private List<ROWSDETAILBean> ROWS_DETAIL;

    public String getERRMSG() {
        return ERRMSG;
    }

    public void setERRMSG(String ERRMSG) {
        this.ERRMSG = ERRMSG;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public List<ROWSDETAILBean> getROWS_DETAIL() {
        return ROWS_DETAIL;
    }

    public void setROWS_DETAIL(List<ROWSDETAILBean> ROWS_DETAIL) {
        this.ROWS_DETAIL = ROWS_DETAIL;
    }

    public static class ROWSDETAILBean {
        /**
         * id : 0
         * metro_code : 1号线
         * start_place : 苹果园
         * end_place : 四惠东
         * img : images/metro_001.jpg
         * Start_place_start_time : 05:10:00
         * Start_place_end_time : 23:30:00
         * End_place_start_time : 05:05:00
         * End_place_end_time : 23:30:00
         * route : ["苹果园","古城","八角游乐园","八宝山","玉泉路","天安门西","天安门东","四惠东","复兴门","建国门"]
         */

        private int id;
        private String metro_code;
        private String start_place;
        private String end_place;
        private String img;
        private String Start_place_start_time;
        private String Start_place_end_time;
        private String End_place_start_time;
        private String End_place_end_time;
        private List<String> route;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMetro_code() {
            return metro_code;
        }

        public void setMetro_code(String metro_code) {
            this.metro_code = metro_code;
        }

        public String getStart_place() {
            return start_place;
        }

        public void setStart_place(String start_place) {
            this.start_place = start_place;
        }

        public String getEnd_place() {
            return end_place;
        }

        public void setEnd_place(String end_place) {
            this.end_place = end_place;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getStart_place_start_time() {
            return Start_place_start_time;
        }

        public void setStart_place_start_time(String Start_place_start_time) {
            this.Start_place_start_time = Start_place_start_time;
        }

        public String getStart_place_end_time() {
            return Start_place_end_time;
        }

        public void setStart_place_end_time(String Start_place_end_time) {
            this.Start_place_end_time = Start_place_end_time;
        }

        public String getEnd_place_start_time() {
            return End_place_start_time;
        }

        public void setEnd_place_start_time(String End_place_start_time) {
            this.End_place_start_time = End_place_start_time;
        }

        public String getEnd_place_end_time() {
            return End_place_end_time;
        }

        public void setEnd_place_end_time(String End_place_end_time) {
            this.End_place_end_time = End_place_end_time;
        }

        public List<String> getRoute() {
            return route;
        }

        public void setRoute(List<String> route) {
            this.route = route;
        }
    }
}
