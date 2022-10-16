package eapli.base.infrastructure.spomsp;

public class CommProtocol {

    public static final byte[] COMM_TEST_V1 = {1,0,0,0};
    public static final byte[] DISCONN_TEST_V1 = {1,1,0,0};
    public static final byte[] ACK_MESSAGE_V1 = {1,2,0,0};
    public static final byte[] ASK_AVAILABLE_AGV_LIST = {1,8,0,0};
    public static final byte[] ASK_ALL_AGV_LIST = {1,10,0,0};
    public static final int PROTOCOL_V1 = 1;

    public static final int COMM_TEST_CODE = 0;
    public static final int DISCONN_CODE = 1;
    public static final int ACK_CODE = 2;
    public static final int ACK_CODE_ERROR=17;
    public static final int TASKASSIGN_CODE = 3;
    public static final int AVAILAGV_CODE = 4;
    public static final int ASSIGNAGV_CODE = 5;
    public static final int STATUSUPD_CODE = 6;
    public static final int ORDERUPD_CODE = 7;
    public static final int ASK_AVAILABLE_AGV_LIST_CODE = 8;
    public static final int AVAILABLE_AGV_LIST_CODE = 9;
    public static final int ASK_ALL_AGV_LIST_CODE = 10;
    public static final int ALL_AGV_LIST_CODE = 11;
    public static final int CART_ADD_CODE =12;
    public static final int CART_REMOVE_CODE =13;
    public static final int CUSTOMER_ID_CODE=14;
    public static final int CATALOG_CODE=15;
    public static final int CATEGORIES_CODE=16;
    public static final int SHOPCHART_CODE=18;
    public static final int CLIENT_ORDERS_CODE=19;
    public static final int ORDER_DETAILED_INFO_CODE = 20;

    public static final int NR_SURVEYS_REQUEST = 21;
    public static final int NR_SURVEYS_REPLY = 22;

    public static final int LIST_SURVEYS_REQUEST = 23;

    public static final int LIST_SURVEYS_REPLY = 24;

    public static final int SAVE_ANSWERS = 25;

    public static final int WAREHOUSE_REQUEST = 26;
    public static final int WAREHOUSE_REPLY = 27;

    public static int[] dataSizeCalculator(String data){
        int dataSize = data.length();

        if(dataSize<256)
            return new int[]{dataSize, 0};

        int times256 = 0;
        while(dataSize > 255){
            times256++;
            dataSize-=256;
        }

        return new int[]{dataSize, times256};
    }

}
