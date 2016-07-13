package com.example.a10648.v2ex.utils.database;

/**
 * Created by 李争 on 2016/7/12 0012.
 */
public class Column {

    private String mColumnName;
    private Constraint mConstraint;  //数据表“约束”
    private DataType mDataType;  //数据类型

    public Column(String mColumnName, Constraint mConstraint, DataType mDataType) {
        this.mColumnName = mColumnName;
        this.mConstraint = mConstraint;
        this.mDataType = mDataType;
    }


    public String getColumnName() {
        return mColumnName;
    }

    public Constraint getConstraint() {
        return mConstraint;
    }

    public DataType getDataType() {
        return mDataType;
    }


    public static enum DataType{
        NULL, INTEGER, REAL, TEXT, BLOB
    }

    public static enum Constraint {
        UNIQUE("UNIQUE"),
        NOT("NOT"),
        NULL("NULL"),
        CHECK("CHECK"),
        FOREIGN_KEY("FOREIGN_KEY"),
        PRIMARY_KEY("PRIMARY_KEY"),
        PRIMARY_KEY_AUTOINCREMENT("PRIMARY_KEY_AUTOINCREMENT");

       private String mValue;

        private Constraint (String value) {
            mValue = value;
        }

        @Override
        public String toString() {
            return mValue;
        }
    }


}
