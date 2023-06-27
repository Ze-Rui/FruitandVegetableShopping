package com.example.shopping.chat.bean;

public class PersonChat {
    /**
     * id
     */
    private int id;
    /**
     * 姓名
     */
    private String seller_name;
    /**
     * 聊天内容
     */
    private String chatMessage;
    /**
     *
     * @return 是否为本人发送
     */
    private String isMeSend;

    private String seller_id;

    private String date;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getChatMessage() {
        return chatMessage;
    }
    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getIsMeSend() {
        return isMeSend;
    }

    public void setIsMeSend(String isMeSend) {
        this.isMeSend = isMeSend;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PersonChat(int id, String seller_name, String chatMessage, String isMeSend, String seller_id, String date) {
        this.id = id;
        this.seller_name = seller_name;
        this.chatMessage = chatMessage;
        this.isMeSend = isMeSend;
        this.seller_id = seller_id;
        this.date = date;
    }

    public PersonChat() {
        super();
    }


    @Override
    public String toString() {
        return "PersonChat{" +
                "id=" + id +
                ", seller_name='" + seller_name + '\'' +
                ", chatMessage='" + chatMessage + '\'' +
                ", isMeSend='" + isMeSend + '\'' +
                ", seller_id='" + seller_id + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
