package com.micromax.hack.model;

/**
 * Created by Rakshith on 28-06-2015.
 */
public class FilePermission {
    private String loginid;
    private String fileid;
     private String _id;
    private String filename;
    private String extension;
    private String securitycode;
    private String time;
    private String status;
    private String createdate;
    private String updatedate;
    private String recieverid;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }
    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getSecuritycode() {
        return securitycode;
    }

    public void setSecuritycode(String securitycode) {
        this.securitycode = securitycode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getRecieverid() {
        return recieverid;
    }

    public void setRecieverid(String recieverid) {
        this.recieverid = recieverid;
    }
}
