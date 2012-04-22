/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 *  create table link
	 (
	 	MSG_ID varchar(100) DEFAULT NULL,
	 	LINK varchar(2000) DEFAULT NULL,
		RANK double default 0.0
	 );
 */

package entity;



/**
 *
 * @author sabghosh
 */
public class Link  {
    private String msg_id;
    private String link;
    private Double rank;

    /**
     * @return the msg_id
     */
    public String getMsg_id() {
        return msg_id;
    }

    /**
     * @param msg_id the msg_id to set
     */
    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the rank
     */
    public Double getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(Double rank) {
        this.rank = rank;
    }
}
