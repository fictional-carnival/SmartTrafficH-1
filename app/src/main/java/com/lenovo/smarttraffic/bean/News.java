package com.lenovo.smarttraffic.bean;

import java.util.List;

public class News {
    private List<ROWSDETAILBean> ROWS_DETAIL;

    public List<ROWSDETAILBean> getROWS_DETAIL() {
        return ROWS_DETAIL;
    }

    public void setROWS_DETAIL(List<ROWSDETAILBean> ROWS_DETAIL) {
        this.ROWS_DETAIL = ROWS_DETAIL;
    }

    public static class ROWSDETAILBean {
        /**
         * id : 1
         * category : 1
         * title : 福州地铁2号线本月下旬试乘运营
         * content : 福州地铁2号线本月下旬试乘运营，南门兜站施工正在全力冲刺；设8个进出口，换乘跑离约百米
         * createtime : 2019-04-10 08:19:21
         */

        private int id;
        private int category;
        private String title;
        private String content;
        private String createtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }
}
