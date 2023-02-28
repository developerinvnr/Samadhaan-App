package com.vnrseeds.samadhan.postclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;

public class SaveAsset {
    @SerializedName("asset_ac_id")
    @Expose
    private String asset_ac_id;
    @SerializedName("asset_id")
    @Expose
    private String asset_id;
    @SerializedName("asset_type")
    @Expose
    private String asset_type;
    @SerializedName("asset_brand_id")
    @Expose
    private String asset_brand_id;
    @SerializedName("asset_model_id")
    @Expose
    private String asset_model_id;
    @SerializedName("asset_serial_no")
    @Expose
    private String asset_serial_no;
    @SerializedName("asset_monitor_brand_id")
    @Expose
    private String asset_monitor_brand_id;
    @SerializedName("asset_monitor_model_id")
    @Expose
    private String asset_monitor_model_id;
    @SerializedName("asset_monitor_serial_no")
    @Expose
    private String asset_monitor_serial_no;
    @SerializedName("asset_keyboard_type")
    @Expose
    private String asset_keyboard_type;
    @SerializedName("asset_keyboard_brand_id")
    @Expose
    private String asset_keyboard_brand_id;
    @SerializedName("asset_keyboard_model_id")
    @Expose
    private String asset_keyboard_model_id;
    @SerializedName("asset_keyboard_serial_no")
    @Expose
    private String asset_keyboard_serial_no;
    @SerializedName("asset_mouse_type")
    @Expose
    private String asset_mouse_type;
    @SerializedName("asset_mouse_brand_id")
    @Expose
    private String asset_mouse_brand_id;
    @SerializedName("asset_mouse_model_id")
    @Expose
    private String asset_mouse_model_id;
    @SerializedName("asset_mouse_serial_no")
    @Expose
    private String asset_mouse_serial_no;
    @SerializedName("asset_processor_id")
    @Expose
    private String asset_processor_id;
    @SerializedName("asset_ram_id")
    @Expose
    private String asset_ram_id;
    @SerializedName("asset_rc_id")
    @Expose
    private String asset_rc_id;
    @SerializedName("asset_hard_disk_type")
    @Expose
    private String asset_hard_disk_type;
    @SerializedName("asset_hdd_capacity")
    @Expose
    private String asset_hdd_capacity;
    @SerializedName("asset_ssd_capacity")
    @Expose
    private String asset_ssd_capacity;
    @SerializedName("asset_invoice_date")
    @Expose
    private String asset_invoice_date;
    @SerializedName("asset_invoice_no")
    @Expose
    private String asset_invoice_no;
    @SerializedName("asset_vendor_id")
    @Expose
    private String asset_vendor_id;
    @SerializedName("asset_price")
    @Expose
    private String asset_price;
    @SerializedName("asset_warranty_upto")
    @Expose
    private String asset_warranty_upto;
    @SerializedName("asset_monitor_warranty_upto")
    @Expose
    private String asset_monitor_warranty_upto;
    @SerializedName("asset_keyboard_warranty_upto")
    @Expose
    private String asset_keyboard_warranty_upto;
    @SerializedName("asset_mouse_warranty_upto")
    @Expose
    private String asset_mouse_warranty_upto;
    @SerializedName("asset_is_eol")
    @Expose
    private int asset_is_eol;
    @SerializedName("asset_eol_warranty_upto")
    @Expose
    private String asset_eol_warranty_upto;
    @SerializedName("asset_installation_date")
    @Expose
    private String asset_installation_date;
    @SerializedName("asset_installation_location_id")
    @Expose
    private String asset_installation_location_id;
    @SerializedName("asset_installation_department_id")
    @Expose
    private String asset_installation_department_id;
    @SerializedName("asset_installation_section_id")
    @Expose
    private String asset_installation_section_id;
    @SerializedName("asset_installation_custodian_ids")
    @Expose
    private String asset_installation_custodian_ids;
    @SerializedName("asset_nt_id")
    @Expose
    private String asset_nt_id;
    @SerializedName("asset_mac_ethernet_address")
    @Expose
    private String asset_mac_ethernet_address;
    @SerializedName("asset_mac_wifi_address")
    @Expose
    private String asset_mac_wifi_address;
    @SerializedName("asset_connection_type")
    @Expose
    private String asset_connection_type;
    @SerializedName("asset_ip_address")
    @Expose
    private String asset_ip_address;
    @SerializedName("asset_subnet_mask_address")
    @Expose
    private String asset_subnet_mask_address;
    @SerializedName("asset_gateway_address")
    @Expose
    private String asset_gateway_address;
    @SerializedName("asset_os_id")
    @Expose
    private String asset_os_id;
    @SerializedName("asset_os_is_updated")
    @Expose
    private int asset_os_is_updated;
    @SerializedName("asset_antivirus_id")
    @Expose
    private String asset_antivirus_id;
    @SerializedName("asset_antivirus_serial_no")
    @Expose
    private String asset_antivirus_serial_no;
    @SerializedName("asset_antivirus_installation_date")
    @Expose
    private String asset_antivirus_installation_date;
    @SerializedName("asset_antivirus_renewal_date")
    @Expose
    private String asset_antivirus_renewal_date;
    @SerializedName("asset_browsers")
    @Expose
    private String asset_browsers;
    @SerializedName("asset_is_office")
    @Expose
    private int asset_is_office;
    @SerializedName("asset_office_type")
    @Expose
    private String asset_office_type;
    @SerializedName("asset_office_is_updated")
    @Expose
    private int asset_office_is_updated;
    @SerializedName("asset_other_softwares")
    @Expose
    private String asset_other_softwares;
    @SerializedName("asset_remarks")
    @Expose
    private String asset_remarks;
    @SerializedName("asset_status")
    @Expose
    private int asset_status;
    @SerializedName("asset_office_version")
    @Expose
    private String asset_office_version;
    @SerializedName("asset_hdd_storage")
    @Expose
    private String asset_hdd_storage;
    @SerializedName("asset_ssd_storage")
    @Expose
    private String asset_ssd_storage;
    @SerializedName("asset_hdd_capacity_secondary")
    @Expose
    private String asset_hdd_capacity_secondary;
    @SerializedName("asset_ssd_capacity_secondary")
    @Expose
    private String asset_ssd_capacity_secondary;
    @SerializedName("asset_hdd_storage_secondary")
    @Expose
    private String asset_hdd_storage_secondary;
    @SerializedName("asset_ssd_storage_secondary")
    @Expose
    private String asset_ssd_storage_secondary;
    @SerializedName("asset_precise_location")
    @Expose
    private String asset_precise_location;
    @SerializedName("asset_monitor_size")
    @Expose
    private String asset_monitor_size;
    @SerializedName("asset_rack_size")
    @Expose
    private String asset_rack_size;
    @SerializedName("asset_camera_type")
    @Expose
    private String asset_camera_type;
    @SerializedName("asset_camera_form_factor")
    @Expose
    private String asset_cameraform_factor;
    @SerializedName("asset_channel")
    @Expose
    private String asset_channel;
    @SerializedName("asset_storage_capacity")
    @Expose
    private String asset_storage_capacity;
    @SerializedName("asset_printer_connection_type")
    @Expose
    private String asset_printer_type;
    @SerializedName("asset_printer_function")
    @Expose
    private String asset_printer_function;
    @SerializedName("asset_printer_cartridge_no")
    @Expose
    private String asset_printer_cartridge;
    @SerializedName("asset_barcode_connection_type")
    @Expose
    private String asset_barcode_connection_type;
    @SerializedName("asset_deskphone_connection_type")
    @Expose
    private String asset_deskphone_connection_type;
    @SerializedName("asset_deskphone_type")
    @Expose
    private String asset_deskphone_type;
    @SerializedName("asset_microphone_type")
    @Expose
    private String asset_microphone_type;
    @SerializedName("asset_camera_available")
    @Expose
    private String asset_camera_available;
    @SerializedName("asset_sd_card_size")
    @Expose
    private String asset_sd_card_size;
    @SerializedName("asset_imei_no_first")
    @Expose
    private String asset_imei_no_first;
    @SerializedName("asset_imei_no_second")
    @Expose
    private String asset_imei_no_second;
    @SerializedName("asset_owned_by")
    @Expose
    private String asset_owned_by;
    @SerializedName("asset_sim_operator")
    @Expose
    private String asset_sim_operator;
    @SerializedName("asset_sim_no")
    @Expose
    private String asset_sim_no;
    @SerializedName("asset_mobile_no")
    @Expose
    private String asset_mobile_no;
    @SerializedName("asset_screen_size")
    @Expose
    private String asset_screen_size;
    @SerializedName("asset_color")
    @Expose
    private String asset_color;
    @SerializedName("asset_projector_is_remote")
    @Expose
    private int asset_projector_is_remote;
    @SerializedName("asset_speaker_type")
    @Expose
    private String asset_speaker_type;
    @SerializedName("asset_speaker_no_of_mic")
    @Expose
    private String asset_speaker_no_of_mic;
    @SerializedName("asset_switch_type")
    @Expose
    private String asset_switch_type;
    @SerializedName("asset_switch_port_type")
    @Expose
    private String asset_switch_port_type;
    @SerializedName("asset_switch_lan_port")
    @Expose
    private String asset_switch_lan_port;
    @SerializedName("asset_switch_fiber_port")
    @Expose
    private String asset_switch_fiber_port;
    @SerializedName("asset_switch_speed")
    @Expose
    private String asset_switch_speed;
    @SerializedName("asset_switch_speed_unit")
    @Expose
    private String asset_switch_speed_unit;
    @SerializedName("asset_switch_poe_wattage")
    @Expose
    private String asset_switch_poe_wattage;
    @SerializedName("asset_tv_type")
    @Expose
    private String asset_tv_type;
    @SerializedName("asset_tv_hdmi_ports")
    @Expose
    private String asset_tv_hdmi_ports;
    @SerializedName("asset_tv_is_remote")
    @Expose
    private int asset_tv_is_remote;
    @SerializedName("asset_tv_screen_size")
    @Expose
    private String asset_tv_screen_size;
    @SerializedName("asset_ups_capacity")
    @Expose
    private String asset_ups_capacity;
    @SerializedName("asset_ups_capacity_unit")
    @Expose
    private String asset_ups_capacity_unit;
    @SerializedName("asset_port_available")
    @Expose
    private String asset_port_available;
    @SerializedName("asset_port_in_use")
    @Expose
    private String asset_port_in_use;
    @SerializedName("asset_ethernet_port")
    @Expose
    private String asset_ethernet_port;
    @SerializedName("asset_fiber_optic_port")
    @Expose
    private String asset_fiber_optic_port;
    @SerializedName("asset_ssid")
    @Expose
    private String asset_ssid;
    @SerializedName("asset_mobile_technology")
    @Expose
    private int asset_mobile_technology;
    @SerializedName("asset_image")
    @Expose
    private MultipartBody.Part asset_image;
    @SerializedName("asset_serial_no_image")
    @Expose
    private MultipartBody.Part asset_serial_no_image;

