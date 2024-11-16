package com.easylearn.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName options
 */
@TableName(value ="options")
@Data
public class Options implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer oid;

    /**
     * 
     */
    private Integer qid;

    /**
     * 
     */
    private String op;

    /**
     * 
     */
    private Integer pos;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Options other = (Options) that;
        return (this.getOid() == null ? other.getOid() == null : this.getOid().equals(other.getOid()))
            && (this.getQid() == null ? other.getQid() == null : this.getQid().equals(other.getQid()))
            && (this.getOp() == null ? other.getOp() == null : this.getOp().equals(other.getOp()))
            && (this.getPos() == null ? other.getPos() == null : this.getPos().equals(other.getPos()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOid() == null) ? 0 : getOid().hashCode());
        result = prime * result + ((getQid() == null) ? 0 : getQid().hashCode());
        result = prime * result + ((getOp() == null) ? 0 : getOp().hashCode());
        result = prime * result + ((getPos() == null) ? 0 : getPos().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", oid=").append(oid);
        sb.append(", qid=").append(qid);
        sb.append(", op=").append(op);
        sb.append(", pos=").append(pos);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}