package org.example.fileuploadjob.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Farm {
    @JacksonXmlProperty(localName = "date")
    private String date;
    @JacksonXmlProperty(localName = "Date")
    private String dateU;

    @JacksonXmlProperty(localName = "farm_name")
    private String farmName;
    @JacksonXmlProperty(localName = "FarmName")
    private String farmNameU;
    @JacksonXmlProperty(localName = "Farm_Name")
    private String farmNameUA;

    @JacksonXmlProperty(localName = "farm_location")
    private String farmLocation;
    @JacksonXmlProperty(localName = "FarmLocation")
    private String farmLocationU;
    @JacksonXmlProperty(localName = "Farm_Location")
    private String farmLocationUA;

    @JacksonXmlProperty(localName = "farm_size_hectares")
    private String farmSizeHectares;
    @JacksonXmlProperty(localName = "FarmSizeHectares")
    private String farmSizeHectaresU;
    @JacksonXmlProperty(localName = "Farm_Size_hectares")
    private String farmSizeHectaresUA;

    @JacksonXmlProperty(localName = "number_of_cows")
    private String numberOfCows;
    @JacksonXmlProperty(localName = "NumberOfCows")
    private String numberOfCowsU;
    @JacksonXmlProperty(localName = "Number_Of_Cows")
    private String numberOfCowsUA;

    @JacksonXmlProperty(localName = "cow_breed")
    private String cowBreed;
    @JacksonXmlProperty(localName = "CowBreed")
    private String cowBreedU;
    @JacksonXmlProperty(localName = "Cow_Breed")
    private String cowBreedUA;

    @JacksonXmlProperty(localName = "cow_age_months")
    private String cowAgeMonths;
    @JacksonXmlProperty(localName = "CowAgeMonths")
    private String cowAgeMonthsU;
    @JacksonXmlProperty(localName = "Cow_Age_Months")
    private String cowAgeMonthsUA;

    @JacksonXmlProperty(localName = "cow_health")
    private String cowHealth;
    @JacksonXmlProperty(localName = "CowHealth")
    private String cowHealthU;
    @JacksonXmlProperty(localName = "Cow_Health")
    private String cowHealthUA;

    @JacksonXmlProperty(localName = "milk_volume_liters")
    private String milkVolumeLiters;
    @JacksonXmlProperty(localName = "MilkVolumeLiters")
    private String milkVolumeLitersU;
    @JacksonXmlProperty(localName = "Milk_Volume_Liters")
    private String milkVolumeLitersUA;

    @JacksonXmlProperty(localName = "bacterial_count_cfu_per_ml")
    private String bacterialCountCfuPerMl;
    @JacksonXmlProperty(localName = "BacterialCountCFUml")
    private String bacterialCountCfuPerMlU;
    @JacksonXmlProperty(localName = "bacterial_count_CFU_ml")
    private String bacterialCountCfuPerMlUA;

    @JacksonXmlProperty(localName = "somatic_cell_count_scc")
    private String somaticCellCountScc;
    @JacksonXmlProperty(localName = "SomaticCellCountSCC")
    private String somaticCellCountSccU;
    @JacksonXmlProperty(localName = "somatic_cell_count_SCC")
    private String somaticCellCountSccUA;

    @JacksonXmlProperty(localName = "coliform_count_cfu_per_ml")
    private String coliformCountCfuPerMl;
    @JacksonXmlProperty(localName = "ColiformCountCFUml")
    private String coliformCountCfuPerMlU;
    @JacksonXmlProperty(localName = "coliform_count_CFU_ml")
    private String coliformCountCfuPerMlUA;

    @JacksonXmlProperty(localName = "yeast_and_mold_count_cfu_per_ml")
    private String yeastAndMoldCountCfuPerMl;
    @JacksonXmlProperty(localName = "YeastAndMoldCountCFUml")
    private String yeastAndMoldCountCfuPerMlU;
    @JacksonXmlProperty(localName = "yeast_and_mold_count_CFU_ml")
    private String yeastAndMoldCountCfuPerMlUA;

    @JacksonXmlProperty(localName = "pH")
    private String pH;

    @JacksonXmlProperty(localName = "antibiotics_usage")
    private String antibioticsUsage;
    @JacksonXmlProperty(localName = "AntibioticsUsage")
    private String antibioticsUsageU;
    @JacksonXmlProperty(localName = "Antibiotics_Usage")
    private String antibioticsUsageUA;

    @JacksonXmlProperty(localName = "cleaning_practices")
    private String cleaningPractices;
    @JacksonXmlProperty(localName = "CleaningPractices")
    private String cleaningPracticesU;
    @JacksonXmlProperty(localName = "Cleaning_Practices")
    private String cleaningPracticesUQ;

    @JacksonXmlProperty(localName = "feed_type")
    private String feedType;
    @JacksonXmlProperty(localName = "FeedType")
    private String feedTypeU;
    @JacksonXmlProperty(localName = "Feed_Type")
    private String feedTypeUA;

    @JacksonXmlProperty(localName = "feed_quantity_kg")
    private String feedQuantityKg;
    @JacksonXmlProperty(localName = "FeedQuantityKg")
    private String feedQuantityKgU;
    @JacksonXmlProperty(localName = "Feed_Quantity_Kg")
    private String feedQuantityKgUA;

    @JacksonXmlProperty(localName = "pesticides_used")
    private String pesticidesUsed;
    @JacksonXmlProperty(localName = "PesticidesUsed")
    private String pesticidesUsedU;
    @JacksonXmlProperty(localName = "Pesticides_Used")
    private String pesticidesUsedUA;

    @JacksonXmlProperty(localName = "fertilizers_used")
    private String fertilizersUsed;
    @JacksonXmlProperty(localName = "FertilizersUsed")
    private String fertilizersUsedU;
    @JacksonXmlProperty(localName = "Fertilizers_Used")
    private String fertilizersUsedUA;

    @JacksonXmlProperty(localName = "comments")
    private String comments;
    @JacksonXmlProperty(localName = "Comments")
    private String commentsU;
}