    public SaveAsset() {
    }

    public SaveAsset(String asset_ac_id, String asset_id, String asset_type, String asset_brand_id, String asset_model_id, String asset_serial_no, String asset_monitor_brand_id, String asset_monitor_model_id, String asset_monitor_serial_no, String asset_keyboard_type, String asset_keyboard_brand_id, String asset_keyboard_model_id, String asset_keyboard_serial_no, String asset_mouse_type, String asset_mouse_brand_id, String asset_mouse_model_id, String asset_mouse_serial_no, String asset_processor_id, String asset_ram_id, String asset_rc_id, String asset_hard_disk_type, String asset_hdd_capacity, String asset_ssd_capacity, String asset_invoice_date, String asset_invoice_no, String asset_vendor_id, String asset_price, String asset_warranty_upto, String asset_monitor_warranty_upto, String asset_keyboard_warranty_upto, String asset_mouse_warranty_upto, int asset_is_eol, String asset_eol_warranty_upto, String asset_installation_date, String asset_installation_location_id, String asset_installation_department_id, String asset_installation_section_id, String asset_installation_custodian_ids, String asset_nt_id, String asset_mac_ethernet_address, String asset_mac_wifi_address, String asset_connection_type, String asset_ip_address, String asset_subnet_mask_address, String asset_gateway_address, String asset_os_id, int asset_os_is_updated, String asset_antivirus_id, String asset_antivirus_serial_no, String asset_antivirus_installation_date, String asset_antivirus_renewal_date, String asset_browsers, int asset_is_office, String asset_office_type, int asset_office_is_updated, String asset_other_softwares, String asset_remarks, int asset_status, String asset_office_version, String asset_hdd_storage, String asset_ssd_storage, String asset_hdd_capacity_secondary, String asset_ssd_capacity_secondary, String asset_hdd_storage_secondary, String asset_ssd_storage_secondary, String asset_precise_location, String asset_monitor_size, String asset_rack_size, String asset_camera_type, String asset_cameraform_factor, String asset_channel, String asset_storage_capacity, String asset_printer_type, String asset_printer_function, String asset_printer_cartridge, String asset_barcode_connection_type, String asset_deskphone_connection_type, String asset_deskphone_type, String asset_microphone_type, String asset_camera_available, String asset_sd_card_size, String asset_imei_no_first, String asset_imei_no_second, String asset_owned_by, String asset_sim_operator, String asset_sim_no, String asset_mobile_no, String asset_screen_size, String asset_color, int asset_projector_is_remote, String asset_speaker_type, String asset_speaker_no_of_mic, String asset_switch_type, String asset_switch_port_type, String asset_switch_lan_port, String asset_switch_fiber_port, String asset_switch_speed, String asset_switch_speed_unit, String asset_switch_poe_wattage, String asset_tv_type, String asset_tv_hdmi_ports, int asset_tv_is_remote, String asset_tv_screen_size, String asset_ups_capacity, String asset_ups_capacity_unit, String asset_port_available, String asset_port_in_use, String asset_ethernet_port, String asset_fiber_optic_port, String asset_ssid, int asset_mobile_technology) {
        this.asset_ac_id = asset_ac_id;
        this.asset_id = asset_id;
        this.asset_type = asset_type;
        this.asset_brand_id = asset_brand_id;
        this.asset_model_id = asset_model_id;
        this.asset_serial_no = asset_serial_no;
        this.asset_monitor_brand_id = asset_monitor_brand_id;
        this.asset_monitor_model_id = asset_monitor_model_id;
        this.asset_monitor_serial_no = asset_monitor_serial_no;
        this.asset_keyboard_type = asset_keyboard_type;
        this.asset_keyboard_brand_id = asset_keyboard_brand_id;
        this.asset_keyboard_model_id = asset_keyboard_model_id;
        this.asset_keyboard_serial_no = asset_keyboard_serial_no;
        this.asset_mouse_type = asset_mouse_type;
        this.asset_mouse_brand_id = asset_mouse_brand_id;
        this.asset_mouse_model_id = asset_mouse_model_id;
        this.asset_mouse_serial_no = asset_mouse_serial_no;
        this.asset_processor_id = asset_processor_id;
        this.asset_ram_id = asset_ram_id;
        this.asset_rc_id = asset_rc_id;
        this.asset_hard_disk_type = asset_hard_disk_type;
        this.asset_hdd_capacity = asset_hdd_capacity;
        this.asset_ssd_capacity = asset_ssd_capacity;
        this.asset_invoice_date = asset_invoice_date;
        this.asset_invoice_no = asset_invoice_no;
        this.asset_vendor_id = asset_vendor_id;
        this.asset_price = asset_price;
        this.asset_warranty_upto = asset_warranty_upto;
        this.asset_monitor_warranty_upto = asset_monitor_warranty_upto;
        this.asset_keyboard_warranty_upto = asset_keyboard_warranty_upto;
        this.asset_mouse_warranty_upto = asset_mouse_warranty_upto;
        this.asset_is_eol = asset_is_eol;
        this.asset_eol_warranty_upto = asset_eol_warranty_upto;
        this.asset_installation_date = asset_installation_date;
        this.asset_installation_location_id = asset_installation_location_id;
        this.asset_installation_department_id = asset_installation_department_id;
        this.asset_installation_section_id = asset_installation_section_id;
        this.asset_installation_custodian_ids = asset_installation_custodian_ids;
        this.asset_nt_id = asset_nt_id;
        this.asset_mac_ethernet_address = asset_mac_ethernet_address;
        this.asset_mac_wifi_address = asset_mac_wifi_address;
        this.asset_connection_type = asset_connection_type;
        this.asset_ip_address = asset_ip_address;
        this.asset_subnet_mask_address = asset_subnet_mask_address;
        this.asset_gateway_address = asset_gateway_address;
        this.asset_os_id = asset_os_id;
        this.asset_os_is_updated = asset_os_is_updated;
        this.asset_antivirus_id = asset_antivirus_id;
        this.asset_antivirus_serial_no = asset_antivirus_serial_no;
        this.asset_antivirus_installation_date = asset_antivirus_installation_date;
        this.asset_antivirus_renewal_date = asset_antivirus_renewal_date;
        this.asset_browsers = asset_browsers;
        this.asset_is_office = asset_is_office;
        this.asset_office_type = asset_office_type;
        this.asset_office_is_updated = asset_office_is_updated;
        this.asset_other_softwares = asset_other_softwares;
        this.asset_remarks = asset_remarks;
        this.asset_status = asset_status;
        this.asset_office_version = asset_office_version;
        this.asset_hdd_storage = asset_hdd_storage;
        this.asset_ssd_storage = asset_ssd_storage;
        this.asset_hdd_capacity_secondary = asset_hdd_capacity_secondary;
        this.asset_ssd_capacity_secondary = asset_ssd_capacity_secondary;
        this.asset_hdd_storage_secondary = asset_hdd_storage_secondary;
        this.asset_ssd_storage_secondary = asset_ssd_storage_secondary;
        this.asset_precise_location = asset_precise_location;
        this.asset_monitor_size = asset_monitor_size;
        this.asset_rack_size = asset_rack_size;
        this.asset_camera_type = asset_camera_type;
        this.asset_cameraform_factor = asset_cameraform_factor;
        this.asset_channel = asset_channel;
        this.asset_storage_capacity = asset_storage_capacity;
        this.asset_printer_type = asset_printer_type;
        this.asset_printer_function = asset_printer_function;
        this.asset_printer_cartridge = asset_printer_cartridge;
        this.asset_barcode_connection_type = asset_barcode_connection_type;
        this.asset_deskphone_connection_type = asset_deskphone_connection_type;
        this.asset_deskphone_type = asset_deskphone_type;
        this.asset_microphone_type = asset_microphone_type;
        this.asset_camera_available = asset_camera_available;
        this.asset_sd_card_size = asset_sd_card_size;
        this.asset_imei_no_first = asset_imei_no_first;
        this.asset_imei_no_second = asset_imei_no_second;
        this.asset_owned_by = asset_owned_by;
        this.asset_sim_operator = asset_sim_operator;
        this.asset_sim_no = asset_sim_no;
        this.asset_mobile_no = asset_mobile_no;
        this.asset_screen_size = asset_screen_size;
        this.asset_color = asset_color;
        this.asset_projector_is_remote = asset_projector_is_remote;
        this.asset_speaker_type = asset_speaker_type;
        this.asset_speaker_no_of_mic = asset_speaker_no_of_mic;
        this.asset_switch_type = asset_switch_type;
        this.asset_switch_port_type = asset_switch_port_type;
        this.asset_switch_lan_port = asset_switch_lan_port;
        this.asset_switch_fiber_port = asset_switch_fiber_port;
        this.asset_switch_speed = asset_switch_speed;
        this.asset_switch_speed_unit = asset_switch_speed_unit;
        this.asset_switch_poe_wattage = asset_switch_poe_wattage;
        this.asset_tv_type = asset_tv_type;
        this.asset_tv_hdmi_ports = asset_tv_hdmi_ports;
        this.asset_tv_is_remote = asset_tv_is_remote;
        this.asset_tv_screen_size = asset_tv_screen_size;
        this.asset_ups_capacity = asset_ups_capacity;
        this.asset_ups_capacity_unit = asset_ups_capacity_unit;
        this.asset_port_available = asset_port_available;
        this.asset_port_in_use = asset_port_in_use;
        this.asset_ethernet_port = asset_ethernet_port;
        this.asset_fiber_optic_port = asset_fiber_optic_port;
        this.asset_ssid = asset_ssid;
        this.asset_mobile_technology = asset_mobile_technology;
    }

