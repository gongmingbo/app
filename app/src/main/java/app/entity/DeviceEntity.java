package app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import app.config.JsonDataUserType;

@Entity
@Table(name = "device")
@TypeDef(name = "JsonDataUserType", typeClass = JsonDataUserType.class)
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "deviceNum")
    private String deviceNum;

    @Column(name = "jsonb")
    @Type(type = "JsonDataUserType")
    private String jsonb;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(String deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getJsonb() {
        return jsonb;
    }

    public void setJsonb(String jsonb) {
        this.jsonb = jsonb;
    }
}

