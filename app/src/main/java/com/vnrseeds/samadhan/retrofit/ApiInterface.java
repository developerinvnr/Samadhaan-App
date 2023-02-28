package com.vnrseeds.samadhan.retrofit;

import com.vnrseeds.samadhan.parser.addassetdropdownparser.DropDownResponse;
import com.vnrseeds.samadhan.parser.addtoassetconsumalesparser.AddToAssetConsumablesListResponse;
import com.vnrseeds.samadhan.parser.addtoassetperipherallistparser.PeripheralListResponse;
import com.vnrseeds.samadhan.parser.addtoticketparser.AddToTicketResponse;
import com.vnrseeds.samadhan.parser.applicationlistparser.ApplicationResponseParser;
import com.vnrseeds.samadhan.parser.assetcategoryparser.AssetCategoryListResponse;
import com.vnrseeds.samadhan.parser.assetdataparser.AssetDataResponse;
import com.vnrseeds.samadhan.parser.assetlistparser.AssetListResponse;
import com.vnrseeds.samadhan.parser.assetmaintaincestatusparser.MaintainanceStatusResponse;
import com.vnrseeds.samadhan.parser.brandlistparser.BrandListResponse;
import com.vnrseeds.samadhan.parser.brandmodelparser.BrandModelListResponse;
import com.vnrseeds.samadhan.parser.classificationparser.ClassificationResponse;
import com.vnrseeds.samadhan.parser.custodianparser.CustodianResponse;
import com.vnrseeds.samadhan.parser.departmentparser.DepartmentResponse;
import com.vnrseeds.samadhan.parser.deployableassetparser.DeployableAssetResponse;
import com.vnrseeds.samadhan.parser.issuegroupparser.IssueGroupResponse;
import com.vnrseeds.samadhan.parser.issuelistparser.IssueListResponse;
import com.vnrseeds.samadhan.parser.itncreateparser.ITNCreateResponse;
import com.vnrseeds.samadhan.parser.itnlistparser.ITNListResponse;
import com.vnrseeds.samadhan.parser.locationparser.LocationResponse;
import com.vnrseeds.samadhan.parser.loginparser.LoginResponse;
import com.vnrseeds.samadhan.parser.noticeparser.NoticeResponse;
import com.vnrseeds.samadhan.parser.notificationlistparser.NotificationListResponse;
import com.vnrseeds.samadhan.parser.notificationuserparser.NotificationUserResponse;
import com.vnrseeds.samadhan.parser.primarylocparser.PrimaryLocationsResponse;
import com.vnrseeds.samadhan.parser.priorityparser.PriorityResponse;
import com.vnrseeds.samadhan.parser.removableassets.RemovableAssetsResponse;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.secondarylocparser.SecondaryLocationResponse;
import com.vnrseeds.samadhan.parser.sectionparser.SectionResponse;
import com.vnrseeds.samadhan.parser.seviceproviderlistparser.ServiceProviderListResponse;
import com.vnrseeds.samadhan.parser.sitevisitparser.SiteVisitListResponse;
import com.vnrseeds.samadhan.parser.srnlistparser.SRNListResponse;
import com.vnrseeds.samadhan.parser.srnparser.SRNCreateResponse;
import com.vnrseeds.samadhan.parser.storagesectionparser.StorageSectionResponse;
import com.vnrseeds.samadhan.parser.storagetypeparser.StorageTypeResponse;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.parser.submoduleparser.SubModuleListResponse;
import com.vnrseeds.samadhan.parser.ticketassetparser.TicketAssetListResponse;
import com.vnrseeds.samadhan.parser.ticketcreateparser.TicketCreateResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketListResponse;
import com.vnrseeds.samadhan.parser.ticketviewparser.TicketViewResponse;
import com.vnrseeds.samadhan.postclasses.SaveAsset;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> getLoginInfo(
            @Header("Accept") String Token,
            @Field("email") String username, @Field("password") String password,
            @Field("user_firebase_token") String firebase_token
    );

    @POST("assets/storeOrUpdate")
    Call<SubmitSuccessResponse> getChangePasswordInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                      @Query("classification_id") String classification_id, @Query("classification_id") String classification,
                                                      @Query("classification_id") String classificatio);

    @GET("asset-categories")
    Call<AssetCategoryListResponse> getAssetCategoryInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                         @Query("classification_id") String classification_id, @Query("action") String action,
                                                         @Query("used_in_ac_id") String used_in_ac_id);

    @GET("application-softwares")
    Call<ApplicationResponseParser> getAssetApplicationInfo(@Header("Accept") String header1, @Header("Authorization") String header2);

    @GET("asset-classifications")
    Call<ClassificationResponse> getClassificationInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                       @Query("action") String action);

    @GET("assets/create")
    Call<DropDownResponse> getDropDownInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                           @Query("ac_id") String ac_id);

    @GET("brand/models")
    Call<BrandModelListResponse> getModelInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                              @Query("ac_id") String ac_id, @Query("brand_id") String brand_id);

    @GET("location/departments")
    Call<DepartmentResponse> getDeptListInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                             @Query("location_id") String loc_id);

    @GET("location/departments")
    Call<DepartmentResponse> getDeptListInfoBYOD(@Header("Accept") String header1, @Header("Authorization") String header2,
                                             @Query("location_id") String loc_id, @Query("action") String action);

    @GET("location/department/sections")
    Call<SectionResponse> getSectionInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                         @Query("location_id") String loc_id, @Query("department_id") String dept_id);

    @GET("section/users")
    Call<CustodianResponse> getCustodianInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                             @Query("section_id") String section_id, @Query("except_user_id") String userid);

    @GET("location/users")
    Call<CustodianResponse> getCustodianLocInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                             @Query("location_id") String loc_id);
    @GET("locations")
    Call<LocationResponse> getLocationListInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                               @Query("action") String ticketFor);

    @GET("brands")
    Call<BrandListResponse> getBrandsListInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                              @Query("ac_id") String ac_id);

    @GET("tickets/getTicketRaiseData")
    Call<TicketAssetListResponse> getTicketRaiseDataInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                         @Query("ticket_raise_user_type") String ticketFor, @Query("ticket_raise_by") String raisedByID);

    @GET("tickets/create")
    Call<TicketCreateResponse> getTicketCreateDataInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                       @Query("ticket_action") String ticketFor, @Query("service_type") String service_type,
                                                       @Query("service_type_id") String service_type_id);

    @GET("application-sub-modules")
    Call<SubModuleListResponse> getSubModuleDataInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                     @Query("module_id") String module_id);

    @GET("tickets/getTicketIssues")
    Call<IssueListResponse> getIssueListDataInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                 @Query("service_type") String service_type, @Query("service_type_id") String service_type_id,
                                                 @Query("ticket_id") String ticket_id, @Query("user_id") String user_id,
                                                 @Query("ticket_module_id") String ticket_module_id, @Query("ticket_sub_module_id") String ticket_sub_module_id);

    @GET("tickets/getTicketServiceProvider")
    Call<ServiceProviderListResponse> getServiceProviderListInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                                 @Query("ticket_id") String service_type);

    @Multipart
    @POST("assets/storeOrUpdate")
    Call<SubmitSuccessResponse> getSubmitInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                              @Part("asset_ac_id") RequestBody asset_category_id, @Part("asset_type") RequestBody asset_type,
                                              @Part("asset_brand_id") RequestBody cpubrand_id, @Part("asset_model_id") RequestBody cpumodel_id,
                                              @Part("asset_serial_no") RequestBody cpusrno, @Part("asset_monitor_brand_id") RequestBody monitorbrand_id,
                                              @Part("asset_monitor_model_id") RequestBody monitormodel_id, @Part("asset_monitor_serial_no") RequestBody monitorsrno,
                                              @Part("asset_keyboard_type") RequestBody keyboardtype, @Part("asset_keyboard_brand_id") RequestBody keybrand_id,
                                              @Part("asset_keyboard_model_id") RequestBody keyboardmodel_id, @Part("asset_keyboard_serial_no") RequestBody keyboardsrno,
                                              @Part("asset_mouse_type") RequestBody mousetype, @Part("asset_mouse_brand_id") RequestBody mousebrand_id, @Part("asset_mouse_model_id") RequestBody mousemodel_id,
                                              @Part("asset_mouse_serial_no") RequestBody mousesrno, @Part("asset_processor_id") RequestBody processor_id, @Part("asset_ram_id") RequestBody ramtype_id,
                                              @Part("asset_rc_id") RequestBody ramcapacity_id, @Part("asset_hard_disk_type") RequestBody hddtype, @Part("asset_hdd_capacity") RequestBody hddingb,
                                              @Part("asset_ssd_capacity") RequestBody sddingb, @Part("asset_invoice_date") RequestBody purchasedate, @Part("asset_invoice_no") RequestBody invoiceno,
                                              @Part("asset_vendor_id") RequestBody vendor_id, @Part("asset_price") RequestBody price, @Part("asset_warranty_upto") RequestBody cpuwarrantyupto,
                                              @Part("asset_monitor_warranty_upto") RequestBody monitorwarrantyupto, @Part("asset_keyboard_warranty_upto") RequestBody keywarrantyupto,
                                              @Part("asset_mouse_warranty_upto") RequestBody mousewarrantyupto, @Part("asset_is_eol") RequestBody eolvalue, @Part("asset_eol_warranty_upto") RequestBody eolwarranty_date,
                                              @Part("asset_installation_date") RequestBody installationdate, @Part("asset_installation_location_id") RequestBody selloc_id, @Part("asset_installation_department_id") RequestBody seldept_id,
                                              @Part("asset_installation_section_id") RequestBody selsection_id, @Part("asset_installation_user_ids") RequestBody custodian_id,
                                              @Part("asset_nt_id") RequestBody nw_id, @Part("asset_mac_ethernet_address") RequestBody maethernet, @Part("asset_mac_wifi_address") RequestBody mawifi,
                                              @Part("asset_connection_type") RequestBody connectiontype, @Part("asset_ip_address") RequestBody ipaddress, @Part("asset_subnet_mask_address") RequestBody subnetmask,
                                              @Part("asset_gateway_address") RequestBody gateway, @Part("asset_os_id") RequestBody os_id, @Part("asset_os_is_updated") RequestBody osislic,
                                              @Part("asset_antivirus_id") RequestBody antivirus_id, @Part("asset_antivirus_serial_no") RequestBody avsrno,
                                              @Part("asset_antivirus_installation_date") RequestBody avinstall_date, @Part("asset_antivirus_renewal_date") RequestBody avrenew_date,
                                              @Part("asset_browsers") RequestBody browsername, @Part("asset_is_office") RequestBody officeval, @Part("asset_office_type") RequestBody officename,
                                              @Part("asset_office_is_updated") RequestBody officeupdateval, @Part("asset_other_softwares") RequestBody sw_id,
                                              @Part("asset_remarks") RequestBody remark, @Part("asset_status") RequestBody statusval, @Part("asset_office_version") RequestBody officeverion,
                                              @Part("asset_hdd_storage") RequestBody asset_hdd_storage, @Part("asset_ssd_storage") RequestBody asset_ssd_storage,
                                              @Part("asset_hdd_capacity_secondary") RequestBody hddingb_sec, @Part("asset_ssd_capacity_secondary") RequestBody sddingb_sec,
                                              @Part("asset_hdd_storage_secondary") RequestBody asset_hdd_storage_sec, @Part("asset_ssd_storage_secondary") RequestBody asset_ssd_storage_sec,
                                              @Part("asset_precise_location") RequestBody precise_loc, @Part("asset_monitor_size") RequestBody monitorscrsize, @Part List<MultipartBody.Part> files);

    @GET("assets")
    Call<AssetListResponse> getAssetListInfo(@Header("Accept") String header1, @Header("Authorization") String header2);

    @GET("assets/getDeployableAssets")
    Call<DeployableAssetResponse> getStoreAssetListInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                        @Query("ac_id") String ac_id);

    @GET("tickets")
    Call<TicketListResponse> getTicketListInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                               @Query("search_ticket_status") String ticketStatus, @Query("search_ticket_except_status") String exceptStatus);

    @GET("assets/edit")
    Call<AssetDataResponse> getAssetInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                         @Query("asset_id") String ac_id);

    @Multipart
    @POST("assets/storeOrUpdate")
    Call<SubmitSuccessResponse> getUpdateInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                              @Part("asset_id") RequestBody asset_id, @Part("asset_type") RequestBody asset_type,
                                              @Part("asset_brand_id") RequestBody cpubrand_id, @Part("asset_model_id") RequestBody cpumodel_id,
                                              @Part("asset_serial_no") RequestBody cpusrno, @Part("asset_monitor_brand_id") RequestBody monitorbrand_id,
                                              @Part("asset_monitor_model_id") RequestBody monitormodel_id, @Part("asset_monitor_serial_no") RequestBody monitorsrno,
                                              @Part("asset_keyboard_type") RequestBody keyboardtype, @Part("asset_keyboard_brand_id") RequestBody keybrand_id,
                                              @Part("asset_keyboard_model_id") RequestBody keyboardmodel_id, @Part("asset_keyboard_serial_no") RequestBody keyboardsrno,
                                              @Part("asset_mouse_type") RequestBody mousetype, @Part("asset_mouse_brand_id") RequestBody mousebrand_id, @Part("asset_mouse_model_id") RequestBody mousemodel_id,
                                              @Part("asset_mouse_serial_no") RequestBody mousesrno, @Part("asset_processor_id") RequestBody processor_id, @Part("asset_ram_id") RequestBody ramtype_id,
                                              @Part("asset_rc_id") RequestBody ramcapacity_id, @Part("asset_hard_disk_type") RequestBody hddtype, @Part("asset_hdd_capacity") RequestBody hddingb,
                                              @Part("asset_ssd_capacity") RequestBody sddingb, @Part("asset_invoice_date") RequestBody purchasedate, @Part("asset_invoice_no") RequestBody invoiceno,
                                              @Part("asset_vendor_id") RequestBody vendor_id, @Part("asset_price") RequestBody price, @Part("asset_warranty_upto") RequestBody cpuwarrantyupto,
                                              @Part("asset_monitor_warranty_upto") RequestBody monitorwarrantyupto, @Part("asset_keyboard_warranty_upto") RequestBody keywarrantyupto,
                                              @Part("asset_mouse_warranty_upto") RequestBody mousewarrantyupto, @Part("asset_is_eol") RequestBody eolvalue, @Part("asset_eol_warranty_upto") RequestBody eolwarranty_date,
                                              @Part("asset_installation_date") RequestBody installationdate, @Part("asset_installation_location_id") RequestBody selloc_id, @Part("asset_installation_department_id") RequestBody seldept_id,
                                              @Part("asset_installation_section_id") RequestBody selsection_id, @Part("asset_installation_user_ids") RequestBody custodian_id,
                                              @Part("asset_nt_id") RequestBody nw_id, @Part("asset_mac_ethernet_address") RequestBody maethernet, @Part("asset_mac_wifi_address") RequestBody mawifi,
                                              @Part("asset_connection_type") RequestBody connectiontype, @Part("asset_ip_address") RequestBody ipaddress, @Part("asset_subnet_mask_address") RequestBody subnetmask,
                                              @Part("asset_gateway_address") RequestBody gateway, @Part("asset_os_id") RequestBody os_id, @Part("asset_os_is_updated") RequestBody osislic,
                                              @Part("asset_antivirus_id") RequestBody antivirus_id, @Part("asset_antivirus_serial_no") RequestBody avsrno,
                                              @Part("asset_antivirus_installation_date") RequestBody avinstall_date, @Part("asset_antivirus_renewal_date") RequestBody avrenew_date,
                                              @Part("asset_browsers") RequestBody browsername, @Part("asset_is_office") RequestBody officeval, @Part("asset_office_type") RequestBody officename,
                                              @Part("asset_office_is_updated") RequestBody officeupdateval, @Part("asset_other_softwares") RequestBody sw_id,
                                              @Part("asset_remarks") RequestBody remark, @Part("asset_status") RequestBody statusval, @Part("asset_office_version") RequestBody officeverion,
                                              @Part("asset_hdd_storage") RequestBody asset_hdd_storage,@Part("asset_ssd_storage") RequestBody asset_ssd_storage,
                                              @Part("asset_hdd_capacity_secondary") RequestBody hddingb_sec, @Part("asset_ssd_capacity_secondary") RequestBody sddingb_sec,
                                              @Part("asset_hdd_storage_secondary") RequestBody asset_hdd_storage_sec, @Part("asset_ssd_storage_secondary") RequestBody asset_ssd_storage_sec,
                                              @Part("asset_ac_id") RequestBody asset_ac_id, @Part("asset_precise_location") RequestBody precise_loc, @Part("asset_monitor_size") RequestBody monitorscrsize,
                                              @Part List<MultipartBody.Part> files);


    @POST("assets/storeOrUpdate")
    Call<SubmitSuccessResponse> getLaptopSubmitInfo(@Header("Accept") String header1, @Header("Authorization") String header2, @Body SaveAsset saveAsset);

    @POST("assets/storeOrUpdate")
    Call<SubmitSuccessResponse> getLaptopUpdateInfo(@Header("Accept") String header1, @Header("Authorization") String header2, @Body SaveAsset saveAsset);

    @Multipart
    @POST("assets/storeOrUpdate")
    Call<String> submitAsset(@Header("Accept") String header1, @Header("Authorization") String header2,@Part List<MultipartBody.Part> files);

    @Multipart
    @POST("assets/storeOrUpdate")
    Call<SubmitSuccessResponse> getPrinterSubmitInfo(@Header("Accept") String s, @Header("Authorization") String s1,
                                                     @Part("asset_ac_id") RequestBody asset_ac_id, @Part("asset_id") RequestBody asset_id1,
                                                     @Part("asset_type") RequestBody asset_type1, @Part("asset_brand_id") RequestBody brand_id1,
                                                     @Part("asset_model_id") RequestBody model_id1, @Part("asset_serial_no") RequestBody srno1,
                                                     @Part("asset_printer_connection_type") RequestBody printertype1, @Part("asset_printer_function") RequestBody printerfunction1,
                                                     @Part("asset_printer_cartridge_no") RequestBody cartridgenumber1, @Part("asset_invoice_date") RequestBody purchasedate1,
                                                     @Part("asset_invoice_no") RequestBody invoiceno1, @Part("asset_vendor_id") RequestBody vendor_id1,
                                                     @Part("asset_price") RequestBody price1, @Part("asset_warranty_upto") RequestBody cpuwarrantyupto1,
                                                     @Part("asset_is_eol") RequestBody eolvalue1, @Part("asset_eol_warranty_upto") RequestBody eolwarranty_date1,
                                                     @Part("asset_installation_date") RequestBody installationdate1, @Part("asset_installation_location_id") RequestBody selloc_id1,
                                                     @Part("asset_installation_department_id") RequestBody seldept_id1, @Part("asset_installation_section_id") RequestBody selsection_id1,
                                                     @Part("asset_installation_user_ids") RequestBody cust_id1, @Part("asset_precise_location") RequestBody precise_loc1,
                                                     @Part("asset_nt_id") RequestBody nw_id1, @Part("asset_mac_ethernet_address") RequestBody maethernet1,
                                                     @Part("asset_mac_wifi_address") RequestBody mawifi1, @Part("asset_connection_type") RequestBody connectiontype1,
                                                     @Part("asset_ip_address") RequestBody ipaddress1, @Part("asset_subnet_mask_address") RequestBody subnetmask1,
                                                     @Part("asset_gateway_address") RequestBody gateway1, @Part("asset_firmware_version") RequestBody firmwareversion1,
                                                     @Part("asset_released_date") RequestBody released_date1, @Part("asset_remarks") RequestBody remark, @Part("asset_status") RequestBody statusval,
                                                     @Part("asset_channel") RequestBody channel1, @Part("asset_storage_capacity") RequestBody storage1, @Part("asset_camera_type") RequestBody camtype1,
                                                     @Part("asset_camera_form_factor") RequestBody camera, @Part List<MultipartBody.Part> files);

    @Multipart
    @POST("assets/storeOrUpdate")
    Call<SubmitSuccessResponse> getUpsSubmitInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                 @Part("asset_ac_id") RequestBody asset_category_id, @Part("asset_type") RequestBody asset_type,
                                                 @Part("asset_brand_id") RequestBody cpubrand_id, @Part("asset_model_id") RequestBody cpumodel_id,
                                                 @Part("asset_serial_no") RequestBody cpusrno, @Part("asset_invoice_date") RequestBody purchasedate,
                                                 @Part("asset_invoice_no") RequestBody invoiceno, @Part("asset_vendor_id") RequestBody vendor_id,
                                                 @Part("asset_price") RequestBody price, @Part("asset_warranty_upto") RequestBody cpuwarrantyupto,
                                                 @Part("asset_is_eol") RequestBody eolvalue, @Part("asset_eol_warranty_upto") RequestBody eolwarranty_date,
                                                 @Part("asset_installation_date") RequestBody installationdate, @Part("asset_installation_location_id") RequestBody selloc_id,
                                                 @Part("asset_installation_department_id") RequestBody seldept_id, @Part("asset_installation_section_id") RequestBody selsection_id,
                                                 @Part("asset_installation_user_ids") RequestBody custodian_id, @Part("asset_remarks") RequestBody remark,
                                                 @Part("asset_status") RequestBody statusval, @Part("asset_precise_location") RequestBody precise_loc,
                                                 @Part("asset_tv_type") RequestBody tvtype1, @Part("asset_tv_is_remote") RequestBody remoteval1,
                                                 @Part("asset_tv_hdmi_ports") RequestBody noofhdmiports1, @Part("asset_tv_screen_size") RequestBody tvscreensize1,
                                                 @Part("asset_ups_capacity") RequestBody upscategory1, @Part("asset_ups_capacity_unit") RequestBody upscategoryunit1,
                                                 @Part List<MultipartBody.Part> files);

    @Multipart
    @POST("assets/storeOrUpdate")
    Call<SubmitSuccessResponse> getModemSubmitInfo(@Header("Accept") String s, @Header("Authorization") String s1,
                                                     @Part("asset_ac_id") RequestBody asset_ac_id, @Part("asset_id") RequestBody asset_id1,
                                                     @Part("asset_type") RequestBody asset_type1, @Part("asset_brand_id") RequestBody brand_id1,
                                                     @Part("asset_model_id") RequestBody model_id1, @Part("asset_serial_no") RequestBody srno1,
                                                     @Part("asset_invoice_date") RequestBody purchasedate1, @Part("asset_invoice_no") RequestBody invoiceno1,
                                                     @Part("asset_vendor_id") RequestBody vendor_id1,
                                                     @Part("asset_price") RequestBody price1, @Part("asset_warranty_upto") RequestBody cpuwarrantyupto1,
                                                     @Part("asset_is_eol") RequestBody eolvalue1, @Part("asset_eol_warranty_upto") RequestBody eolwarranty_date1,
                                                     @Part("asset_installation_date") RequestBody installationdate1, @Part("asset_installation_location_id") RequestBody selloc_id1,
                                                     @Part("asset_installation_department_id") RequestBody seldept_id1, @Part("asset_installation_section_id") RequestBody selsection_id1,
                                                     @Part("asset_installation_user_ids") RequestBody cust_id1, @Part("asset_precise_location") RequestBody precise_loc1,
                                                     @Part("asset_nt_id") RequestBody nw_id1, @Part("asset_mac_ethernet_address") RequestBody maethernet1,
                                                     @Part("asset_mac_wifi_address") RequestBody mawifi1, @Part("asset_connection_type") RequestBody connectiontype1,
                                                     @Part("asset_ip_address") RequestBody ipaddress1, @Part("asset_subnet_mask_address") RequestBody subnetmask1,
                                                     @Part("asset_gateway_address") RequestBody gateway1, @Part("asset_rack_size") RequestBody racksize1,
                                                     @Part("asset_barcode_connection_type") RequestBody brcdconnection_type1, @Part("asset_deskphone_connection_type") RequestBody dphn_contype1,
                                                     @Part("asset_deskphone_type") RequestBody deskphonetype1, @Part("asset_microphone_type") RequestBody mic_contype1,
                                                     @Part("asset_projector_is_remote") RequestBody remotevalue1, @Part("asset_remarks") RequestBody remark1,
                                                     @Part("asset_status") RequestBody statusval1, @Part List<MultipartBody.Part> files);

    @Multipart
    @POST("assets/storeOrUpdate")
    Call<SubmitSuccessResponse> getRouterSubmitInfo(@Header("Accept") String s, @Header("Authorization") String s1,
                                                   @Part("asset_ac_id") RequestBody asset_ac_id, @Part("asset_id") RequestBody asset_id1,
                                                   @Part("asset_type") RequestBody asset_type1, @Part("asset_brand_id") RequestBody brand_id1,
                                                   @Part("asset_model_id") RequestBody model_id1, @Part("asset_serial_no") RequestBody srno1,
                                                   @Part("asset_invoice_date") RequestBody purchasedate1, @Part("asset_invoice_no") RequestBody invoiceno1,
                                                   @Part("asset_vendor_id") RequestBody vendor_id1, @Part("asset_price") RequestBody price1,
                                                   @Part("asset_warranty_upto") RequestBody cpuwarrantyupto1, @Part("asset_is_eol") RequestBody eolvalue1,
                                                   @Part("asset_eol_warranty_upto") RequestBody eolwarranty_date1, @Part("asset_installation_date") RequestBody installationdate1,
                                                   @Part("asset_installation_location_id") RequestBody selloc_id1, @Part("asset_installation_department_id") RequestBody seldept_id1,
                                                   @Part("asset_installation_section_id") RequestBody selsection_id1, @Part("asset_installation_user_ids") RequestBody cust_id1,
                                                   @Part("asset_precise_location") RequestBody precise_loc1, @Part("asset_nt_id") RequestBody nw_id1,
                                                   @Part("asset_mac_ethernet_address") RequestBody maethernet1, @Part("asset_mac_wifi_address") RequestBody mawifi1,
                                                   @Part("asset_connection_type") RequestBody connectiontype1, @Part("asset_ip_address") RequestBody ipaddress1,
                                                   @Part("asset_subnet_mask_address") RequestBody subnetmask1, @Part("asset_gateway_address") RequestBody gateway1,
                                                   @Part("asset_remarks") RequestBody remark1, @Part("asset_status") RequestBody statusval1,
                                                   @Part("asset_port_available") RequestBody availableport1, @Part("asset_port_in_use") RequestBody portinuse1,
                                                   @Part("asset_ethernet_port") RequestBody eth_ports1, @Part("asset_fiber_optic_port") RequestBody fo_ports1,
                                                   @Part("asset_ssid") RequestBody ssid1, @Part("asset_mobile_technology") RequestBody mobiletechvalue1,
                                                   @Part("asset_sim_operator") RequestBody simoperator1, @Part("asset_sim_no") RequestBody simno1,
                                                   @Part("asset_mobile_no") RequestBody mobileno1, @Part List<MultipartBody.Part> files);


    @Multipart
    @POST("assets/storeOrUpdate")
    Call<SubmitSuccessResponse> getSFPSubmitInfo(@Header("Accept") String s, @Header("Authorization") String s1,
                                                    @Part("asset_ac_id") RequestBody asset_ac_id, @Part("asset_id") RequestBody asset_id1,
                                                    @Part("asset_type") RequestBody asset_type1, @Part("asset_brand_id") RequestBody brand_id1,
                                                    @Part("asset_model_id") RequestBody model_id1, @Part("asset_serial_no") RequestBody srno1,
                                                    @Part("asset_invoice_date") RequestBody purchasedate1, @Part("asset_invoice_no") RequestBody invoiceno1,
                                                    @Part("asset_vendor_id") RequestBody vendor_id1, @Part("asset_price") RequestBody price1,
                                                    @Part("asset_warranty_upto") RequestBody cpuwarrantyupto1, @Part("asset_is_eol") RequestBody eolvalue1,
                                                    @Part("asset_eol_warranty_upto") RequestBody eolwarranty_date1, @Part("asset_installation_date") RequestBody installationdate1,
                                                    @Part("asset_installation_location_id") RequestBody selloc_id1, @Part("asset_installation_department_id") RequestBody seldept_id1,
                                                    @Part("asset_installation_section_id") RequestBody selsection_id1, @Part("asset_installation_user_ids") RequestBody cust_id1,
                                                    @Part("asset_precise_location") RequestBody precise_loc1,
                                                    @Part("asset_remarks") RequestBody remark1, @Part("asset_status") RequestBody statusval1,
                                                    @Part("asset_sim_no") RequestBody sfpmode1, @Part("asset_switch_port_type") RequestBody porttype1,
                                                    @Part("asset_speaker_type") RequestBody speakertype1, @Part("asset_speaker_no_of_mic") RequestBody noofmic1,
                                                    @Part("asset_switch_type") RequestBody switchtype1, @Part("asset_switch_lan_port") RequestBody switchlanport1,
                                                    @Part("asset_switch_fiber_port") RequestBody switchfiberport1, @Part("asset_switch_fiber_port") RequestBody speed1,
                                                    @Part("asset_switch_speed_unit") RequestBody speedunit1, @Part("asset_switch_poe_wattage") RequestBody poewattage1,
                                                    @Part List<MultipartBody.Part> files);
    @Multipart
    @POST("assets/storeOrUpdate")
    Call<SubmitSuccessResponse> getTabSubmitInfo(@Header("Accept") String s, @Header("Authorization") String s1,
                                                 @Part("asset_ac_id") RequestBody asset_ac_id, @Part("asset_id") RequestBody asset_id1,
                                                 @Part("asset_type") RequestBody asset_type1, @Part("asset_brand_id") RequestBody brand_id1,
                                                 @Part("asset_model_id") RequestBody model_id1, @Part("asset_serial_no") RequestBody srno1,
                                                 @Part("asset_invoice_date") RequestBody purchasedate1, @Part("asset_invoice_no") RequestBody invoiceno1,
                                                 @Part("asset_vendor_id") RequestBody vendor_id1, @Part("asset_price") RequestBody price1,
                                                 @Part("asset_warranty_upto") RequestBody cpuwarrantyupto1, @Part("asset_is_eol") RequestBody eolvalue1,
                                                 @Part("asset_eol_warranty_upto") RequestBody eolwarranty_date1, @Part("asset_installation_date") RequestBody installationdate1,
                                                 @Part("asset_installation_location_id") RequestBody selloc_id1, @Part("asset_installation_department_id") RequestBody seldept_id1,
                                                 @Part("asset_installation_section_id") RequestBody selsection_id1, @Part("asset_installation_user_ids") RequestBody cust_id1,
                                                 @Part("asset_precise_location") RequestBody precise_loc1, @Part("asset_remarks") RequestBody remark1,
                                                 @Part("asset_status") RequestBody statusval1, @Part("asset_sim_operator") RequestBody simoperator1,
                                                 @Part("asset_sim_no") RequestBody simno1, @Part("asset_mobile_no") RequestBody mobileno1,
                                                 @Part("asset_processor_id") RequestBody processor_id1, @Part("asset_ram_id") RequestBody ramtype_id1,
                                                 @Part("asset_ram_id") RequestBody ramcapacity_id1, @Part("asset_storage_capacity") RequestBody storage1,
                                                 @Part("asset_camera_available") RequestBody cameraavailable1, @Part("asset_sd_card_size") RequestBody sdcardsize1,
                                                 @Part("asset_imei_no_first") RequestBody imeino11, @Part("asset_imei_no_second") RequestBody imeino22,
                                                 @Part("asset_owned_by") RequestBody ownedby1, @Part("asset_screen_size") RequestBody screensize1,
                                                 @Part("asset_color") RequestBody color1, @Part("asset_os_id") RequestBody os_id1,
                                                 @Part List<MultipartBody.Part> list);

    @Multipart
    @POST("tickets/storeOrUpdate")
    Call<SubmitSuccessResponse> getTicketUpdateInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                    @Part("ticket_action") RequestBody action, @Part("ticket_raise_user_type") RequestBody ticketFor,
                                                    @Part("ticket_raise_service_type") RequestBody serviceType, @Part("ticket_raise_asset_id") RequestBody asset_id,
                                                    @Part("ticket_raise_application_id") RequestBody application_id, @Part("ticket_raise_priority_id") RequestBody priority_id,
                                                    @Part("ticket_raise_description") RequestBody description, @Part("ticket_raise_issue_ids") RequestBody issue_id1,
                                                    @Part("ticket_raise_issue_other") RequestBody issue_title1, @Part("ticket_id") RequestBody ticket_id,
                                                    @Part("ticket_issue_other") RequestBody issue_other, @Part("ticket_raise_by") RequestBody raisedByID1,
                                                    @Part("ticket_raise_module_id") RequestBody ticket_raise_module_id, @Part("ticket_raise_sub_module_id") RequestBody ticket_raise_submodule_id,
                                                    @Part List<MultipartBody.Part> list);


    @GET("user/role")
    Call<RoleResponse> getRoleListInfo(@Header("Accept") String header1, @Header("Authorization") String header2);

    @POST("tickets/storeOrUpdate")
    Call<SubmitSuccessResponse> getTicketUpdateAssignInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                          @Query("ticket_action") String assignto, @Query("ticket_id") String ticket_id,
                                                          @Query("ticket_assign_to") String assignto_id, @Query("ticket_assign_description") String message);

    @POST("tickets/storeOrUpdate")
    Call<SubmitSuccessResponse> getTicketUpdateHandleInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                          @Query("ticket_action") String assignto, @Query("ticket_id") String ticket_id,
                                                          @Query("ticket_handle_description") String handle_description, @Query("ticket_estimated_handle_date") String handle_datetime,
                                                          @Query("ticket_is_work_in_progress") String is_wip, @Query("ticket_estimated_handle_description") String estimated_handle_description,
                                                          @Query("cr_title") String cr_title, @Query("cr_description") String cr_description,
                                                          @Query("cr_ticket_closure_remark") String cr_ticket_closure_remark, @Query("ticket_request_type") String ticket_is_change_request,
                                                          @Query("ticket_change_request_status") int ticket_is_hold, @Query("ticket_is_discussion_required") int cr_is_discussion_required,
                                                          @Query("cr_is_finish_in_next_version") int cr_is_finish_in_next_version, @Query("cr_priority_id") String cr_priority_id,
                                                          @Query("ticket_change_request_status") String ticket_change_request_status);

    @POST("tickets/storeOrUpdate")
    Call<SubmitSuccessResponse> getTicketUpdateHoldInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                        @Query("ticket_action") String assignto, @Query("ticket_id") String ticket_id,
                                                        @Query("ticket_handle_description") String handle_description, @Query("cr_title") String cr_title,
                                                        @Query("cr_description") String cr_description, @Query("cr_ticket_closure_remark") String cr_ticket_closure_remark,
                                                        @Query("ticket_is_change_request") int ticket_is_change_request, @Query("ticket_is_hold") int ticket_is_hold,
                                                        @Query("cr_is_discussion_required") int cr_is_discussion_required, @Query("cr_is_finish_in_next_version") int cr_is_finish_in_next_version,
                                                        @Query("cr_priority_id") String cr_priority_id);
    @Multipart
    @POST("tickets/storeOrUpdate")
    Call<SubmitSuccessResponse> getTicketUpdateUserNoteInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                          @Part("ticket_action") RequestBody assignto, @Part("ticket_id") RequestBody ticket_id,
                                                          @Part("ticket_reply_description") RequestBody handle_private_notes, @Part List<MultipartBody.Part> list);

    @POST("tickets/storeOrUpdate")
    Call<SubmitSuccessResponse> getTicketUpdateNoteInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                        @Query("ticket_action") String assignto, @Query("ticket_id") String ticket_id,
                                                        @Query("ticket_internal_note_description") String handle_private_notes,
                                                        @Query("ticket_is_internal_note_for_user") String show_to_user_flg);
    @POST("tickets/storeOrUpdate")
    Call<SubmitSuccessResponse> getTicketReopenInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                        @Query("ticket_action") String action, @Query("ticket_id") String ticket_id,
                                                        @Query("ticket_reopen_description") String handle_private_notes);
    @Multipart
    @POST("tickets/storeOrUpdate")
    Call<SubmitSuccessResponse> getTicketUpdateResolvedInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                            @Part("ticket_action") RequestBody assignto, @Part("ticket_id") RequestBody ticket_id,
                                                            @Part("ticket_resolve_description") RequestBody handle_private_notes, @Part("ticket_is_add_to_asset") RequestBody ticket_is_add_to_asset,
                                                            @Part("ticket_is_site_visit") RequestBody ticket_is_site_visit, @Part("ticket_site_visit_at") RequestBody ticket_site_visit_at,
                                                            @Part List<MultipartBody.Part> list);

    @POST("tickets/storeOrUpdate")
    Call<SubmitSuccessResponse> getTicketUpdateCloseInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                         @Query("ticket_action") String assignto, @Query("ticket_id") String ticket_id,
                                                         @Query("ticket_close_rating") String rating, @Query("ticket_close_description") String handle_private_notes);

    @POST("tickets/storeOrUpdate")
    Call<SubmitSuccessResponse> getTicketCancelInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                    @Query("ticket_action") String action, @Query("ticket_id") String ticketId,
                                                    @Query("ticket_withdraw_description") String message);

    @POST("site-visits/storeOrUpdate")
    Call<SubmitSuccessResponse> setScheduleVisit(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                    @Query("tsv_id") String action, @Query("tsv_ticket_id") String ticketId,
                                                    @Query("tsv_visited_at") String date, @Query("tsv_description") String message);

    @GET("tickets/setViewStatus")
    Call<ResponseBody> setTicketViewStatus(@Header("Accept") String header1, @Header("Authorization") String header2,
                                           @Query("ticket_id") String ticketId);
    @GET("tickets/view")
    Call<TicketViewResponse> getTicketViewInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                               @Query("ticket_id") String ticketId);

    @GET("tickets/getAddtoTickets")
    Call<AddToTicketResponse> getAddToTicketInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                 @Query("ticket_service_type") String servicetype, @Query("ticket_service_type_id") String assetcategory_id,
                                                 @Query("ticket_raise_by") String ticket_raise_by);

    @POST("tickets/storeOrUpdate")
    Call<SubmitSuccessResponse> getTicketUpdateAddToTicketInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                               @Query("ticket_action") String add_to_ticket, @Query("ticket_id") String ticket_id,
                                                               @Query("ticket_add_to_ticket_comment") String ticket_add_to_ticket_comment,@Query("ticket_add_to_ticket_by") String ticket_raise_by);

    @POST("assets/deploy")
    Call<SubmitSuccessResponse> getDeploySubmitInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                    @Query("asset_id") String asset_id, @Query("asset_type") String asset_type,
                                                    @Query("asset_installation_date") String install_date, @Query("asset_installation_location_id") String install_loc,
                                                    @Query("asset_installation_department_id") String install_dept, @Query("asset_installation_section_id") String install_section,
                                                    @Query("asset_installation_user_ids") String cust_id, @Query("asset_precise_location") String precise_loc);

    @GET("location/department/users")
    Call<NotificationUserResponse> getUsersInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                @Query("location_id") String location_id, @Query("department_id") String department_id,
                                                @Query("action") String action);


    @GET("issue-groups")
    Call<IssueGroupResponse> getIssueGroupInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                               @Query("service_type") String service_type, @Query("service_type_id") String service_type_id);

    @POST("notifications/storeOrUpdate")
    Call<SubmitSuccessResponse> getNotificationSubmitInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                          @Query("notification_id") String notification_id, @Query("notification_location_ids") String selloc_id,
                                                          @Query("notification_department_ids") String seldept_id, @Query("notification_user_ids") String custodian_id,
                                                          @Query("notification_subject") String subject, @Query("notification_message") String notification_desc,
                                                          @Query("notification_is_notice") int consider_notice, @Query("notification_service_type") String serviceType,
                                                          @Query("notification_service_type_ids") String category_id, @Query("notification_issue_group_ids") String issueGroup_id,
                                                          @Query("notification_affected_from") String affectedFrom, @Query("notification_affected_to") String affectedTo,
                                                          @Query("notification_visible_from") String visibleFrom, @Query("notification_visible_to") String visibleTo);

    @POST("assets/storeByodAsset")
    Call<SubmitSuccessResponse> getBYODSubmitInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                          @Query("user_id") String user_id, @Query("asset_id") String asset_id,
                                                          @Query("asset_ac_id") String asset_ac_id, @Query("asset_brand_id") String asset_brand_id,
                                                          @Query("asset_model_id") String asset_model_id, @Query("asset_serial_no") String asset_serial_no,
                                                          @Query("asset_remarks") String asset_remarks);


    @GET("notifications/getUserNotifications")
    Call<NotificationListResponse> getNotifications(@Header("Accept") String header1, @Header("Authorization") String header2);

    @GET("notifications")
    Call<NotificationListResponse> getAllNotifications(@Header("Accept") String header1, @Header("Authorization") String header2);

    @GET("notifications/getUserNotices")
    Call<NoticeResponse> getNotice(@Header("Accept") String header1, @Header("Authorization") String header2);

    @GET("notifications/setViewStatus")
    Call<SubmitSuccessResponse> setNotificationViewed(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                         @Query("notification_id") Integer notification_id);

    @GET("itn/create")
    Call<ITNCreateResponse> getITNCreateData(@Header("Accept") String header1, @Header("Authorization") String header2,
                                             @Query("ticket_id") String ticket_id);

    @GET("srn/create")
    Call<SRNCreateResponse> getSRNCreateData(@Header("Accept") String header1, @Header("Authorization") String header2,
                                             @Query("ticket_id") String ticket_id);

    @GET("primary-locations")
    Call<PrimaryLocationsResponse> getPrimaryLocData(@Header("Accept") String header1, @Header("Authorization") String header2);

    @GET("secondary-locations")
    Call<SecondaryLocationResponse> getSecondaryLocData(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                        @Query("primary_location_id") String primary_location_id);

    @GET("storage-types")
    Call<StorageTypeResponse> getStorageTypeData(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                 @Query("secondary_location_id") String primary_location_id);

    @GET("storage-sections")
    Call<StorageSectionResponse> getStorageSectionData(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                       @Query("storage_type_id") String primary_location_id);

    @POST("itn/storeOrUpdate")
    Call<SubmitSuccessResponse> getITNSubmitInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                 @Query("itn_id") String itn_id, @Query("itn_ticket_id") String ticket_id,
                                                 @Query("itn_for") String itn_for, @Query("itn_transfer_to") String transfer_to,
                                                 @Query("itn_send_to") String send_to, @Query("itn_vendor_id") String vendor_id,
                                                 @Query("itn_mode") String itn_mode, @Query("itn_person_name") String itn_person_name,
                                                 @Query("itn_mobile") String itn_mobile, @Query("itn_courier_company") String courier_company,
                                                 @Query("itn_courier_docket_no") String docket_no, @Query("itn_transporter_name") String transporter_name,
                                                 @Query("itn_vehicle_no") String vehicle_no, @Query("itn_driver_name") String driver_name,
                                                 @Query("itn_particular") String particular, @Query("itn_quantity") String quantity,
                                                 @Query("itn_remarks") String remarks, @Query("itn_send_location_id") String selloc);

    @POST("srn/storeOrUpdate")
    Call<SubmitSuccessResponse> getSRNSubmitInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                 @Query("srn_id") String srn_id, @Query("srn_ticket_id") String srn_ticket_id,
                                                 @Query("srn_for") String srn_for, @Query("srn_transfer_to") String srn_transfer_to,
                                                 @Query("srn_from_workshop_id") String workshop_id, @Query("vendor_id") String vendor_id,
                                                 @Query("service_center_id") String service_center_id, @Query("srn_mode") String srn_mode,
                                                 @Query("srn_person_name") String person_name, @Query("srn_mobile") String srn_mobile,
                                                 @Query("srn_courier_company") String courier_company, @Query("srn_courier_docket_no") String docket_no,
                                                 @Query("srn_transporter_name") String transporter_name, @Query("srn_vehicle_no") String vehicle_no,
                                                 @Query("srn_driver_name") String driver_name, @Query("srn_particular") String particular,
                                                 @Query("srn_quantity") String quantity, @Query("srn_remarks") String remarks,
                                                 @Query("srn_vendor_address") String srn_vendor_address);


    @GET("itn")
    Call<ITNListResponse> getITNList(@Header("Accept") String header1, @Header("Authorization") String header2,
                                     @Query("ticket_id") String ticket_id);

    @GET("srn")
    Call<SRNListResponse> getSRNList(@Header("Accept") String header1, @Header("Authorization") String header2,
                                     @Query("ticket_id") String ticket_id);

    @GET("site-visits")
    Call<SiteVisitListResponse> getVisitList(@Header("Accept") String header1, @Header("Authorization") String header2,
                                             @Query("ticket_id") String ticket_id);


    @Multipart
    @POST("itn/receive")
    Call<SubmitSuccessResponse> setITNReceived(@Header("Accept") String header1, @Header("Authorization") String header2,
                                               @Part("itn_id") RequestBody ticket_id, @Part("itn_received_remarks") RequestBody received_remarks,
                                               @Part List<MultipartBody.Part> itn_received_file);

    @Multipart
    @POST("srn/receive")
    Call<SubmitSuccessResponse> setSRNReceived(@Header("Accept") String header1, @Header("Authorization") String header2,
                                               @Part("receive_srn_id") RequestBody ticket_id, @Part("srn_received_for") RequestBody srn_received_for,
                                               @Part("srn_received_transfer_to") RequestBody srn_received_transfer_to, @Part("itn_received_remarks") RequestBody received_remarks,
                                               @Part("srn_primary_location_id") RequestBody primary_location_id, @Part("srn_secondary_location_id") RequestBody secondary_location_id,
                                               @Part("srn_storage_type_id") RequestBody storage_type_id, @Part("srn_storage_section_id") RequestBody storage_section_id,
                                               @Part("srn_box_number") RequestBody box_number, @Part List<MultipartBody.Part> itn_received_file);

    @POST("itn/cancel")
    Call<SubmitSuccessResponse> setITNCancel(@Header("Accept") String header1, @Header("Authorization") String header2,
                                             @Query("itn_id") Integer ticket_id, @Query("itn_cancel_remarks") String received_remarks);

    @POST("srn/cancel")
    Call<SubmitSuccessResponse> setSRNCancel(@Header("Accept") String header1, @Header("Authorization") String header2,
                                             @Query("srn_id") Integer ticket_id, @Query("srn_cancel_remarks") String received_remarks);

    @POST("site-visits/cancel")
    Call<SubmitSuccessResponse> setVisitCancel(@Header("Accept") String header1, @Header("Authorization") String header2,
                                               @Query("tsv_id") Integer ticket_id, @Query("tsv_cancel_remarks") String received_remarks);

    @POST("site-visits/confirm")
    Call<SubmitSuccessResponse> setConfirmVisit(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                @Query("tsv_id") Integer tsv_id);

    @GET("assets/getAddToAssets")
    Call<AddToAssetConsumablesListResponse> getConsumalesInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                              @Query("asset_ac_id") String classification_id);


    @GET("assets/getAddToAssets")
    Call<PeripheralListResponse> getPeripheralInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                   @Query("asset_ac_id") String classification_id);


    @POST("assets/storeAddToAssets")
    Call<SubmitSuccessResponse> getAddToAssetSubmitInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                 @Query("ticket_id") String ticket_id, @Query("asset_id") String asset_id,
                                                 @Query("asset_ac_id") String asset_ac_id, @Query("asset_quantity") String asset_quantity);

    @GET("assets/getRemovableAssets")
    Call<RemovableAssetsResponse> getRemovableAssets(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                     @Query("asset_id") String asset_id);


    @POST("assets/storeRemovableAssets")
    Call<SubmitSuccessResponse> getRemoveAssetSubmitInfo(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                         @Query("ticket_id") String ticket_id, @Query("kit_id") String asset_id,
                                                         @Query("asset_history_ids") String asset_history_ids, @Query("internal_ac_id") String internal_ac_id,
                                                         @Query("rc_id") String rc_id, @Query("disk_size") String disk_size,
                                                         @Query("disk_size_unit") String disk_size_unit, @Query("remove_asset_type") String remove_asset_type,
                                                         @Query("remove_asset_send_to") String remove_asset_send_to, @Query("remove_primary_location_id") String remove_primary_location_id,
                                                         @Query("remove_secondary_location_id") String remove_secondary_location_id, @Query("remove_storage_type_id") String remove_storage_type_id,
                                                         @Query("remove_storage_section_id") String remove_storage_section_id, @Query("remove_box_number") String remove_box_number);

    @GET("tickets/getPriorities")
    Call<PriorityResponse> getPriority(@Header("Accept") String header1, @Header("Authorization") String header2);

    @GET("tickets/checkServiceIsInMaintenance")
    Call<MaintainanceStatusResponse> getMaintainanceFlag(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                         @Query("service_type") String service_type, @Query("service_type_id") String service_type_id,
                                                         @Query("user_id") String user_id);

    @POST("user/changePassword")
    Call<SubmitSuccessResponse> getChangePasswordResponse(@Header("Accept") String header1, @Header("Authorization") String header2,
                                                         @Query("current_password") String current_password, @Query("new_password") String new_password,
                                                         @Query("confirm_password") String confirm_password);


}