    public String getAsset_ac_id() {
        return asset_ac_id;
    }

    public void setAsset_ac_id(String asset_ac_id) {
        this.asset_ac_id = asset_ac_id;
    }

    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

    public String getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(String asset_type) {
        this.asset_type = asset_type;
    }

    public String getAsset_brand_id() {
        return asset_brand_id;
    }

    public void setAsset_brand_id(String asset_brand_id) {
        this.asset_brand_id = asset_brand_id;
    }

    public String getAsset_model_id() {
        return asset_model_id;
    }

    public void setAsset_model_id(String asset_model_id) {
        this.asset_model_id = asset_model_id;
    }

    public String getAsset_serial_no() {
        return asset_serial_no;
    }

    public void setAsset_serial_no(String asset_serial_no) {
        this.asset_serial_no = asset_serial_no;
    }

    public String getAsset_monitor_brand_id() {
        return asset_monitor_brand_id;
    }

    public void setAsset_monitor_brand_id(String asset_monitor_brand_id) {
        this.asset_monitor_brand_id = asset_monitor_brand_id;
    }

    public String getAsset_monitor_model_id() {
        return asset_monitor_model_id;
    }

    public void setAsset_monitor_model_id(String asset_monitor_model_id) {
        this.asset_monitor_model_id = asset_monitor_model_id;
    }

