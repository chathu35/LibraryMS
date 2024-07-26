package org.example.bo;

import org.example.bo.custom.impl.*;

public class BOFactory {
    public static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getBoFactory(){
        return (boFactory==null) ? boFactory=new BOFactory() : boFactory ;
    }

    public enum BOType {
        BOOK,USER,BRANCH,TRANSACTION,LOGIN,CATEGORY;
    }

    public SuperBO getBO(BOType boType){
        switch (boType){
            case BOOK :
                return new BookBOImpl();
            case USER:
                return new UserBOImpl();
            case BRANCH:
                return new BranchBOImpl();
            case TRANSACTION:
                return new TransactionBOImpl();
            case LOGIN:
                return new UserLoginBOImpl();
            case CATEGORY:
                return new CategoryBOImpl();
            default:
                return null;
        }
    }
}
