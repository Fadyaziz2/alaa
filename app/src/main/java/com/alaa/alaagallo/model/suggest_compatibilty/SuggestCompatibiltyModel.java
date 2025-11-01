package com.alaa.alaagallo.model.suggest_compatibilty;

public class SuggestCompatibiltyModel {
    String type_id;
    String brand_id;
    String mod_id;
    String other_brand_id;
    String compatibility_name;
    String notes;
    public SuggestCompatibiltyModel(){

    }
    public SuggestCompatibiltyModel(String type_id, String brand_id, String mod_id, String other_brand_id, String compatibility_name, String notes) {
        this.type_id = type_id;
        this.brand_id = brand_id;
        this.mod_id = mod_id;
        this.other_brand_id = other_brand_id;
        this.compatibility_name = compatibility_name;
        this.notes = notes;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getMod_id() {
        return mod_id;
    }

    public void setMod_id(String mod_id) {
        this.mod_id = mod_id;
    }

    public String getCompatibility_name() {
        return compatibility_name;
    }

    public void setCompatibility_name(String compatibility_name) {
        this.compatibility_name = compatibility_name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