    public String getAsset_monitor_serial_no() {
        return asset_monitor_serial_no;
    }

    public void setAsset_monitor_serial_no(String asset_monitor_serial_no) {
        this.asset_monitor_serial_no = asset_monitor_serial_no;
    }

    public String getAsset_keyboard_type() {
        return asset_keyboard_type;
    }

    public void setAsset_keyboard_type(String asset_keyboard_type) {
        this.asset_keyboard_type = asset_keyboard_type;
    }

    public String getAsset_keyboard_brand_id() {
        return asset_keyboard_brand_id;
    }

    public void setAsset_keyboard_brand_id(String asset_keyboard_brand_id) {
        this.asset_keyboard_brand_id = asset_keyboard_brand_id;
    }

    public String getAsset_keyboard_model_id() {
        return asset_keyboard_model_id;
    }

    public void setAsset_keyboard_model_id(String asset_keyboard_model_id) {
        this.asset_keyboard_model_id = asset_keyboard_model_id;
    }

    public String getAsset_keyboard_serial_no() {
        return asset_keyboard_serial_no;
    }

    public void setAsset_keyboard_serial_no(String asset_keyboard_serial_no) {
        this.asset_keyboard_serial_no = asset_keyboard_serial_no;
    }

    public String getAsset_mouse_type() {
        return asset_mouse_type;
    }

