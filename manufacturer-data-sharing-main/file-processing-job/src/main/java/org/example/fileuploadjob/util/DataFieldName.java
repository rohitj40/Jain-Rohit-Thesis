package org.example.fileuploadjob.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum DataFieldName {
    NAME("Producer Name", Arrays.asList("name","Producer Name"), Collections.emptyList()),
    HUMIDITY("Humidity(%)", Arrays.asList("Humidity", "Humidity(%)"), Collections.emptyList()),
    TEMP("Temperature (C)", Arrays.asList("Temp", "Temp (C)"), Collections.emptyList()),
    PROTEINCONTENT("Protein Content (%)", Arrays.asList("Protein Content", "Protein Content (%)"), Collections.emptyList()),
    FATCONTENT("Fat Content (%)", Arrays.asList("Fat Content", "Fat Content (%)"), Collections.emptyList()),
    DATE("Date",Arrays.asList("date", "Date"), Arrays.asList("date", "dateU")),
    FARMDATE("FirmDate",Arrays.asList("farm_name", "FarmName", "Farm_Name"), Arrays.asList("farmName", "farmNameU", "farmNameUA")),
    FARMLOCATION("FirmDate",Arrays.asList("farm_location", "FarmLocation", "Farm_Location"), Arrays.asList("farmLocation", "farmLocationU", "farmLocationUA")),
    FARMSIZEHECTARES("FarmSizeHectares",Arrays.asList("farm_size_hectares", "FarmSizeHectares", "Farm_Size_hectares"), Arrays.asList("farmSizeHectares", "farmSizeHectaresU", "farmSizeHectaresUA")),
    NOOFCOWS("NumberOfCows",Arrays.asList("number_of_cows", "NumberOfCows", "Number_Of_Cows"), Arrays.asList("numberOfCows", "numberOfCowsU", "numberOfCowsUA")),
    COWBREED("CowBreed",Arrays.asList("cow_breed", "CowBreed", "Cow_Breed"), Arrays.asList("cowBreed", "cowBreedU", "cowBreedUA")),
    COWAGEMONTHS("CowAgeMonths",Arrays.asList("cow_age_months", "CowAgeMonths", "Cow_Age_Months"), Arrays.asList("cowAgeMonths", "cowAgeMonthsU", "cowAgeMonthsUA")),
    COWHEALTH("CowHealth",Arrays.asList("cow_health", "CowHealth", "Cow_Health"), Arrays.asList("cowHealth", "cowHealthU", "cowHealthUA")),
    MILKVOLUMELITERS("MilkVolumeLiters",Arrays.asList("milk_volume_liters", "MilkVolumeLiters", "Milk_Volume_Liters"), Arrays.asList("milkVolumeLiters", "milkVolumeLitersU", "milkVolumeLitersUA")),
    BACTERIALCOUNTCFUML("BacterialCountCFUml",Arrays.asList("bacterial_count_cfu_per_ml", "BacterialCountCFUml", "bacterial_count_CFU_ml"), Arrays.asList("bacterialCountCfuPerMl", "bacterialCountCfuPerMlU", "bacterialCountCfuPerMlUA")),
    SOMATICCELLCOUNTSCC("SomaticCellCountSCC",Arrays.asList("somatic_cell_count_scc", "SomaticCellCountSCC", "somatic_cell_count_SCC"), Arrays.asList("somaticCellCountScc", "somaticCellCountSccU", "somaticCellCountSccUA")),
    COLIFORMCOUNTCFUML("ColiformCountCFUml",Arrays.asList("coliform_count_cfu_per_ml", "ColiformCountCFUml", "coliform_count_CFU_ml"), Arrays.asList("coliformCountCfuPerMl", "coliformCountCfuPerMlU", "coliformCountCfuPerMlUA")),
    YEASTANDMOLDCOUNTCFUML("YeastAndMoldCountCFUml",Arrays.asList("yeast_and_mold_count_cfu_per_ml", "YeastAndMoldCountCFUml", "yeast_and_mold_count_CFU_ml"), Arrays.asList("yeastAndMoldCountCfuPerMl", "yeastAndMoldCountCfuPerMlU", "yeastAndMoldCountCfuPerMlUA")),
    ANTIBIOTICSUSAGE("AntibioticsUsage",Arrays.asList("antibiotics_usage", "AntibioticsUsage", "Antibiotics_Usage"), Arrays.asList("antibioticsUsage", "antibioticsUsageU", "antibioticsUsageUA")),
    CLEANINGPRACTICES("CleaningPractices",Arrays.asList("cleaning_practices", "CleaningPractices", "Cleaning_Practices"), Arrays.asList("cleaningPractices", "cleaningPracticesU", "cleaningPracticesUQ")),
    FEEDTYPE("FeedType",Arrays.asList("feed_type", "FeedType", "Feed_Type"), Arrays.asList("feedType", "feedTypeU", "feedTypeUA")),
    FEEDQUANTITYKG("FeedQuantityKg",Arrays.asList("feed_quantity_kg", "FeedQuantityKg", "Feed_Quantity_Kg"), Arrays.asList("feedQuantityKg", "feedQuantityKgU", "feedQuantityKgUA")),
    PESITICIDESUSED("PesticidesUsed",Arrays.asList("pesticides_used", "PesticidesUsed", "Pesticides_Used"), Arrays.asList("pesticidesUsed", "pesticidesUsedU", "pesticidesUsedUA")),
    FERTILIZERSUSED("FertilizersUsed",Arrays.asList("fertilizers_used", "FertilizersUsed", "Fertilizers_Used"), Arrays.asList("fertilizersUsed", "fertilizersUsedU", "fertilizersUsedUA")),
    COMMENTS("Comments",Arrays.asList("comments", "Comments"), Arrays.asList("comments", "commentsU"));


    DataFieldName(String dataFieldName, List<String> synonyms, List<String> objectFieldNames) {
        this.dataFieldName = dataFieldName;
        this.synonyms = synonyms;
        this.objectFieldNames = objectFieldNames;
    }

    String dataFieldName;
    List<String> synonyms;
    List<String> objectFieldNames;

    public List<String> getSynonyms() {
        return this.synonyms;
    }
    public String getDataFieldName() {
        return this.dataFieldName;
    }
    public List<String> getObjectFieldNames() {
        return this.objectFieldNames;
    }
}
