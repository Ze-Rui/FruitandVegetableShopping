package com.example.shopping.home.bean;

import java.util.List;

public class ResultBeanData {

    private int code;
    private String message;
    private ResultBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getData() {
        return data;
    }

    public void setData(ResultBean data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "ResultBeanData{" +
                "code=" + code +
                ", msg='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class ResultBean {
        private List<ActInfoBean> act_info;
        private List<BannerInfoBean> banner_info;
        private List<ChannelInfoBean> channel_info;
        private List<RecommendInfoBean> recommend_info;


        public List<ActInfoBean> getAct_info() {
            return act_info;
        }

        public void setAct_info(List<ActInfoBean> act_info) {
            this.act_info = act_info;
        }

        public List<BannerInfoBean> getBanner_info() {
            return banner_info;
        }

        public void setBanner_info(List<BannerInfoBean> banner_info) {
            this.banner_info = banner_info;
        }

        public List<ChannelInfoBean> getChannel_info() {
            return channel_info;
        }

        public void setChannel_info(List<ChannelInfoBean> channel_info) {
            this.channel_info = channel_info;
        }

        public List<RecommendInfoBean> getRecommend_info() {
            return recommend_info;
        }

        public void setRecommend_info(List<RecommendInfoBean> recommend_info) {
            this.recommend_info = recommend_info;
        }

        public static class ActInfoBean {

            private int actId;
            private String icon_url;
            private String name;
            private String url;

            public int getActId() {
                return actId;
            }

            public void setActId(int actId) {
                this.actId = actId;
            }

            public String getIcon_url() {
                return icon_url;
            }

            public void setIcon_url(String icon_url) {
                this.icon_url = icon_url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public String toString() {
                return "ActInfoBean{" +
                        "actId=" + actId +
                        ", icon_url='" + icon_url + '\'' +
                        ", name='" + name + '\'' +
                        ", url='" + url + '\'' +
                        '}';
            }
        }

        public static class BannerInfoBean {
            private int bannerId;
            private String image;
            private int option;
            private int type;
            private String url;

            public int getBannerId() {
                return bannerId;
            }

            public void setBannerId(int bannerId) {
                this.bannerId = bannerId;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getOption() {
                return option;
            }

            public void setOption(int option) {
                this.option = option;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public String toString() {
                return "BannerInfoBean{" +
                        "bannerId=" + bannerId +
                        ", image='" + image + '\'' +
                        ", option=" + option +
                        ", type=" + type +
                        ", url='" + url + '\'' +
                        '}';
            }
        }

        public static class ChannelInfoBean {
            private int channelId;
            private String channel_name;
            private String image;
            private int option;
            private int type;

            public int getChannelId() {
                return channelId;
            }

            public void setChannelId(int channelId) {
                this.channelId = channelId;
            }
            public String getChannel_name() {
                return channel_name;
            }

            public void setChannel_name(String channel_name) {
                this.channel_name = channel_name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getOption() {
                return option;
            }

            public void setOption(int option) {
                this.option = option;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }


            @Override
            public String toString() {
                return "ChannelInfoBean{" +
                        "channelId=" + channelId +
                        ", channel_name='" + channel_name + '\'' +
                        ", image='" + image + '\'' +
                        ", option=" + option +
                        ", type=" + type +
                        '}';
            }
        }

        public static class RecommendInfoBean {

            private int recommendId;
            private String cover_price;
            private String figure;
            private String name;
            private String product_id;

            public int getRecommendId() {
                return recommendId;
            }

            public void setRecommendId(int recommendId) {
                this.recommendId = recommendId;
            }

            public String getCover_price() {
                return cover_price;
            }

            public void setCover_price(String cover_price) {
                this.cover_price = cover_price;
            }

            public String getFigure() {
                return figure;
            }

            public void setFigure(String figure) {
                this.figure = figure;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

        }

    }
}