    public void setAsset_mouse_type(String asset_mouse_type) {
        this.asset_mouse_type = asset_mouse_type;
    }

    public String getAsset_mouse_brand_id() {
        return asset_mouse_brand_id;
    }

    public void setAsset_mouse_brand_id(String asset_mouse_brand_id) {
        this.asset_mouse_brand_id = asset_mouse_brand_id;
    }

    public String getAsset_mouse_model_id() {
        return asset_mouse_model_id;
    }

    public void setAsset_mouse_model_id(String asset_mouse_model_id) {
        this.asset_mouse_model_id = asset_mouse_model_id;
    }

    public String getAsset_mouse_serial_no() {
        return asset_mouse_serial_no;
    }

    public void setAsset_mouse_serial_no(String asset_mouse_serial_no) {
        this.asset_mouse_serial_no = asset_mouse_serial_no;
    }

    public String getAsset_processor_id() {
        return asset_processor_id;
    }

    public void setAsset_processor_id(String asset_processor_id) {
        this.asset_processor_id = asset_processor_id;
    }

    public String getAsset_ram_id() {
        return asset_ram_id;
    }

    public void setAsset_ram_id(String asset_ram_id) {
        this.asset_ram_id = asset_ram_id;
    }

    public String getAsset_rc_id() {
        return asset_rc_id;
    }

    public void setAsset_rc_id(String asset_rc_id) {
        this.asset_rc_id = asset_rc_id;
    }

    public String getAsset_hard_disk_type() {
        return asset_hard_disk_type;
    }

    public void setAsset_hard_disk_type(String asset_hard_disk_type) {
        this.asset_hard_disk_type = asset_hard_disk_type;
    }

    public String getAsset_hdd_capacity() {
        return asset_hdd_capacity;
    }

    public void setAsset_hdd_capacity(String asset_hdd_capacity) {
        this.asset_hdd_capacity = asset_hdd_capacity;
    }

    public String getAsset_ssd_capacity() {
        return asset_ssd_capacity;
    }

    public void setAsset_ssd_capacity(String asset_ssd_capacity) {
        this.asset_ssd_capacity = asset_ssd_capacity;
    }

    public String getAsset_invoice_date() {
        return asset_invoice_date;
    }

    public void setAsset_invoice_date(String asset_invoice_date) {
        this.asset_invoice_date = asset_invoice_date;
    }

    public String getAsset_invoice_no() {
        return asset_invoice_no;
    }

    public void setAsset_invoice_no(String asset_invoice_no) {
        this.asset_invoice_no = asset_invoice_no;
    }

    public String getAsset_vendor_id() {
        return asset_vendor_id;
    }

    public void setAsset_vendor_id(String asset_vendor_id) {
        this.asset_vendor_id = asset_vendor_id;
    }

    public String getAsset_price() {
        return asset_price;
    }

    public void setAsset_price(String asset_price) {
        this.asset_price = asset_price;
    }

    public String getAsset_warranty_upto() {
        return asset_warranty_upto;
    }

    public void setAsset_warranty_upto(String asset_warranty_upto) {
        this.asset_warranty_upto = asset_warranty_upto;
    }

    public String getAsset_monitor_warranty_upto() {
        return asset_monitor_warranty_upto;
    }

    public void setAsset_monitor_warranty_upto(String asset_monitor_warranty_upto) {
        this.asset_monitor_warranty_upto = asset_monitor_warranty_upto;
    }

    public String getAsset_keyboard_warranty_upto() {
        return asset_keyboard_warranty_upto;
    }

    public void setAsset_keyboard_warranty_upto(String asset_keyboard_warranty_upto) {
        this.asset_keyboard_warranty_upto = asset_keyboard_warranty_upto;
    }

    public String getAsset_mouse_warranty_upto() {
        return asset_mouse_warranty_upto;
    }

    public void setAsset_mouse_warranty_upto(String asset_mouse_warranty_upto) {
        this.asset_mouse_warranty_upto = asset_mouse_warranty_upto;
    }

    public int getAsset_is_eol() {
        return asset_is_eol;
    }

    public void setAsset_is_eol(int asset_is_eol) {
        this.asset_is_eol = asset_is_eol;
    }

    public String getAsset_eol_warranty_upto() {
        return asset_eol_warranty_upto;
    }

    public void setAsset_eol_warranty_upto(String asset_eol_warranty_upto) {
        this.asset_eol_warranty_upto = asset_eol_warranty_upto;
    }

    public String getAsset_installation_date() {
        return asset_installation_date;
    }

    public void setAsset_installation_date(String asset_installation_date) {
        this.asset_installation_date = asset_installation_date;
    }

    public String getAsset_installation_location_id() {
        return asset_installation_location_id;
    }

    public void setAsset_installation_location_id(String asset_installation_location_id) {
        this.asset_installation_location_id = asset_installation_location_id;
    }

