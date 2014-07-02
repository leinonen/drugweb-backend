package se.leinonen.drugweb.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drugs")
public class Drug {

    @Id
    private Long id;

    private String name;

    @Column(unique = true)
    private String simpleName;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String url;

    private String chemicalName;

    private String commonName;

    private String effects;

    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    // Helpers

    public JsonObject toJson(){
        JsonObject drugJson = new JsonObject();
        //Gson gson = new Gson();
        //String json = gson.toJson(drug);
        drugJson.addProperty("id", this.getId());
        drugJson.addProperty("name", this.getName());
        drugJson.addProperty("simpleName", this.getSimpleName());
        drugJson.addProperty("description", this.getDescription());
        drugJson.addProperty("imageUrl", this.getImageUrl());
        drugJson.addProperty("chemicalName", this.getChemicalName());
        drugJson.addProperty("commonName", this.getCommonName());
        drugJson.addProperty("effects", this.getEffects());
        return drugJson;
    }


    public static List<JsonObject> drugsToJson(List<Drug> list) {
        List<JsonObject> data = new ArrayList<JsonObject>();
        for (Drug drug : list) {
            data.add(drug.toJson());
        }
        return data;
    }

    public static Drug parseFromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Drug.class);
    }
}