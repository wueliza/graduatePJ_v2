package com.project.graduatepj;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RESTfulApi {
    @GET("patient")
    Call<List<Patient_Api>> getPosts();

    @GET("patient/{qrChart}") //1 . 2 . 3 ... 病人的api
    Call<Patient_Api> getOne(@Path("qrChart")String qrChart);

    @GET("staff/{emid}") //A00001 ...
    Call<Staff_Api> get_staff(@Path("emid")String emid);

    @POST("patient")
    Call<Patient_Api> postData(@Body Patient_Api patientApi);

    @GET("eisai/{eisaiNum}")
    Call<Eisai_Api> get_eisai(@Path("eisaiNum")String eisaiNum);

    @GET("ora4chart/{ora4Chart}")
    Call<ORA4_CHART_API> get_ora4Chart(@Path("ora4Chart")String ora4Chart);

    @GET("bloodbank/{blnos}")
    Call<Bloodbank_Api> get_bloodbank(@Path("blnos")String blnos);

    @GET("checkoperation/{bsnos}")
    Call<CheckOperation_Api> get_checkoperation(@Path("bsnos")String bsnos);

    @GET("medicine/{tubg}")
    Call<Medicine_Api> get_medicine(@Path("tubg")String tubg);

    @GET("tpr/{tprnum}")
    Call<Tpr_Api> get_Tpr(@Path("tprnum")String tprnum);

    @GET("tpr/{tprnum}")
    Call<Tprtime_Api> get_tprtime(@Path("tprnum")String tprnum);

    @GET("transoperation/{rqno}")
    Call<TransOperation_Api> get_transoperation(@Path("rqno")String rqno);

    @GET("transoperation")
    Call<List<TransOperation_Api>> get_transoperationquery(@Query("qrChart") String qrChart);

    @POST("BloodBagSignRecord")
    Call<BloodBagSignRecord> post_BloodBagSignRecord(@Body BloodBagSignRecord bloodBagSignRecord);

    @POST("BloodCheckRecord")
    Call<BloodCheckRecord> post_BloodCheckRecord(@Body BloodCheckRecord bloodCheckRecord);

    @POST("CheckOperationRecord")
    Call<CheckOperationRecord> post_CheckOperationRecord(@Body CheckOperationRecord checkOperationRecord);

    @POST("MedCheckRecord")
    Call<MedCheckRecord> post_MedCheckRecord(@Body MedCheckRecord medCheckRecord);

    @POST("MedGiveRecord")
    Call<MedGiveRecord> post_MedGiveRecord(@Body MedGiveRecord medGiveRecord);

    @POST("MedSignRecord")
    Call<MedSignRecord> post_MedSignRecord(@Body MedSignRecord medSignRecord);

    @POST("OperationRecord")
    Call<OperationRecord> post_OperationRecord(@Body OperationRecord operationRecord);

    @POST("Tpr1Record")
    Call<Tpr1Record> post_Tpr1Record(@Body Tpr1Record tpr1Record);

    @POST("Tpr2Record")
    Call<Tpr2Record> post_Tpr2Record(@Body Tpr2Record tpr2Record);

    @POST("Tpr3Record")
    Call<Tpr3Record> post_Tpr3Record(@Body Tpr3Record tpr3Record);

    @POST("TransOperationRecord")
    Call<TransOperationRecord> post_TransOperationRecord(@Body TransOperationRecord transOperationRecord);

    Call<OperationRecord> checkOperationRecord(CheckOperationRecord checkOperationRecord);
}