    public String getAsset_installation_department_id() {
        return asset_installation_department_id;
    }

    public void setAsset_installation_department_id(String asset_installation_department_id) {
        this.asset_installation_department_id = asset_installation_department_id;
    }

    public String getAsset_installation_section_id() {
        return asset_installation_section_id;
    }

    public void setAsset_installation_section_id(String asset_installation_section_id) {
        this.asset_installation_section_id = asset_installation_section_id;
    }

    public String getAsset_installation_custodian_ids() {
        return asset_installation_custodian_ids;
    }

    public void setAsset_installation_custodian_ids(String asset_installation_custodian_ids) {
        this.asset_installation_custodian_ids = asset_installation_custodian_ids;
    }

    public String getAsset_nt_id() {
        return asset_nt_id;
    }

    public void setAsset_nt_id(String asset_nt_id) {
        this.asset_nt_id = asset_nt_id;
    }

    public String getAsset_mac_ethernet_address() {
        return asset_mac_ethernet_address;
    }

    public void setAsset_mac_ethernet_address(String asset_mac_ethernet_address) {
        this.asset_mac_ethernet_address = asset_mac_ethernet_address;
    }

    public String getAsset_mac_wifi_address() {
        return asset_mac_wifi_address;
    }

    public void setAsset_mac_wifi_address(String asset_mac_wifi_address) {
        this.asset_mac_wifi_address = asset_mac_wifi_address;
    }

    public String getAsset_connection_type() {
        return asset_connection_type;
    }

    public void setAsset_connection_type(String asset_connection_type) {
        this.asset_connection_type = asset_connection_type;
    }

    public String getAsset_ip_address() {
        return asset_ip_address;
    }

    public void setAsset_ip_address(String asset_ip_address) {
        this.asset_ip_address = asset_ip_address;
    }

    public String getAsset_subnet_mask_address() {
        return asset_subnet_mask_address;
    }

    public void setAsset_subnet_mask_address(String asset_subnet_mask_address) {
        this.asset_subnet_mask_address = asset_subnet_mask_address;
    }

    public String getAsset_gateway_address() {
        return asset_gateway_address;
    }

    public void setAsset_gateway_address(String asset_gateway_address) {
        this.asset_gateway_address = asset_gateway_address;
    }

    public String getAsset_os_id() {
        return asset_os_id;
    }

    public void setAsset_os_id(String asset_os_id) {
        this.asset_os_id = asset_os_id;
    }

    public int getAsset_os_is_updated() {
        return asset_os_is_updated;
    }

    public void setAsset_os_is_updated(int asset_os_is_updated) {
        this.asset_os_is_updated = asset_os_is_updated;
    }

    public String getAsset_antivirus_id() {
        return asset_antivirus_id;
    }

    public void setAsset_antivirus_id(String asset_antivirus_id) {
        this.asset_antivirus_id = asset_antivirus_id;
    }

    public String getAsset_antivirus_serial_no() {
        return asset_antivirus_serial_no;
    }

    public void setAsset_antivirus_serial_no(String asset_antivirus_serial_no) {
        this.asset_antivirus_serial_no = asset_antivirus_serial_no;
    }

    public String getAsset_antivirus_installation_date() {
        return asset_antivirus_installation_date;
    }

    public void setAsset_antivirus_installation_date(String asset_antivirus_installation_date) {
        this.asset_antivirus_installation_date = asset_antivirus_installation_date;
    }

    public String getAsset_antivirus_renewal_date() {
        return asset_antivirus_renewal_date;
    }

    public void setAsset_antivirus_renewal_date(String asset_antivirus_renewal_date) {
        this.asset_antivirus_renewal_date = asset_antivirus_renewal_date;
    }

    public String getAsset_browsers() {
        return asset_browsers;
    }

    public void setAsset_browsers(String asset_browsers) {
        this.asset_browsers = asset_browsers;
    }

    public int getAsset_is_office() {
        return asset_is_office;
    }

    public void setAsset_is_office(int asset_is_office) {
        this.asset_is_office = asset_is_office;
    }

    public String getAsset_office_type() {
        return asset_office_type;
    }

    public void setAsset_office_type(String asset_office_type) {
        this.asset_office_type = asset_office_type;
    }

    public int getAsset_office_is_updated() {
        return asset_office_is_updated;
    }

    public void setAsset_office_is_updated(int asset_office_is_updated) {
        this.asset_office_is_updated = asset_office_is_updated;
    }

    public String getAsset_other_softwares() {
        return asset_other_softwares;
    }

    public void setAsset_other_softwares(String asset_other_softwares) {
        this.asset_other_softwares = asset_other_softwares;
    }

    public String getAsset_remarks() {
        return asset_remarks;
    }

    public void setAsset_remarks(String asset_remarks) {
        this.asset_remarks = asset_remarks;
    }

    public int getAsset_status() {
        return asset_status;
    }

    public void setAsset_status(int asset_status) {
        this.asset_status = asset_status;
    }

    public String getAsset_office_version() {
        return asset_office_version;
    }

    public void setAsset_office_version(String asset_office_version) {
        this.asset_office_version = asset_office_version;
    }

    public String getAsset_hdd_storage() {
        return asset_hdd_storage;
    }

    public void setAsset_hdd_storage(String asset_hdd_storage) {
        this.asset_hdd_storage = asset_hdd_storage;
    }

    public String getAsset_ssd_storage() {
        return asset_ssd_storage;
    }

    public void setAsset_ssd_storage(String asset_ssd_storage) {
        this.asset_ssd_storage = asset_ssd_storage;
    }

    public String getAsset_hdd_capacity_secondary() {
        return asset_hdd_capacity_secondary;
    }

    public void setAsset_hdd_capacity_secondary(String asset_hdd_capacity_secondary) {
        this.asset_hdd_capacity_secondary = asset_hdd_capacity_secondary;
    }

    public String getAsset_ssd_capacity_secondary() {
        return asset_ssd_capacity_secondary;
    }

    public void setAsset_ssd_capacity_secondary(String asset_ssd_capacity_secondary) {
        this.asset_ssd_capacity_secondary = asset_ssd_capacity_secondary;
    }

    public String getAsset_hdd_storage_secondary() {
        return asset_hdd_storage_secondary;
    }

    public void setAsset_hdd_storage_secondary(String asset_hdd_storage_secondary) {
        this.asset_hdd_storage_secondary = asset_hdd_storage_secondary;
    }

    public String getAsset_ssd_storage_secondary() {
        return asset_ssd_storage_secondary;
    }

    public void setAsset_ssd_storage_secondary(String asset_ssd_storage_secondary) {
        this.asset_ssd_storage_secondary = asset_ssd_storage_secondary;
    }

    public String getAsset_precise_location() {
        return asset_precise_location;
    }

    public void setAsset_precise_location(String asset_precise_location) {
        this.asset_precise_location = asset_precise_location;
    }

    public String getAsset_monitor_size() {
        return asset_monitor_size;
    }

    public void setAsset_monitor_size(String asset_monitor_size) {
        this.asset_monitor_size = asset_monitor_size;
    }

    public String getAsset_rack_size() {
        return asset_rack_size;
    }

    public void setAsset_rack_size(String asset_rack_size) {
        this.asset_rack_size = asset_rack_size;
    }

    public String getAsset_camera_type() {
        return asset_camera_type;
    }

    public void setAsset_camera_type(String asset_camera_type) {
        this.asset_camera_type = asset_camera_type;
    }

    public String getAsset_cameraform_factor() {
        return asset_cameraform_factor;
    }

    public void setAsset_cameraform_factor(String asset_cameraform_factor) {
        this.asset_cameraform_factor = asset_cameraform_factor;
    }

    public String getAsset_channel() {
        return asset_channel;
    }

    public void setAsset_channel(String asset_channel) {
        this.asset_channel = asset_channel;
    }

    public String getAsset_storage_capacity() {
        return asset_storage_capacity;
    }

    public void setAsset_storage_capacity(String asset_storage_capacity) {
        this.asset_storage_capacity = asset_storage_capacity;
    }

    public String getAsset_printer_type() {
        return asset_printer_type;
    }

    public void setAsset_printer_type(String asset_printer_type) {
        this.asset_printer_type = asset_printer_type;
    }

    public String getAsset_printer_function() {
        return asset_printer_function;
    }

    public void setAsset_printer_function(String asset_printer_function) {
        this.asset_printer_function = asset_printer_function;
    }

    public String getAsset_printer_cartridge() {
        return asset_printer_cartridge;
    }

    public void setAsset_printer_cartridge(String asset_printer_cartridge) {
        this.asset_printer_cartridge = asset_printer_cartridge;
    }

    public String getAsset_barcode_connection_type() {
        return asset_barcode_connection_type;
    }

    public void setAsset_barcode_connection_type(String asset_barcode_connection_type) {
        this.asset_barcode_connection_type = asset_barcode_connection_type;
    }

    public String getAsset_deskphone_connection_type() {
        return asset_deskphone_connection_type;
    }

    public void setAsset_deskphone_connection_type(String asset_deskphone_connection_type) {
        this.asset_deskphone_connection_type = asset_deskphone_connection_type;
    }

    public String getAsset_deskphone_type() {
        return asset_deskphone_type;
    }

    public void setAsset_deskphone_type(String asset_deskphone_type) {
        this.asset_deskphone_type = asset_deskphone_type;
    }

    public String getAsset_microphone_type() {
        return asset_microphone_type;
    }

    public void setAsset_microphone_type(String asset_microphone_type) {
        this.asset_microphone_type = asset_microphone_type;
    }

    public String getAsset_camera_available() {
        return asset_camera_available;
    }

    public void setAsset_camera_available(String asset_camera_available) {
        this.asset_camera_available = asset_camera_available;
    }

    public String getAsset_sd_card_size() {
        return asset_sd_card_size;
    }

    public void setAsset_sd_card_size(String asset_sd_card_size) {
        this.asset_sd_card_size = asset_sd_card_size;
    }

    public String getAsset_imei_no_first() {
        return asset_imei_no_first;
    }

    public void setAsset_imei_no_first(String asset_imei_no_first) {
        this.asset_imei_no_first = asset_imei_no_first;
    }

    public String getAsset_imei_no_second() {
        return asset_imei_no_second;
    }

    public void setAsset_imei_no_second(String asset_imei_no_second) {
        this.asset_imei_no_second = asset_imei_no_second;
    }

    public String getAsset_sim_operator() {
        return asset_sim_operator;
    }

    public void setAsset_sim_operator(String asset_sim_operator) {
        this.asset_sim_operator = asset_sim_operator;
    }

    public String getAsset_sim_no() {
        return asset_sim_no;
    }

    public void setAsset_sim_no(String asset_sim_no) {
        this.asset_sim_no = asset_sim_no;
    }

    public String getAsset_mobile_no() {
        return asset_mobile_no;
    }

    public void setAsset_mobile_no(String asset_mobile_no) {
        this.asset_mobile_no = asset_mobile_no;
    }

    public String getAsset_screen_size() {
        return asset_screen_size;
    }

    public void setAsset_screen_size(String asset_screen_size) {
        this.asset_screen_size = asset_screen_size;
    }

    public String getAsset_color() {
        return asset_color;
    }

    public void setAsset_color(String asset_color) {
        this.asset_color = asset_color;
    }

    public int getAsset_projector_is_remote() {
        return asset_projector_is_remote;
    }

    public void setAsset_projector_is_remote(int asset_projector_is_remote) {
        this.asset_projector_is_remote = asset_projector_is_remote;
    }

    public String getAsset_speaker_type() {
        return asset_speaker_type;
    }

    public void setAsset_speaker_type(String asset_speaker_type) {
        this.asset_speaker_type = asset_speaker_type;
    }

    public String getAsset_speaker_no_of_mic() {
        return asset_speaker_no_of_mic;
    }

    public void setAsset_speaker_no_of_mic(String asset_speaker_no_of_mic) {
        this.asset_speaker_no_of_mic = asset_speaker_no_of_mic;
    }

    public String getAsset_switch_type() {
        return asset_switch_type;
    }

    public void setAsset_switch_type(String asset_switch_type) {
        this.asset_switch_type = asset_switch_type;
    }

    public String getAsset_switch_port_type() {
        return asset_switch_port_type;
    }

    public void setAsset_switch_port_type(String asset_switch_port_type) {
        this.asset_switch_port_type = asset_switch_port_type;
    }

    public String getAsset_switch_lan_port() {
        return asset_switch_lan_port;
    }

    public void setAsset_switch_lan_port(String asset_switch_lan_port) {
        this.asset_switch_lan_port = asset_switch_lan_port;
    }

    public String getAsset_switch_fiber_port() {
        return asset_switch_fiber_port;
    }

    public void setAsset_switch_fiber_port(String asset_switch_fiber_port) {
        this.asset_switch_fiber_port = asset_switch_fiber_port;
    }

    public String getAsset_switch_speed() {
        return asset_switch_speed;
    }

    public void setAsset_switch_speed(String asset_switch_speed) {
        this.asset_switch_speed = asset_switch_speed;
    }

    public String getAsset_switch_speed_unit() {
        return asset_switch_speed_unit;
    }

    public void setAsset_switch_speed_unit(String asset_switch_speed_unit) {
        this.asset_switch_speed_unit = asset_switch_speed_unit;
    }

    public String getAsset_switch_poe_wattage() {
        return asset_switch_poe_wattage;
    }

    public void setAsset_switch_poe_wattage(String asset_switch_poe_wattage) {
        this.asset_switch_poe_wattage = asset_switch_poe_wattage;
    }

    public String getAsset_tv_type() {
        return asset_tv_type;
    }

    public void setAsset_tv_type(String asset_tv_type) {
        this.asset_tv_type = asset_tv_type;
    }

    public String getAsset_tv_hdmi_ports() {
        return asset_tv_hdmi_ports;
    }

    public void setAsset_tv_hdmi_ports(String asset_tv_hdmi_ports) {
        this.asset_tv_hdmi_ports = asset_tv_hdmi_ports;
    }

    public int getAsset_tv_is_remote() {
        return asset_tv_is_remote;
    }

    public void setAsset_tv_is_remote(int asset_tv_is_remote) {
        this.asset_tv_is_remote = asset_tv_is_remote;
    }

    public String getAsset_tv_screen_size() {
        return asset_tv_screen_size;
    }

    public void setAsset_tv_screen_size(String asset_tv_screen_size) {
        this.asset_tv_screen_size = asset_tv_screen_size;
    }

    public String getAsset_ups_capacity() {
        return asset_ups_capacity;
    }

    public void setAsset_ups_capacity(String asset_ups_capacity) {
        this.asset_ups_capacity = asset_ups_capacity;
    }

    public String getAsset_ups_capacity_unit() {
        return asset_ups_capacity_unit;
    }

    public void setAsset_ups_capacity_unit(String asset_ups_capacity_unit) {
        this.asset_ups_capacity_unit = asset_ups_capacity_unit;
    }

    public String getAsset_port_available() {
        return asset_port_available;
    }

    public void setAsset_port_available(String asset_port_available) {
        this.asset_port_available = asset_port_available;
    }

    public String getAsset_port_in_use() {
        return asset_port_in_use;
    }

    public void setAsset_port_in_use(String asset_port_in_use) {
        this.asset_port_in_use = asset_port_in_use;
    }

    public String getAsset_ethernet_port() {
        return asset_ethernet_port;
    }

    public void setAsset_ethernet_port(String asset_ethernet_port) {
        this.asset_ethernet_port = asset_ethernet_port;
    }

    public String getAsset_fiber_optic_port() {
        return asset_fiber_optic_port;
    }

    public void setAsset_fiber_optic_port(String asset_fiber_optic_port) {
        this.asset_fiber_optic_port = asset_fiber_optic_port;
    }

    public String getAsset_ssid() {
        return asset_ssid;
    }

    public void setAsset_ssid(String asset_ssid) {
        this.asset_ssid = asset_ssid;
    }

    public String getAsset_owned_by() {
        return asset_owned_by;
    }

    public void setAsset_owned_by(String asset_owned_by) {
        this.asset_owned_by = asset_owned_by;
    }

    public int getAsset_mobile_technology() {
        return asset_mobile_technology;
    }

    public void setAsset_mobile_technology(int asset_mobile_technology) {
        this.asset_mobile_technology = asset_mobile_technology;
    }

    public MultipartBody.Part getAsset_image() {
        return asset_image;
    }

    public void setAsset_image(MultipartBody.Part asset_image) {
        this.asset_image = asset_image;
    }

    public MultipartBody.Part getAsset_serial_no_image() {
        return asset_serial_no_image;
    }

    public void setAsset_serial_no_image(MultipartBody.Part asset_serial_no_image) {
        this.asset_serial_no_image = asset_serial_no_image;
    }
}
